// Daniela Florentina Tatu, grupa 315CA

#ifndef __MAIN__H
#define __MAIN__H

class entry {

public:
	string word;
	int number;

	bool operator> (entry& e)
	{
		if ( number > e.number )
			return true;
		else if ( (word < e.word) && (number == e.number) )
			return true;
		else
			return false;
	}

	bool operator< (entry& e)
	{
		if ( number < e.number)
			return true;
		else if ( (word > e.word) && (number == e.number) )
			return true;
		else
			return false;
	}

};

void str_to_int (string& word, int *wd) {

	//const char *w = word.c_str();
	//T: Aici nu era necesar acel const char, poti lucra direct pe word

	for ( unsigned int i = 0; i < word.size(); i++ )
		if (word[i] >= 'a' && word[i] <= 'c')
			wd[i] = 2;
		else if (word[i] >= 'd' && word[i] <= 'f')
			wd[i] = 3;
		else if (word[i] >= 'g' && word[i] <= 'i')
			wd[i] = 4;
		else if (word[i] >= 'j' && word[i] <= 'l')
			wd[i] = 5;
		else if (word[i] >= 'm' && word[i] <= 'o')
			wd[i] = 6;
		else if (word[i] >= 'p' && word[i] <= 's')
			wd[i] = 7;
		else if (word[i] >= 't' && word[i] <= 'v')
			wd[i] = 8;
		else if (word[i] >= 'w' && word[i] <= 'z')
			wd[i] = 9;
}

void resolve_query (string& query, int *vect, int& n, int& k) {

	//const char *q = query.c_str(); 
	//T: Iar nu era nevoie de linia de mai sus
	
	for ( int i = 0; i < n; i++ ) {
		vect[i] = query[i] - '0';
	}

	// daca exista '*' in sir 
		//if ( i < n )
			// calculam k
    // T: Nu conteaza daca exista sau nu, se va executa forul doar daca exista
    // T: De la pozitia '*' + 1 adica sare de steluta si merge sa ia cifrele de dupa
			for ( unsigned int j = n + 1; j < query.size(); j++ )
				k = k*10 + (query[j] - '0') ;
}

#endif
