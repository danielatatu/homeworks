Student: Daniela Florentina Tatu
Grupa: 325 CA

In continuare voi detalia ideea generala de implementare a temei.

Simulation manager este "centrul de comanda", care preia datele de intrare si:

* creeaza reteaua de centre de mesagerie in doua etape:
	* citeste numele centrelor si componentele atribuite fiecaruia si le salveaza intr-un ArrayList
	  utilizand metoda 'createCentersList' din clasa 'Factory'
	* pargurge lista de centre si completeaza lista de vecini a fiecaruia pe baza fisierului de configurare
	  utilizand metoda 'createNetwork' din clasa 'Factory'

* preia comenzile de la stdin si trimite task-urile catre singurul MessageCenter la care e conectat;
aceasta actiune se desfasoara in metoda 'start', in mai multe etape:
	* crearea mesajului de load si incarcarea imaginii prin utilizarea metodei publish a MessageCenter-ului
	* procesarea comenzii primite la pasul de precaptura si transmiterea task-urilor 
	  de 'flash' si 'zoom' catre MessageCenter cu ajutorul metodei 'precapture' din clasa 'Command'
	* procesarea comenzii primite la pasul de captura si transmiterea task-urilor de 'raw' sau 'normal' photo 
	  catre MessageCenter cu ajutorul metodei 'capture' din clasa 'Command'
	* procesarea comenzii primite la pasul de postcaptura si transmiterea task-urilor de 'sepia', 'blur' sau 
	  'black and white' catre MessageCenter cu ajutorul metodei 'postcapture' din clasa 'Command'
	* crearea mesajului de save si salvarea imaginii prin utilizarea metodei publish a MessageCenter-ului

MessageCenter-ul primeste task-urile incapsulate in clasa Message, isi afiseaza numele la consola pentru 
a semnala faptul ca mesajul a ajuns la el, apoi apeleaza meotda 'publishAlgorithm', care:
	* verifica lista de id-uri ale mesajelor procesate pentru a evita reevaluarea unui task
	* cauta in lista 'components' o componenta care stie sa rezolve acel tip de task utilizand metoda notify
	* daca nu gaseste, transmite task-ul mai departe vecinilor sai din lista 'connectedCenters'

'SpecializedMessageCenter' este o clasa ce extinde 'MessageCenter' si implementeaza metodele abstracte ale acesteia, 
adaugand de asemenea listele 'processedMessages', 'components' si 'connectedCenters'.