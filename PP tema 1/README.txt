Student: Daniela Florentina Tatu
Grupa: 325 CA

Avand in vedere comentariile consistente pe care le contine sursa mea, 
m-am gandit ca in acest fisier sa prezint ideea generala de implementare.

==> REPREZENTAREA  ARBORELUI

;;    shape
;;     / \
;; round square
;;   /     \
;; yes     no

  '(none node shape
         (round leaf yes)
         (square leaf no)
    )

;; fiecare nod este reprezentat ca o lista, astfel:
;;
;; - pe prima pozitie contine valoarea atributului din nodul parinte 
;; pentru toate exemplele din nodul curent

exemplu:
-> cele 2 frunze au 'round si 'square
-> radacina are 'none (singurul nod din arbore care are aceasta valoare 
						pe prima pozitie, deoarece nu are nod parinte)

;;
;; - pe a doua pozitie tipul nodului

poate fi: 'node, 'leaf, 'default, 'majority

;;
;; - pe a treia pozitie:
;;           -> numele clasei, daca e frunza
;;           -> numele atributului, daca e nod
;;
;; - de la a patra pozitie pana la final
;; copii nodului curent (daca nu e frunza)


==> FUNCTIILE DE CALCULARE A ENTROPIEI SI A CASTIGULUI INFORMATIONAL 
sunt explicate detaliat in comentarii si urmeaza formulele date in enuntul temei


==> CREAREA ARBORELUI

Am utilizat o functie auxiliara numita < diff-classes? > 
care verifica daca un set de exemple contine clase diferite

;; daca nu, intoarce false
;; daca da, intoarce clasa majoritara (util pentru bonus)


Arborele se creeaza recursiv, urmand algoritmul:

;; daca nu mai am exemple creez o frunza < default >
;; daca toate exemplele au aceeasi clasa creez o frunza normala 
;; daca au clase diferite, dar nu mai am atribute dupa care sa impart creez o frunza < majority >
;;
;; altfel
;; sortez lista atributelor ramase descrescator, in functie de castigul informational
;; aleg atributul cu castig informational maxim
;;
;; concatenez 2 liste:
  	-> prima contine valoarea atributului din parinte, tipul si numele atributului curent
  	-> a doua contine lista de noduri copii ai nodului curent; aceasta se obtine aplicand 
	apelul recursiv pe toate valorile atributului cu castig informational maxim


La fiecare pas al recursivitatii retin:

;; valoarea atributului din nodul parinte
;; clasa majoritara a exemplelor din nodul p?rinte
;; setul curent de exemple (in recursivitate se realizeaza impartirea setului de exemple 
;;			in functie de valorile pe care le au pentru atributul cu cel mai mare castig)
;; atributele ramase




