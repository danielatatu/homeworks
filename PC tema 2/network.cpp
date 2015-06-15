/******************************************************************************/
/* Tema 2 Protocoale de Comunicatie (Aprilie 2015)                            */
/* Tatu Daniela Florentina, 325CA
/******************************************************************************/

#include "network.h"

void Network::add_initial_links (char *topology_file) {

	ifstream f (topology_file);

  f >> num_routers;
  routers.reserve(num_routers);
  for (int i = 0; i < num_routers; i++)
  	routers[i] = new Router(i, num_routers);

	// citesc pe rand fiecare legatura din "topology.in"
	// si o adaug in topologiile routerelor implicate
	int r1, cost, r2;
  do {
  	f >> r1;
  	f >> cost;
  	f >> r2;
  	routers[r1]->add_link (r1, r2, cost);
  	routers[r2]->add_link (r1, r2, cost);
  } while ( !f.eof() );

  f.close();
}

void Network::save_events (char *events_file) {

	ifstream f (events_file);

  f >> time_units;
  f >> num_events;
  events.reserve(num_events);

	// citesc pe rand fiecare eveniment din "events.in"
	// si il adaug in vectorul de evenimente
  for (int i = 0; i < num_events; i++) {
  	f >> events[i].r1;
		f >> events[i].r2;
		f >> events[i].type;
		f >> events[i].cost;
		f >> events[i].time;
	}
  f.close();
}

void Network::save_messages (char *messages_file) {

	ifstream f (messages_file);

  f >> num_messages;
  messages.reserve(num_messages);
  for (int i = 0; i < num_messages; i++)
		messages[i].msg = new char[MAX_MSG_CONTENT];

	// citesc pe rand fiecare mesaj din "messages.in"
	// si il adaug in vectorul de mesaje
  for (int i = 0; i < num_messages; i++) {
		f >> messages[i].src;
		f >> messages[i].dst;
		f >> messages[i].time;
		f >> messages[i].tag;
		f.getline (messages[i].msg, MAX_MSG_CONTENT);
		int len = strlen(messages[i].msg);
		messages[i].msg[len] = '\n';
		messages[i].msg[len+1] = '\0';
	}
  f.close();
}
