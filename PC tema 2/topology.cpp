/******************************************************************************/
/* Tema 2 Protocoale de Comunicatie (Aprilie 2015)                            */
/* Tatu Daniela Florentina, 325CA
/******************************************************************************/

#include "topology.h"

Topology::Topology (int sz) {
	size  = sz;
	topology = new int*[sz];

	for (int i = 0; i < sz; i++) {
		topology[i] = new int[sz];
		for (int j = 0; j < sz; j++)
			if (i != j)
				topology[i][j] = LUNGMAX; // initial toate routerele sunt neconectate
			else
				topology[i][j] = 0; // costul dintre un router si el insusi este 0
	}
}

void Topology::add_link (int i, int j, int cost) {
	topology[i][j] = cost;
	topology[j][i] = cost;
}

void Topology::erase_link (int i, int j) {
	topology[i][j] = LUNGMAX;
	topology[j][i] = LUNGMAX;
}

void Topology::reset_info (int i) {

	// routerele devin neconectate cu routerul i
	for (int j = 0; j < size; j++)
		if (i != j) {
			topology[i][j] = LUNGMAX;
			topology[j][i] = LUNGMAX;
		}
}

int Topology::get_cost (int i, int j) {
	return topology[i][j];
}

vector<int> Topology::get_neighbors (int r) {
	vector<int> neighbors;

	for (int i = 0; i < size; i++)
		if (i != r && topology[r][i] < LUNGMAX)
			neighbors.push_back(i);
	return neighbors;
}
