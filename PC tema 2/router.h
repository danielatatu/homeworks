/******************************************************************************/
/* Tema 2 Protocoale de Comunicatie (Aprilie 2015)                            */
/* Tatu Daniela Florentina, 325CA
/******************************************************************************/

#ifndef _ROUTER_H_
#define _ROUTER_H_

#include <string.h>
#include <stdlib.h>
#include <sstream>

#include "topology.h"
#include "routing_table.h"
#include "api.h"

struct Message {
	int src, dst, time, tag;
	char* msg;
};

class Router {

private:
	int id;
	Topology *topology;
	RoutingTable *r_table;
	int *versions; // vector ce retine pe pozitia i ultima versiune 
								 // cunoscuta despre routerul cu id-ul i

public:
	Router (int i, int n);

	// adauga o legatura in topologie
	void add_link (int r1, int r2, int cost);
	// sterge o legatura din topologie
	void erase_link (int r1, int r2);

	// creeaza un mesaj de gestionare (cu vecinii si costurile aferente)
	// si il transmite tuturor vecinilor
	void start_protocol_msg();

	// trimite mesajul primit ca parametru tuturor vecinilor
	// functie folosita pentru forwarding
	void send_protocol_msg (int msg_size, char *msg);

	// primeste mesajele de gestionare, actualizeaza datele din topologie
	// in functie de ele si le trimite mai departe, vecinilor
	void process_protocol_msg ();

	// daca routerul curent este sursa unui mesaj rutabil
	// il trimite next-hop-ului, tinand cont de tabela de rutare
	void start_routing_msg (Message msg);

	// primeste mesajele rutabile si daca routerul curent nu este
	// destinatia, le trimite next-hop-urilor
	void process_routing_msg ();

	// recalculeaza tabela de rutare pe baza topologiei
	void update_table();
};

#endif /* _ROUTER_H_ */
