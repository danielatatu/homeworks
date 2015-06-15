// Daniela Florentina Tatu, grupa 315CA

#ifndef __MAIN__H
#define __MAIN__H

class entry {

public:
	string word;
	int number;

	// supraincarcarea operatorului >
	bool operator> (entry& e)
	{
		if ( number > e.number )
			return true;
		else if ( (word < e.word) && (number == e.number) )
			return true;
		else
			return false;
	}

	// supraincarcarea operatorului <
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
	
	for ( int i = 0; i < n; i++ )
		vect[i] = query[i] - '0';

	// forul se executa de la pozitia n + 1, adica parcurge cifrele de dupa '*'
	for ( unsigned int j = n + 1; j < query.size(); j++ )
		k = k*10 + (query[j] - '0') ;
}

#endif
