// Daniela Florentina Tatu, grupa 315CA

#ifndef __TRIE__H
#define __TRIE__H

#include "treap.h"

using namespace std;
class Trie {

public:
	Treap<entry> *words;
	Trie *child[8];

	Trie()
	{
		words = NULL;
		for (int i = 0; i < 8; i++)
			child[i] = NULL;
	}

// Insereaza un element in trie, folosind recursivitatea
	void insert ( entry& new_entry, int priority,
						int *wd_num, int& dim, int level ) // T: Prin referinta 
						                                    // am pus si dim
	{
	// pozitia unde trebuie continuata inserarea
		int p = wd_num[level] - 2;

		if ( ! child[p] )
			child[p] = new Trie;

	// daca am ajuns la ultima cifra
	// urmeaza sa facem inserarea in treap
		if ( level == dim-1 )
		{
			if ( ! child[p]->words )
				child[p]->words = new Treap<entry>;

			child[p]->words->insert
					( child[p]->words, new_entry, priority );
			return ;
		}

	// recursivitatea
		child[p]->insert ( new_entry, priority, wd_num, dim, level+1 );
	}

// Cauta si afiseaza in fisierul dat ca parametru
// un element din trie, folosind recursivitatea
	void find ( int *vect, int& dim, int k ,
					int level, int priority, ofstream &f)
	{
	// pozitia unde trebuie continuata cautarea
		int p = (vect[level]) - 2;

	// daca am ajuns la ultima cifra
	// urmeaza sa cautam in treap si sa afisam
		if ( level == dim-1 )
		{
		// comportamentul "circular" al lui k
			//if ( k > child[p]->words->nr_nodes )
			//T: am comentat linia de mai sus pentru ca sigur trebuie sa ai
			// cel putin un nod in treap daca ai introdus cuvintele deci era inutila
			k = k % child[p]->words->nr_nodes;
                
  			entry key;
  			/*if ( k == 0 )
  				key = child[p]->words->findMin();
  			    else if ( k == 1 )
  			    key = child[p]->words->findMax();
  			else*/
  			//T: Nu mai e nevoie de fnidMax si findMin, am explicat in treap
		    key = child[p]->words->findK(k);

  		// incrementarea numarului de aparitii
			child[p]->words->erase ( child[p]->words, key );
			key.number++;
			child[p]->words->insert ( child[p]->words, key, priority );

		// afisarea
			f << key.word << "\n"; //T: endl ingreuneaza , foloseste '\n
			return ;
		}

	// recursivitatea
		child[p]->find ( vect, dim, k, level+1, priority, f);
	}

};

#endif
