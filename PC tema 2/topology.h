/******************************************************************************/
/* Tema 2 Protocoale de Comunicatie (Aprilie 2015)                            */
/* Tatu Daniela Florentina, 325CA
/******************************************************************************/

#ifndef _TOPOLOGY_H_
#define _TOPOLOGY_H_

#include <vector>
using namespace std;

#define LUNGMAX 123456789

class Topology {

private:
	int size; // numarul de routere din retea
	int **topology; // matrice ce retine pe pozitia [i][j] costul legaturii i-j

public:
	Topology (int sz);

	// adauga o legatura in topologie
	void add_link (int i, int j, int cost);

	// sterge o legatura din topologie
	void erase_link (int i, int j);

	// sterge informatiile vechi despre routerul primit ca parametru
	void reset_info (int i);

	// intoarce costul legaturii i-j
	int get_cost (int i, int j);

	// intoarce un vector ce contine id-urile vecinilor
	// routerului dat ca parametru
	vector<int> get_neighbors (int r);
};

#endif /* _TOPOLOGY_H_ */
