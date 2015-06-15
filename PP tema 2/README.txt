Student: Daniela Florentina Tatu
Grupa: 325 CA

Continutul arhivei este urmatorul:

* Board.hs, BoardTest.hs: implementarea tablei de joc si a mutarilor, 
						  alaturi de testele aferente
* AISimple.hs, AISimpleTest.hs: implementarea euristicii simple de joc,
								alaturi de testele aferente
* AIMinimax.hs, AIMinimaxTest.hs: implementarea algoritmului minimax
								  alaturi de testele aferente
* Tests.hs: toate testele de mai sus, cumulate
* Interactive.hs: mecanisme de I/O, ce permit jocul între doi utilizatori
    umani sau între un utilizator uman ai calculator, pe baza euristicilor.

Testele pot fi rulate individual sau toate odata, pe baza fisierului 'Tests.hs'.
Pentru rularea globala, invocati din terminalul sistemului de operare:

> runhaskell Tests


In continuare voi prezenta ideea generala de implementare:

Tipul 'Board' retine informatii despre:
-- jucatorul care urmeaza sa mute.
-- (scorul utilizatorului, scorul oponentului)
-- numarul de seminte din fiecare casuta a utilizatorului
-- numarul de seminte din fiecare casuta a adversarului

Reprezentarea tablei se va face in felul urmator:
	
>> daca jocul s-a incheiat:
	
	Game over! Score: (x, y)
	   4  0  1  0  0  0
	 y                  x
	   0  0  0  0  0  0
	   
>> daca nu s-a incheiat:

	Next player: You
	   4  4  4  4  4  4
	 0                  0
	   4  4  4  4  4  4

Functia MOVE este cea mai complexa. 

Verifica daca numarul casutei dat ca parametru este valid (intre 1 si 6) si daca 
pe casuta respectiva exista cel putin o samanta si, daca nu, intoarce aceeasi configuratie.

Daca mutarea este valida se creeaza o lista "circulara" care retine casutele jucatorului aflat la rand, 
scorul jucatorului si casutele oponentului si in cadrul caruia se fac modificarile necesare. 

Descrierea listei circulare ce retine configuratia initiala:

Daca tabla arata astfel:  
	
	1  2  3  4  5  6
S'                  		S   Next player: You
	1 34  1  1  1  1

si casuta de unde se efectueaza mutarea (casuta de start) este 2,

lista circulara formata va arata astfel:

pozitiile in lista: 	0 1 2 3 4 5 	 6 		7 8 9 10 11 12
numarul de seminte: 	1 0 1 1 1 1 	 S 		6 5 4  3  2  1
			playerSeeds			   reversed 
					 playerScore	opponentSeeds

- numarul de seminte ce trebuie mutate va fi: noSeeds = 34
- ultima casuta pe care pun seminte (casuta de stop) va fi: 9
	h - 1 + noSeeds = 35 => (mod 35 13) = 9
- de cate ori trec prin fiecare cesuta: 34 / 13 = 2
	=> cate seminte trebuie sa adaug pe fiecare casuta: 
		addition = 2
		start + 1 -> stop (inclusiv) : + addition + 1
		stop + 1 -> start (inclusiv) : + addition
	=> exista doua cazuri: start < stop SAU stop < start
	
 + + + + + +	+ 		+ + +
 + + + + + + 	+ 		+ + +  +  +  +
   + + + + + 		    + + +  +  +  +
 0 1 2 3 4 5 	6 		7 8 9 10 11 12
 3 2 4 4 4 4    S+3 		9 8 7  5  4  3
 playerSeeds		    reversed opponentSeeds
		 playerScore

Pentru AISimple am utilizat functia sortBy si, cu ajutorul unei functii ce compara 
2 perechi casa-configuratie in functie de scorul obtinut de adversar, 
am ordonat lista posibilelor mutari crescator, dupa castigul adversarului, 
alegand apoi ultimul element din lista obtinuta (cel cu maximum de castig).

Pentru MiniMax am utilizat un arbore reprezentat astfel:

data Tree s a = 
	Leaf (Maybe a, s) | 
	Node (Maybe a, s) [Tree s a] 
deriving Show

(Maybe a, s) este cheia nodului.
'Maybe a' deoarece radacina arborelui nu contine 'a'.

[Tree s a] este lista de copii a nodului.

Constructie: 
daca functia de generare a succesorilor intoarce lista vida, obtinem o frunza, altfel un nod;

Prune:
daca nivelul la care se face prune este n = 0, nodul se transforma in frunza, 
altfel se limiteaza toti copii nodului pana la nivelul n - 1;

Maximize si Minimize:
sunt functii care se aplica recursiv pe copiii nodurilor; se verifica daca starea curenta 
este consecutiva cu cea a copilului si daca da => aceeasi functie, 
daca nu => functia opusa;

Pick:
functioneaza asemanator functiei utilizate pentru AISimple, sortand lista copiilor unui nod 
in functie de rezultatul pe care il dau in urma algoritmului minimax.
Utilizeaza un comparator ce se poate adapta starilor consecutive, 
aplicand functia corespunzatoare (minimize sau maximize).



