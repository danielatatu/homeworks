// Daniela Florentina Tatu, grupa 315CA

#include <iostream>
#include <fstream>
#include <math.h>
#include <stdlib.h>

using namespace std;

#include "tema4.h"
#include "trie.h"

int main() {

	srand(time(0));
	Trie dictionary;

// fisierul de intrare
	ifstream f("date.in");
	if (!f) {
		cerr << "Error opening date.in!\n";
		return 0;
	}

// numarul de cuvinte ce trebuie introduse in dictionar
	int N;
// dimensiunea fiecarui cuvant (se modifica in for)
	int dim;
// structura salvata in "treap"
	entry new_entry;

	f >> N;

// pentru fiecare cuvant
	for (int count = 1; count <= N; count++) {

	// cuvantul
		f >> new_entry.word;

	// numarul de aparitii
		f >> new_entry.number;

		dim = new_entry.word.size();

	// vectorul ce va contine
	// "forma numerica" a cuvantului
		int wd_num[dim];

		str_to_int ( new_entry.word, wd_num );

		dictionary.insert ( new_entry, rand() % 1000, wd_num, dim, 0 );
	}

// numarul de siruri de taste primite de la utilizator
	int M;
// numarul cuvantului cautat (cel de dupa *)
	int k;
// sirul introdus de utilizator
	string query;

	f >> M;

// fisierul de iesire
	ofstream fout("date.out");
	if (!fout) {
		cerr << "Error opening date.out!\n";
		return 0;
	}

	// pentru fiecare sir
	for (int count = 1; count <= M; count++) {

		f >> query;

		dim = query.find('*'); //T: Retin direct pozitia unde gasesc '*'

	// vectorul ce va contine cifrele
	// aflate inaintea caracterului '*'
		int vect[dim];

		k = 0;
		resolve_query (query, vect, dim, k);
	// daca exista '*' micsoram dimensiunea vectorului
	// cu [nr de cifre ale lui k] + [1 -> caracterul '*']
	//T: Nu mai e nevoie sa micsorez marimea vectorului
        //T: Trimit ca parametru k, nu k+1, pentru noul findK (explicat in trie)
       
		dictionary.find ( vect, dim, k, 0, rand() % 1000, fout );
	}

	f.close();
	fout.close();
	return 0;
}
