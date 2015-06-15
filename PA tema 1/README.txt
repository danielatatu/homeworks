
Student: Tatu Daniela Florentina
Grupa: 325CA


Algoritmul de la care am pornit poate fi gasit aici [1].

Pentru o mai buna intelegere a metodei de rezolvare, 
voi explica modul in care se gaseste a k-a suma pe un exemplu.

Fie vectorul de numere: 1 2 4 5 8.

Am ales ca structura ce retine sumele generate o "matrice virtuala". 
Nu vom crea propriu-zis aceasta matrice, insa ne-o imaginam. 
Pentru exemplul de mai sus are forma:

pozitii  0   1   2   3   4
  ...........................
  .  nr  1   2   4   5   8
  .    ......................    
0 . 1  . 2   3   5   6   9
  .    .
1 . 2  . 3   4   6   7  10
  .    .
2 . 4  . 5   6   8   9  12
  .    .
3 . 5  . 6   7   9  10  13
  .    .
4 . 8  . 9  10  12  13  16  sume
  .    .

Se poate observa ca aceasta matrice este sortata crescator atat pe linii, 
cat si pe coloane (de aceea initial se face sortarea vectorului de numere).

Astfel, data fiind o suma, daca pe linia i sunt x sume mai mici decat ea, 
pe linia i-1 vor fi cel putin x sume mai mici decat ea.

Exemplu: pentru suma 8 (4 + 4)

- pe linia 4 nu este nicio suma mai mica
- pe linia 3 sunt 2
- pe linia 2 sunt tot 2
- pe linia 1 sunt 4
- pe linia 0 sunt tot 4

Stiind aceste informatii putem afla pozitia unei sume in O(n), 
calculand cate sume sunt mai mici decat ea, acest lucru fiind realizat in functia 'evaluate'.

Sunt parcurse liniile de jos in sus, retinand cate sume de pe fiecare linie 
sunt mai mici sau egale cu suma data ca parametru. Daca la linia i 
s-au gasit j sume mai mici, la linia i-1 se porneste verificarea de la acest j, 
care se incrementeaza daca mai gasesc si alte sume mai mici sau ramane identic, altfel.

Adunand j de la fiecare linie obtinem totalul de sume mai mici sau egale cu suma data, 
iar in k retinem cate din ele sunt egale, gasind astfel o pozitie minima si o pozitie maxima 
pe care se poate afla suma in setul de sume.


Utilizand aceasta functie este usor de implementat acum o "cautare binara" 
pe aceasta "structura", pornind de la cea mai mica (v[0] + v[0]), 
respectiv cea mai mare (V[N-1] + v[N-1]) suma. 
Recursivitatea se opreste in momentul in care diferenta dintre cele 2 sume 
primite ca parametri este 1 sau cand K se afla intre 
pozitia minima si pozitia maxima a sumei medii generate.


[1]  http://www.cse.yorku.ca/~andy/pubs/X%2BY.pdf