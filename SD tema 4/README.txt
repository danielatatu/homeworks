Nume: Tatu Daniela Florentina
Grupa: 315 CA

	In continuare voi prezenta cateva detalii de implementare:

1. Stringurile ce trebuie inserate in dictionar sunt prelucrate in "str_to_int" din "tema4.h", obtinandu-se vectori 
de int-uri in care sunt stocate formele numerice ale respectivelor cuvinte.

2. Din sirul de cifre tastate de utilizator (eventual cu *k la final) se obtin:

	- un vector de int-uri format din cifrele de pana la caraterul '*'
		(daca acesta exista; toate cifrele, daca nu)
	- numarul k
Acest lucru este realizat cu ajutorul functiei "resolve_query" din "tema4.h".

3. Atat stringul cat si numarul lui de aparitii sunt stocate in clasa "entry", tipul de date continut de "treap". 
Implementarea structurii de date "Treap" am preluat-o din laborator, cu mici modificari:
"findK" intoarce a k-a intrare, incepand de la cea cu numar maxim de aparitii spre cea cu numar minim de aparitii

Observatie!

Datele din interiorul unui treap sunt ordonate atat in functie de numarul lor de aparitii, cat si in functie de 
ordinea alfabetica (in cazul in care numarul de aparitii coincide).

Acest lucru se realizeaza in metoda "insert" din "Treap", utilizand supraincarcarea operatorilor din clasa "entry". 
Astfel, in inserarea unui element, in cazul numarului de aparitii egal, acesta este "mai mare" (stocat in dreapta), 
daca stringul lui este "mai mic" din punct de vedere alfabetic decat cel al elementului cu care este comparat.

4. "Trie"-ul permite apelarea a 2 functii importante:

- "insert" : cauta in interiorul structurii pozitia unde trebuie inserat un nou string si apeleaza inserarea din "treap"; 
"copiii" fiecarui nod din "trie" sunt stocati intr-un vector de 8 elemente; pozitia "i" din acest vector corespunde cifrei "i+2", 
deoarece tastele folosite de utilizator sunt de la 2 la 9;

Observatie!

"level" parcurge vectorul de cifre obtinut in "resolve_query" din stringul introdus de utilizator si ajuta la calcularea pozitiei 
unde trebuie continuata inserarea; totodata reprezinta nivelul la care am ajuns in trie.

exemplu: pornim de la radacina ( level = 0 ); copilul unde trebuie sa inserez va fi pe pozitia "prima cifra din vector" - 2; 
dupa prima inserare vom fi pe nivelul 1, verificand "cea de-a doua cifra din vector", etc. 

- "find" : functie menita sa gaseasca stringul potrivit sirului de cifre introdus de utilizator si sa il afiseze in "date.out";
gasirea pozitiei cuvantului in trie se face asemanator cu cea de la inserare: recursiv, din "copil" in "copil", pentru fiecare cifra; 
dupa determinarea acestei pozitii se apeleaza metoda "findK" din "Treap".

Pentru o intelegere mai buna a metodei de rezolvare pot fi citite comentariile din cadrul codului sursa.
