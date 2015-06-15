// Daniela Florentina Tatu, grupa 315CA

#ifndef __TREAP__H
#define __TREAP__H

template <typename T>
class Treap {

public:
	T key;
	int priority;
	Treap<T> *left, *right;
	bool nil;
	int nr_nodes;

// Creaza un nod nil
	Treap()
	{
		priority = -1;
		left = NULL;
		right = NULL;
		nil = true;
		nr_nodes = 0;
	}

// Adauga date, transformand un nod nil intr-un nod obisnuit
	void addData (T& key, int priority)
	{
		this->nil = false;
		this->key = key;
		this->priority = priority;
		this->nr_nodes = 1;
		this->left = new Treap();
		this->right = new Treap();
	}

// Sterge un nod obisnuit, transformandu-l intr-unul nil
	void delData()
	{
		this->nil = true;
		this->priority = -1;
		delete this->left;
		delete this->right;
		this->nr_nodes = 0;
	}

// Verifica daca un nod este sau nu nil
  bool isNil() {
    return this->nil;
  }

// Realizeaza rotirea la dreapta
	void rotateRight (Treap<T> *&fatherPointer)
	{
  		Treap<T> * aux = fatherPointer->left;
			fatherPointer->left = aux->right;
			aux->right = fatherPointer;
			fatherPointer = aux;

		//if ( ! fatherPointer->right->isNil() )
		//T: Nu era necesar
			fatherPointer->right->nr_nodes =
				fatherPointer->right->left->nr_nodes +
				fatherPointer->right->right->nr_nodes + 1;
		fatherPointer->nr_nodes =
			fatherPointer->left->nr_nodes +
			fatherPointer->right->nr_nodes + 1;
	}

// Realizeaza rotirea la stanga
	void rotateLeft (Treap<T> *&fatherPointer)
	{
		Treap<T> * aux = fatherPointer->right;
			fatherPointer->right = aux->left;
			aux->left = fatherPointer;
			fatherPointer = aux;

		//if ( ! fatherPointer->left->isNil() ) 
		//T: Nu era necesar
			fatherPointer->left->nr_nodes =
				fatherPointer->left->left->nr_nodes +
				fatherPointer->left->right->nr_nodes + 1;
		fatherPointer->nr_nodes =
			fatherPointer->left->nr_nodes +
			fatherPointer->right->nr_nodes + 1;
	}

// Insereaza un element in treap
	void insert ( Treap<T> *&fatherPointer, T& key, int priority )
	{
		if ( this->isNil() ) {
			this->addData (key, priority);
			return ;
		}

		if ( key < this->key ) {
			left->insert (left, key, priority);
			nr_nodes ++;
		}
		else {
			right->insert (right, key, priority);
			nr_nodes ++;
		}

		if ( left->priority > priority )
			rotateRight ( fatherPointer );

		else if ( right->priority > priority )
			rotateLeft ( fatherPointer );
  }

// Sterge un element din treap
	void erase ( Treap<T> *&fatherPointer, T& key ) // T: Key nu era transmis prin parametru
	{
		if ( this->isNil() )
			return ;

		if ( key < fatherPointer->key )
		{
			erase ( fatherPointer->left, key );
			fatherPointer->nr_nodes--;
		}
	 	else {

			if ( key > fatherPointer->key )
			{
				erase ( fatherPointer->right, key );
				fatherPointer->nr_nodes--;
			}
			else if ( fatherPointer->left->isNil() &&
						fatherPointer->right->isNil() )
			{
				fatherPointer->delData();
			}
			else if ( fatherPointer->left->priority >
						fatherPointer->right->priority )
			{
				rotateRight ( fatherPointer );
				erase ( fatherPointer, key );
			}
			else
			{
				rotateLeft ( fatherPointer );
				erase ( fatherPointer, key );
			}
		}
	}

// Gaseste elementul k din treap
	T& findK(int k)
	{
		/*if ( right->nr_nodes >= k )
			return right->findK(k);
		else
		{
			if ( k-1 == right->nr_nodes )
				return key;
			else
				return left->findK (k - 1 - right->nr_nodes);
		}
		*/
		
		// T:Varianta mea de findK , nu mai ai nevoie de findMax si findMin
		if(k < right->nr_nodes){
            return this->right->findK(k);
        }

        else if(k > right->nr_nodes){
            return this->left->findK(k - right->nr_nodes - 1);
        }

        else {
            return this->key;
        }
	}

// Gaseste elementul maxim din treap
// regula de la BST
	/*T& findMax()
	{
		if ( right->isNil() )
			return key;
		else
			return right->findMax();
	}
*/
// Gaseste elementul minim din treap
// regula de la BST
	/*T& findMin()
	{
		if ( left->isNil() )
			return key;
		else
			return left->findMin();
	}*/

};

#endif
