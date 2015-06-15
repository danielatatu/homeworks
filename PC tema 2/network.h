/******************************************************************************/
/* Tema 2 Protocoale de Comunicatie (Aprilie 2015)                            */
/* Tatu Daniela Florentina, 325CA
/******************************************************************************/

#ifndef _NETWORK_H_
#define _NETWORK_H_

#include <fstream>
#include "router.h"

struct Event {
	int r1, r2, type, cost, time;
};

class Network {

public:
	int num_routers; // numarul de routere
	vector<Router*> routers;

	int time_units; // nr de unitati de timp care vor fi simulate de program
	int num_events; // numarul de evenimente
	vector<Event> events;

	int num_messages; // numarul de mesaje
	vector<Message> messages;

	// adauga legaturile initiale in topologiile routerelor implicate
	void add_initial_links (char *topology_file);

	// retine informatiile despre mesajele rutabile ce urmeaza a fi transmise
	void save_events (char *events_file);

	// retine informatiile despre schimbarile ce vor aparea in cadrul retelei
	void save_messages (char *messages_file);
};

#endif /* _NETWORK_H_ */
