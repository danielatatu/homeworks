Tatu Daniela-Florentina
Grupa: 325CA


Intrucat sursa mea este bogata in comentarii, aici voi prezenta doar cateva detalii de implementare.

problema 1.

In GAC3 se proceseaza pe rand fiecare hiperarc si se aplica algoritmul recursiv, 
pana ce domeniile nu mai trebuie restranse.

Se utilizeaza 2 functii auxiliare:

%% verify/3
%% verify(+Ys, +Ds, +C)

Primeste ca parametri variabilele implicate intr-o constrangere, in afara de variabila curenta, 
domeniile acestor variabile si constrangerea ce trebuie satisfacuta si verifica daca 
exista o combinatie de valori ale variabilelor Ys astfel incat C sa fie adevarata.

%% getHyperarc/4
%% getHyperarc(+X, +Vars, +Cons, -Hyperarc)

Primeste ca parametri o variabila X si o constrangere (variabilele de care depinde + predicatul) si 
intoarce un hiperarc. Daca X se afla printre variabilele constrangerii, se determina pe rand toate 
hiperarcele avand ca variabila principala fiecare dintre celelalte variabile ale constrangerii respective.


problema 2.

In MAC se restrange pe rand domeniul fiecarei variabile la o singura valoare. 
Este implementat cu ajutorul unui helper deoarece trebuie retinuta pozitia curenta, 
a variabilei careia ii confer o valoare. Se extrag initial hiperarcele asociate 
tuturor constrangerilor si se restrang toate domeniile, apoi se aplica algoritmul, 
pornind de la pozitia 0 pana ce toate domeniile contin o singura valoare.

bonus.

- 25 de variabile: cate 5 pentru fiecare persoana
- domenii: initial fiecare casa poate avea orice culoare,
fiecare om poate avea orice nationalitate, etc.
- constrangerile sunt scrise conform enuntului problemei si explicate in comentarii