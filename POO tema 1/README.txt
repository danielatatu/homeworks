Student: Daniela Florentina Tatu
Grupa: 325 CA

Avand in vedere comentariile consistente pe care le contin sursele mele,  
m-am gandit ca in acest fisier sa nu mai plictisesc cu detalii, 
ci sa prezint ideea generala de implementare.

==> pachetul "heroes" este format din:
	- o clasa abstracta (Hero) ce contine:
		- parametrii specifici fiecarui erou (HP, XP, etc.)
		- metodele care se aplica in mod indentic fiecarui tip de erou
		(getMaxHP, getDamage, takeDOT, move, levelUp, endFightWith, etc)
		- metodele abstracte fightWith si acceptFightWith, implementate in
	clasele care o mostenesc:
	- Knight
	- Pyromancer
	- Rogue
	- Wizard

Folosind ideea double dispatch, am implementat cate 4 metode fightWith ce primesc ca parametru 
un tip specific de erou si o metoda acceptFightWith ce primeste ca parametru un Hero. Astfel, 
cand parcurg vectorul de eroi, pot sa initiez o lupta fara sa fiu nevoita sa fac downcast in metodele respective.

==> pachetul "abilities" este format analog, dintr-o clasa abstracta "Ability" si alte 8 clase ce o mostenesc.

Fiecare clasa contine metoda applyTo de 4 ori (1 pentru fiecare tip de erou), 
care primeste ca parametri tipul de erou careia i se aplica, level-ul eroului care o aplica si 
terenul pe care se desfasoara lupta. Aceasta metoda calculeaza damage-ul in functie de modificatori 
si il aplica eroului primit ca parametru (adversarului), folosind metoda setHP().

==> pachetul "game" este un fel de "centru de comanda".

	Contine:
- clasa Map, care memoreaza harta jocului intr-o matrice N x M
- clasa Round, care retine miscarile pe care eroii trebuie sa le faca in runda curenta
- clasa Game, care contine vectorul de eroi si vectorul de runde
	De asemenea in clasa game se gasesc metodele:
	- play: realizeaza executarea tuturor actiunilor din cadrul unui joc
	- end: marcheaza finalul unui joc si afiseaza informatiile despre eroi




