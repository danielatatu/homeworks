Student: Daniela Florentina Tatu
Grupa: 325 CA

In continuare voi detalia ideea generala de implementare a temei.

Expresia matematica este analizata in clasa "ExpressionParser", 
functia "eval" intorcand rezultatul ei.

Am decis utilizarea unei clase pentru fiecare element din expresie 
(operatori, paranteze sau numere), toate fiind obiecte de tip Visitable.

Am utilizat doi "vizitatori":

	* unul pentru crearea arborelui de parsare (TreeCreator), care "viziteaza" fiecare element 
	din expresie si actioneaza diferit in functie de tipul acestuia:
		- daca e numar sau paranteza stanga, il adauga direct in stiva de rezultate, respectiv cea de operatori
		- daca e operator aritmetic (+,-,*,...) "rezolva" operatorii dinaintea lui 
		  pana la intalnirea unuia cu prioritate mai mica decat acesta
		- daca e paranteza dreapta rezolva toate operatiile pana la intalnirea unei paranteze stangi

! Rezultatele intermediare si operatorii sunt retinuti in doua stive 
("resultStack" si "operatorStack") din clasa TreeCreator.

	* unul pentru evaluarea arborelui de parsare (Evaluator), care calculeaza 
	recursiv rezultatul numeric din fiecare nod, pornind de la radacina.


Corectitudinea sintactica a expresiilor este verificata pe masura ce se creeaza arborele de parsare 
si sunt aruncate exceptii (SyntacticException) atunci cand este cazul.

Corectitudinea semantica a expresiilor este verificata pe masura ce se evalueaza arborele de parsare 
si sunt aruncate exceptii (EvaluatorException) atunci cand este cazul.
	