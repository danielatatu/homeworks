Tatu Daniela-Florentina
grupa: 325 CA

Intrucat sursele mele sunt foarte bogate in comentarii,
am sa precizez doar ideea generala de rezolvare.

Am utilizat o functie de procesare a unei pagini, in cadrul careia:
 *  se apeleaza functia de  download
 *  se obtin link-urile ce trebuie procesate 
 * 	daca modurile -e sau -r sunt setate
 *  se proceseaza aceste link-uri
 *  se efectueaza mutarea in directorul initial
 *		(in care se afla programul inainte de download)
 
Functia de download trimite cererea catre server in formatul HTTP
si descarca apoi pagina in calupuri de date de o dimensiune prestabilita.
