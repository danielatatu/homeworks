
Student: Tatu Daniela Florentina
Grupa: 325CA

Problema 1.

Pentru rezolvarea acestei probleme am utilizat metoda backtracking, 
colorand pe rand fiecare nod cu o culoare ce ofera un cost minim, 
tinand cont de vecinii deja colorati. 
Daca pentru un nod nu exista o culoare valida, se revine la nodul precedent.

In plus, am retinut un cost minim al solutiei finale, care initial va fi costul 
primei solutii gasite. In cadrul generarii celorlalte solutii se va compara la fiecare pas 
noul cost obtinut, iar daca depaseste costul minim, se renunta la acea posibila colorare.

Problema 2.

Pentru rezolvarea acestei probleme am utilizat metoda Greedy. 
La fiecare pas se gaseste nodul cu numar minim de vecini si care inca are muchii neorientate.

Se verifica daca gradul sau extern (gasit prin procesarea altor noduri) 
adunat cu numarul de muchii neorientate pe care le are este par sau impar:
	-> daca este par, orientez toate muchiile catre exterior
	-> daca este impar, orientez o muchie catre interior, iar pe  celelalte catre exterior
si actualizez datele celorlalte noduri implicate (vecinilor).

La final se verifica daca a ramas vreun nod cu gradul extern impar, iar daca da se afiseaza 'Imposibil'.

Mai multe detalii ale implementarii sunt disponibile in comentariile surselor.