/******************************************************************************/
/* Tema 2 Protocoale de Comunicatie (Aprilie 2015)                            */
/* Tatu Daniela Florentina, 325CA
/******************************************************************************/

#include "routing_table.h"

RoutingTable::RoutingTable (int sz) {
	size = sz;
	table.reserve(sz);
}

int RoutingTable::get_next_hop (int dst) {
	return table[dst].next_hop;
}

void RoutingTable::update (int source, Topology *t) {

	// vector ce tine evidenta nodurilor deja selectate
	int *selected = new int[size];
	for (int i = 0; i < size; i++)
		selected[i] = 0;
	selected[source] = 1;

	// initializare tabela de dirijare
	for (int i = 0; i < size; i++) {
		table[i].cost = t->get_cost (source, i);

		// daca routerul i este vecin, initial va fi si next-hop
		if (table[i].cost < LUNGMAX)
			table[i].next_hop = i;
	}

	// modificare tabela de dirijare
	for (int i = 0; i < size-1; i++) {

		// gaseste nodul neselectat cu cel mai mic cost
		int cost_min = LUNGMAX, min;
		for (int k = 0; k < size; k++)
			if (selected[k] == 0 && table[k].cost < cost_min) {
				cost_min = table[k].cost;
				min = k;
			}
		selected[min] = 1;

		// recalculeaza costurile pentru nodurile neselectate in functie de acesta
		for (int k = 0; k < size; k++) {

			if (selected[k] == 0) {
				// costul de la sursa la nodul k prin min
				int s_min_k = table[min].cost + t->get_cost (min, k);

				// daca gasesc cost egal
				if (table[k].cost == s_min_k && 
						table[k].next_hop > table[min].next_hop)
					// pastrez drumul cu next_hop-ul cu indice mai mic
					table[k].next_hop = table[min].next_hop;

				// daca prin min costul este mai mic, preiau next-hop-ul lui
				else if (table[k].cost > s_min_k) {
					table[k].cost = s_min_k;
					table[k].next_hop = table[min].next_hop;
				}
			}
		}
	}
}
