/******************************************************************************/
/* Tema 2 Protocoale de Comunicatie (Aprilie 2015)                            */
/* Tatu Daniela Florentina, 325CA
/******************************************************************************/

#include "router.h"

Router::Router (int i, int n) {
	id = i;
	topology = new Topology(n);
	r_table = new RoutingTable(n);
	versions = new int[n];
}

void Router::add_link (int r1, int r2, int cost) {
	topology->add_link (r1, r2, cost);
}

void Router::erase_link (int r1, int r2) {
	topology->erase_link (r1, r2);
}


void Router::start_protocol_msg() {

	// mesajul de gestionare va contine:

	// id-ul routerului curent (sursei)
	string s = to_string(id) + " ";
	// versiunea actualizata
	s += to_string (++versions[id]) + " ";

	// fiecare vecin si costul legaturii pana la el
	vector<int> n = topology->get_neighbors (id);
	for (unsigned int i = 0; i < n.size(); i++) {
		s += to_string (n[i]) + " ";
		s += to_string (topology->get_cost(id, n[i])) + " ";
	}

	char * msg = (char *)s.c_str();
	send_protocol_msg (strlen(msg)+1, msg);
	// +1 deoarece includ si terminatorul de sir
}

void Router::send_protocol_msg (int msg_size, char *msg) {

	vector<int> n = topology->get_neighbors (id);

	for (unsigned int i = 0; i < n.size(); i++)
		endpoint[id].send_msg(&endpoint[n[i]], msg, msg_size, NULL);
}

void Router::process_protocol_msg () {

	int msg_size;
	char *message;

	loop {
		message = new char[MAX_MSG_CONTENT];
		msg_size = endpoint[id].recv_protocol_message(message);

		// daca nu s-a primit nimic (si nu mai e nimic de primit la timpul curent)
		if (msg_size == -1) break;

		istringstream iss(message);
		int src, vers;
		iss >> src >> vers;

		// daca am primit un mesaj cu o versiune mai noua
		if ( versions[src] < vers ) {

			versions[src] = vers; // actualizez versiunea
			topology->reset_info(src); // sterg informatiile vechi
			int neigh, cost;

			// actualizez informatiile despre sursa in topologie
		  loop {
		    iss >> neigh >> cost;
		    if (iss == '\0') break;
		    topology->add_link(src, neigh, cost);
		  }

			// trimit mesajul mai departe, vecinilor
			send_protocol_msg(msg_size, message);
		}
		delete[] message;
	}
}

void Router::start_routing_msg (Message msg) {

	int nh = r_table->get_next_hop(msg.dst);
	endpoint[id].route_message(&endpoint[nh], msg.dst, msg.tag, msg.msg, NULL);
}

void Router::process_routing_msg () {

	int  src, dst, tag;
	char* message;
	bool valid;

	loop {
		message = new char[MAX_MSG_CONTENT];
		valid = endpoint[id].recv_message(&src, &dst, &tag, message);

		// daca nu s-a primit nimic (si nu mai e nimic de primit la timpul curent)
		if (!valid) break;

		// daca routerul curent nu este destinatia mesajului
		// il transmite next-hop-ului
		if (id != dst) {
			int nh = r_table->get_next_hop(dst);
			endpoint[id].route_message(&endpoint[nh], dst, tag, message, NULL);
		}
	}
}

void Router::update_table () {
	r_table->update(id, topology);
}
