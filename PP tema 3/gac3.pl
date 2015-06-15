%% Paradigme de Programre 2015
%% Tema 3 / Prolog / CSP
%% student: Tatu Daniela-Florentina
%% grupa: 325CA


%% gac3/5
%% gac3(+Vars, +Domains, +Constraints, +HyperArcs, -RevisedDomains)
%%
gac3(_, RevisedDomains, _, [], RevisedDomains) :- !.
gac3(Vars, Domains, Constraints, [hyperarc(X, Ys, C) | Hyperarcs], RevisedDomains) :- 

	% utilizand pozitia lui X, obtinem domeniul sau
	nth0(I, Vars, V),
	V==X, !,
	nth0(I, Domains, Dom, Rest),

	% obtinem si domeniile variabilelor Ys din hiperarcul curent
	findall(D, (member(Y, Ys), nth0(J, Vars, Var), Var==Y, nth0(J, Domains, D)), Ds), !,

	% restrangem domeniul lui X
	findall(Val, (member(Val, Dom), X=Val, verify(Ys, Ds, C)), RevDom), !,
	
	% daca domeniul s-a modificat
	Dom\==RevDom->
		( nth0(I, RevDomains, RevDom, Rest), % punem in lista de domenii noua sa valoare
		 setof(H, Ws^Cons^(member(constraint(Ws,Cons), Constraints), 
							C\==Cons, getHyperarc(X, Ws, Cons, H)), Hs),
		 append(Hyperarcs, Hs, NewHyperarcs), % adaugam toate hiperarcele care depind de X
		 gac3(Vars, RevDomains, Constraints, NewHyperarcs, RevisedDomains), ! );
	% daca domeniul nu s-a modificat
		gac3(Vars, Domains, Constraints, Hyperarcs, RevisedDomains).
		

%% verify/3
%% verify(+Ys, +Ds, +C)
%%
%% Verifica daca exista o combinatie de valori ale variabilelor Ys
%% astfel incat C sa fie satisfacuta.
%%
verify(Ys, Ds, C) :-
	maplist(member, Vs, Ds),
	maplist((=), Ys, Vs),
	C, !.
		

%% getHyperarc/4
%% getHyperarc(+X, +Vars, +Cons, -Hyperarc)
%%
%% Pornind de la o variabila X si o constrangere,
%% daca X se afla printre variabilele constrangerii,
%% se determina toate hiperarcele avand ca variabila principala
%% fiecare dintre celelalte variabile ale constrangerii respective.
%%
getHyperarc(X, Vars, Cons, hyperarc(Y, Rest, Cons)) :-
	nth0(I, Vars, V), 
	V==X, !,
	nth0(J, Vars, Y, Rest),
	I\==J.
	


%% solve_csp/4
%% solve_csp(+Vars, +Domains, +Constraints, -Solution)
%%
solve_csp(Vars, Domains, Constraints, Solution) :-

	% initial restrangem domeniile tinand cont de toate constrangerile
	getAll(Constraints, [], Hs),
	% setof(hyperarc(V, Rest, C), Vs^C^(member(constraint(Vs, C), Constraints), nth0(_, Vs, V, Rest)), Hs), !,
	gac3(Vars, Domains, Constraints, Hs, RevisedDomains), !,

	% ulterior aplicam algoritmul si obtinem solutia
	helper(0, Vars, RevisedDomains, Constraints, Solution).
	

%% getAll/3
%% getAll(+Constraints, +InitialHypsList, -FinalHypsList)
getAll([], All, All) :- !.
getAll([constraint(Vs, C) | Constraints], Initial, All) :-
	setof(H, X^R^(nth0(_, Vs, X, R), H=hyperarc(X, R, C)), Hs),
	append(Initial, Hs, New),
	getAll(Constraints, New, All).
	

%% helper/5
%% helper(+Pos, +Vars, +Domains, +Constraints, -Solution)
%%
%% Se atribuie pe rand valori fiecarei variabile, pornind de la pozitia 0.
%% Cand se ajunge la final, fiecare domeniu are o singura valoare,
%% deci se poate extrage solutia.
%%
helper(Pos, Vars, Domains, _, Solution) :- 
	length(Vars, Pos),
	findall(V, member([V], Domains), Solution), !.

helper(Pos, Vars, Domains, Constraints, Solution) :-
	% X = variabila curenta
	nth0(Pos, Vars, X),
	% Dom = domeniul variabilei curente
	nth0(Pos, Domains, Dom, Rest), !,

	% atribuim variabilei curente pe rand cate o valoare
	% din domeniul sau
	member(Val, Dom),
	nth0(Pos, RevDoms, [Val], Rest),

	% obtinem hiperarcele care depind de variabila curenta
	setof(H, Vs^C^( member(constraint(Vs, C), Constraints), getHyperarc(X, Vs, C, H) ), Hs),

	% revizuim toate domeniile cu gac3
	gac3(Vars, RevDoms, Constraints, Hs, RevisedDomains),

	% trecem la pozitia urmatoare
	NextPos is Pos + 1,
	helper(NextPos, Vars, RevisedDomains, Constraints, Solution).
	


% BONUS

%% einstein/4
%% einstein(-Vars, -FishNationality, -Domains, -Constraints)
einstein(Vars, FishNationality, Domains, Constraints) :-

	% house, nationality, cigarette, beverage, animal
	Vars=[H1, N1, C1, B1, A1,
		  H2, N2, C2, B2, A2,
		  H3, N3, C3, B3, A3,
		  H4, N4, C4, B4, A4,
		  H5, N5, C5, B5, A5],

    Dom=[[red, white, green, yellow, blue], % house
		[english, danish, swedish, norwegian, german], % nationality
		[pallmall, dunhills, blend, bluemasters, prince], % cigarette
		[tea, beer, coffee, milk, water], % beverage
		[dogs, birds, cats, horses, fish]], % animal
	% aceleasi domenii pentru fiecare persoana in parte
	length(Doms, 5),
    maplist(=(Dom), Doms),
	foldl(append, Doms, [], Domains),

				% toate casele au culori diferite
	Constraints=[constraint([H1,H2,H3,H4,H5],(H1\==H2,H1\==H3,H1\==H4,H1\==H5,H2\==H3,H2\==H4,H2\==H5,H3\==H4,H3\==H5,H4\==H5)),
				% toti oamenii sunt de nationalitati diferite
				 constraint([N1,N2,N3,N4,N5],(N1\==N2,N1\==N3,N1\==N4,N1\==N5,N2\==N3,N2\==N4,N2\==N5,N3\==N4,N3\==N5,N4\==N5)),
				% toti fumeaza marci diferite de tigari
				 constraint([C1,C2,C3,C4,C5],(C1\==C2,C1\==C3,C1\==C4,C1\==C5,C2\==C3,C2\==C4,C2\==C5,C3\==C4,C3\==C5,C4\==C5)),
				% toti prefera bauturi diferite
				 constraint([B1,B2,B3,B4,B5],(B1\==B2,B1\==B3,B1\==B4,B1\==B5,B2\==B3,B2\==B4,B2\==B5,B3\==B4,B3\==B5,B4\==B5)),
				% toti cresc animale diferite
				 constraint([A1,A2,A3,A4,A5],(A1\==A2,A1\==A3,A1\==A4,A1\==A5,A2\==A3,A2\==A4,A2\==A5,A3\==A4,A3\==A5,A4\==A5)),
				% englezul locuieste in casa rosie
				 constraint([N1,H1,N2,H2,N3,H3,N4,H4,N5,H5],(N1==english,H1==red; N2==english,H2==red; N3==english,H3==red;
															 N4==english,H4==red; N5==english,H5==red)),
				% suedezul are caini
				 constraint([N1,A1,N2,A2,N3,A3,N4,A4,N5,A5],(N1==swedish,A1==dogs; N2==swedish,A2==dogs; N3==swedish,A3==dogs;
															 N4==swedish,A4==dogs; N5==swedish,A5==dogs)),
				% danezul bea ceai
				 constraint([N1,B1,N2,B2,N3,B3,N4,B4,N5,B5],(N1==danish,B1==tea; N2==danish,B2==tea; N3==danish,B3==tea;
															 N4==danish,B4==tea; N5==danish,B5==tea)),
				% casa verde este in stanga celei albe
				 constraint([H1,H2,H3,H4,H5],(H1==green,H2==white; H2==green,H3==white; H3==green,H4==white; H4==green,H5==white)),
				% stăpânul casei verzi bea cafea
				 constraint([H1,B1,H2,B2,H3,B3,H4,B4,H5,B5],(H1==green,B1==coffee; H2==green,B2==coffee; H3==green,B3==coffee;
															 H4==green,B4==coffee; H5==green,B5==coffee)),
				% fumătorul de Pall Mall are păsări
				 constraint([C1,A1,C2,A2,C3,A3,C4,A4,C5,A5],(C1==pallmall,A1==birds; C2==pallmall,A2==birds; C3==pallmall,A3==birds;
															 C4==pallmall,A4==birds; C5==pallmall,A5==birds)),
				% stăpânul casei galbene fumează Dunhills
				 constraint([C1,H1,C2,H2,C3,H3,C4,H4,C5,H5],(H1==yellow,C1==dunhills; H2==yellow,C2==dunhills; H3==yellow,C3==dunhills;
															 H4==yellow,C4==dunhills; H5==yellow,C5==dunhills)),
				% omul din casa din mijloc bea lapte
				 constraint([B3],B3==milk),
				% norvegianul locuiește în prima casă
				 constraint([N1],N1==norwegian),
				% fumătorul de Blend are un vecin nebun ce ține pisici
				 constraint([C1,A1,C2,A2,C3,A3,C4,A4,C5,A5],(C1==blend,A2==cats; C2==blend,(A1==cats;A3==cats); C3==blend,(A2==cats;A4==cats);
															 C4==blend,(A3==cats;A5==cats); C5==blend,A4==cats)),
				% fumătorul de Blue Masters bea bere
				 constraint([C1,B1,C2,B2,C3,B3,C4,B4,C5,B5],(C1==bluemasters,B1==beer; C2==bluemasters,B2==beer; C3==bluemasters,B3==beer;
															 C4==bluemasters,B4==beer; C5==bluemasters,B5==beer)),
				% bărbatul care are cai locuiește lângă fumătorul de Dunhills
				 constraint([C1,A1,C2,A2,C3,A3,C4,A4,C5,A5],(C1==dunhills,A2==horses; C2==dunhills,(A1==horses,A3==horses); 
							C3==dunhills,(A2==horses,A4==horses); C4==dunhills,(A3==horses,A5==horses); C5==dunhills,A4==horses)),
				% germanul fumează Prince
				 constraint([N1,C1,N2,C2,N3,C3,N4,C4,N5,C5],(N1==german,C1==prince; N2==german,C2==prince; N3==german,C3==prince;
															 N4==german,C4==prince; N5==german,C5==prince)),
				% norvegianul locuiește lângă casa albastră
				 constraint([N1,H1,N2,H2,N3,H3,N4,H4,N5,H5],(N1==norwegian,H2==blue; N2==norwegian,(H1==blue;H3==blue); 
							N3==norwegian,(H2==blue;H4==blue); N4==norwegian,(H3==blue;H5==blue); N5==norwegian,H4==blue)),
				% fumătorul de Blend are un vecin a cărui băutură favorită este apa
				 constraint([C1,B1,C2,B2,C3,B3,C4,B4,C5,B5],(C1==blend,B2==water; C2==blend,(B1==water;B3==water); C3==blend,(B2==water;B4==water);
															 C4==blend,(B3==water;B5==water); C5==blend,B4==water))],
	% obtine solutia
    solve_csp(Vars, Domains, Constraints, Solution),

	% in functie de indexul unde se afla pestele
	% determinam indexul nationalitatii persoanei ce il detine
	nth0(I, Solution, fish),
	In is I-3,
	nth0(In, Solution, FishNationality).