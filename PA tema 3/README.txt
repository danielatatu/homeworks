
Student: Tatu Daniela Florentina
Grupa: 325CA



Problema 1.

Pentru rezolvarea acestei probleme am pornit de la algoritmul explicat aici [1] (Problem 4.22).

Pe scurt, se demonstreaza ca, fiind data o "greutate" a fiecarei muchii:

wij = r · cij - pj	unde:

r = valoarea estimata a raportului profit / cost
cij = costul muchiei ij
pj = profitul nodului j

=> r este mai mic decat valoarea reala a raportului daca EXISTA cicluri negative in graf 
(se utilizeaza greutatea fiecarei laturi in cadrul algoritmului Bellman Ford pentru a afla acest lucru)

=> r este mai mare sau egal decat valoarea reala a raportului daca NU EXISTA astfel de cicluri


COMPLEXITATEA algoritmului este O (N * M * log R), unde

N = numarul de noduri
M = numarul de muchii
R = valoarea maxima estimata a raportului

cautarea binara => log R
	Bellman Ford => N * M


[1] http://www.ece.northwestern.edu/~dda902/336/hw5-sol.pdf




Problema 2.

Pentru rezolvarea acestei probleme am utilizat metoda calculului fluxului maxim intr-un graf, 
pornind de la ideile prezentate la laboratorul de PA.

Am format matricea de capacitati astfel incat singurele laturi care au capacitate 1 
sunt cele care fac parte dintr-un drum ce leaga sursa de destinatie si are lungime maxim 3.

Astfel, cand saturam un drum de ameliorare in acest graf este echivalent 
cu a elimina un nod si a "sterge" toate drumurile care treceau prin el.

COMPLEXITATEA acestui algoritm depinde de complexitatea algoritmului BFS 
utilizat pentru calculul fluxului maxim, care, datorita implementarii cu liste de adiacenta, este

 O (N + M), unde 

N = nr de noduri
M = nr de muchii de capacitate 1

Intrucat acest algoritm este apelat in mod repetat pana la saturarea tuturor drumurilor de ameliorare, 
complexitatea este marita

O (distance * (N + M)), unde

distance = distanta ceruta in enuntul problemei


