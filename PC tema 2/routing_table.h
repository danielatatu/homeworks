/******************************************************************************/
/* Tema 2 Protocoale de Comunicatie (Aprilie 2015)                            */
/* Tatu Daniela Florentina, 325CA
/******************************************************************************/

#ifndef _TABLE_H_
#define _TABLE_H_

#include "topology.h"

struct RoutingTableEntry {
	int next_hop;
	int cost;
};

class RoutingTable {

private:
	int size; // numarul de routere din retea
	vector<RoutingTableEntry> table; // vector ce retine pe pozitia i
																	 // next-hop-ul si costul catre routerul i
public:
	RoutingTable (int sz);

	// intoarce next-hop-ul catre dst
	int get_next_hop (int dst);

	// recalculeaza tabela de rutare pe baza topologiei date ca parametru
	void update (int source, Topology *t);
};

#endif /* _TABLE_H_ */
