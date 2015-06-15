%% (C) Tudor Berariu, 2015 <tudor.berariu@gmail.com>

%% Paradigme de Programare :: 2015 :: Prolog :: Tema 3 :: CSP :: Tester

:-['gac3.pl'].

%% ----------------------------------------------------------------------------
%% Testul 0 : Colorarea hÄrČilor
%% -----------------------------
%% PROBLEMA: sÄ se coloreze o hartÄ cu 5 ČÄri folosind culorile roČu, verde Či
%% albastru astfel ĂŽncĂ˘t oricare douÄ ČÄri vecine sÄ fie coloare diferit.
%%
%% Se va codifica astfel:
%%  - culoarea cu care va fi coloratÄ fiecare ČarÄ va reprezenta o variabilÄ
%%  - toate variabilele au acelaČi domeniu [r,g,b]
%%  - pentru toate ČÄrile vecine X, Y vom avea o constrĂ˘ngere care va impune
%%    ca acestea sÄ aibÄ valori diferite:
%%        constraint([X, Y],X\==Y).
%%  - Pentru a evita soluČii simetrice Či a reduce spaČiul cÄutÄrii, vom alege
%%    ca primele douÄ ČÄri vecine A Či B sÄ fie colorate cu douÄ culori
%%    diferite fixate:
%%        constraint([A], A==r).
%%        constraint([B], B==g).

%% Test pentru arc-consistenČÄ (gac3)
test_gac3_0(RevisedDomains):-
    Vars = [A, B, C, D, E],
    Domains = [[r,g,b], [r,g,b], [r,g,b], [r,g,b], [r,g,b]],
    Hyperarcs = [hyperarc(A,[],A==r)],
    Constraints = [constraint([A],A==r),
                   constraint([A,B],A\==B), constraint([A,C],A\==C),
                   constraint([A,D],A\==D), constraint([B,C],B\==C),
                   constraint([B,E],B\==E), constraint([C,D],C\==D),
                   constraint([C,E],C\==E), constraint([D,E],D\==E)],
	gac3(Vars, Domains, Constraints, Hyperarcs, RevisedDomains),
	!,
	maplist(var, Vars).

test_0a:-
    test_gac3_0(RevisedDomains),
    !,
    maplist(sort, RevisedDomains, Sorted),
    Sorted == [[r],[b,g],[b,g],[b,g],[b,g,r]].

%% Test pentru MAC (solve_csp)
test_mac_0(Solution):-
    Vars = [A, B, C, D, E],
    Domains = [[r,g,b], [r,g,b], [r,g,b], [r,g,b], [r,g,b]],
    Constraints = [constraint([A],A==r), constraint([B],B==g),
                   constraint([A,B],A\==B), constraint([A,C],A\==C),
                   constraint([A,D],A\==D), constraint([B,C],B\==C),
                   constraint([B,E],B\==E), constraint([C,D],C\==D),
                   constraint([C,E],C\==E), constraint([D,E],D\==E)],
    solve_csp(Vars, Domains, Constraints, Solution),
    ground(Solution).

test_0b:-
    bagof(Solution, test_mac_0(Solution), Solutions),
    Solutions == [[r,g,b,g,r]].

%% ----------------------------------------------------------------------------
%% ----------------------------------------------------------------------------

%% ----------------------------------------------------------------------------
%% Testul 1 : Colorarea hÄrČilor
%% -----------------------------
%% PROBLEMA: sÄ se coloreze o hartÄ cu 7 ČÄri folosind culorile roČu, verde Či
%% albastru astfel ĂŽncĂ˘t oricare douÄ ČÄri vecine sÄ fie coloare diferit.
%%
%% Se va codifica astfel:
%%  - culoarea cu care va fi coloratÄ fiecare ČarÄ va reprezenta o variabilÄ
%%  - toate variabilele au acelaČi domeniu [r,g,b]
%%  - pentru toate ČÄrile vecine X, Y vom avea o constrĂ˘ngere care va impune
%%    ca acestea sÄ aibÄ valori diferite:
%%        constraint([X, Y],X\==Y).
%%  - Pentru a evita soluČii simetrice Či a reduce spaČiul cÄutÄrii, vom impune
%%    culoarea cu care va fi coloratÄ prima ČarÄ:
%%        constraint([A], A==r).

%% Test pentru arc-consistenta (gac3)
%% AtenČie, din domeniul lui b lipseČte 'b'
test_gac3_1(RevisedDomains):-
    Vars = [A, B, C, D, E, F, G],
    Domains = [[r,g,b],[r,g],[r,g,b],[r,g,b],[r,g,b],[r,g,b],[r,g,b]],
    Hyperarcs = [hyperarc(A,[],A==r)],
    Constraints = [constraint([A],A==r),
                   constraint([A,B],A\==B), constraint([A,C],A\==C),
                   constraint([A,E],A\==E), constraint([B,C],B\==C),
                   constraint([B,D],B\==D), constraint([C,D],C\==D),
                   constraint([C,E],C\==E), constraint([C,F],C\==F),
                   constraint([D,G],D\==G), constraint([E,F],E\==F),
                   constraint([F,G],F\==G)],
    gac3(Vars, Domains, Constraints, Hyperarcs, RevisedDomains),
    !,
    maplist(var, Vars).
	

test_1a:-
    test_gac3_1(RevisedDomains),
    maplist(sort, RevisedDomains, Sorted),
    Sorted == [[r],[g],[b],[r],[g],[r],[b,g]].

%% Test pentru MAC (solve_csp)

test_mac_1(Solution):-
    Vars=[A, B, C, D, E, F, G],
    Domains=[[r,g,b], [r,g,b], [r,g,b], [r,g,b], [r,g,b], [r,g,b], [r,g,b]],
    Constraints = [constraint([A],A==r),
                   constraint([A,B],A\==B), constraint([A,C],A\==C),
                   constraint([A,E],A\==E), constraint([B,C],B\==C),
                   constraint([B,D],B\==D), constraint([C,D],C\==D),
                   constraint([C,E],C\==E), constraint([C,F],C\==F),
                   constraint([D,G],D\==G), constraint([E,F],E\==F),
                   constraint([F,G],F\==G)],
    solve_csp(Vars, Domains, Constraints, Solution),
    ground(Solution).

test_1b:-
    bagof(Solution, test_mac_1(Solution), Solutions),
    sort(Solutions, Sorted),
    Sorted == [[r,b,g,r,b,r,b],[r,b,g,r,b,r,g],[r,g,b,r,g,r,b],[r,g,b,r,g,r,g]].

%% ----------------------------------------------------------------------------

%% ----------------------------------------------------------------------------
%% Testul 2 : PÄtratul fermecat (sau "pÄtratul vrÄjit")
%% ----------------------------------------------------
%% PROBLEMA: sÄ se completeze un pÄtrat de dimensiune 3x3 cu numerele de l la 9
%% astfel ĂŽncĂ˘t suma pe fiecare linie, fiecare coloanÄ Či fiecare diagonalÄ sÄ fie
%% 15
%% Se va codifica astfel:
%%  - fiecare dintre cele 9 celule ale pÄtratului va reprezenta o variabilÄ
%%  - domeniul fiecÄrei variabile va fi lista numerelor de la 1 la 9
%%  - vom impune constrĂ˘ngeri pentru sume:
%%        constraint([X,Y,Z], X + Y + Z =:= 15).
%%    dar Či pentru a avea toate cele 9 variabile distincte.
%%

%% Test pentru arc-consistenČÄ (gac3)

%% Testul introduce douÄ condiČii artificiale: X11 = 2 Či X12 = 7 sau 9

test_gac3_2(RevisedDomains):-
    Vars = [X11, X12, X13, X21, X22, X23, X31, X32, X33],
    length(Domains, 9),
    maplist(=([1,2,3,4,5,6,7,8,9]), Domains),
    %% Cifrele nu au voie sÄ se repete ĂŽn cadrul pÄtratului fascinant
    bagof(constraint([X,Y],X=\=Y),
          L1^L2^L3^L4^(append(L1, [X|L2], Vars), append(L3, [Y|L4], L2)),
          AllDifferent),
    append(AllDifferent,
           [constraint([X11,X12,X13],15 =:= X11 + X12 + X13),
            constraint([X21,X22,X23],15 =:= X21 + X22 + X23),
            constraint([X31,X32,X33],15 =:= X31 + X32 + X33),
            constraint([X11,X21,X31],15 =:= X11 + X21 + X31),
            constraint([X12,X22,X32],15 =:= X12 + X22 + X32),
            constraint([X13,X23,X33],15 =:= X13 + X23 + X33),
            constraint([X11,X22,X33],15 =:= X11 + X22 + X33),
            constraint([X13,X22,X31],15 =:= X13 + X22 + X31)],
           Constraints),
    Hyperarcs = [hyperarc(X11,[],X11 is 2),
                 hyperarc(X12,[],(X12 is 7 ; X12 is 9))],
    gac3(Vars, Domains, Constraints, Hyperarcs, RevisedDomains),
    !,
    maplist(var, Vars).

test_2a:-
    test_gac3_2(RevisedDomains),
    maplist(sort, RevisedDomains, Sorted),
    Sorted == [[2],[7,9],[4,6],[7,9],[5,7],[1,3],[4,6],[1,3],[6,8]].

%% Test pentru MAC (solve_csp)

test_mac_2(Solution):-
    Vars = [X11, X12, X13, X21, X22, X23, X31, X32, X33],
    length(Domains, 9),
    maplist(=([1,2,3,4,5,6,7,8,9]), Domains),
    %% Cifrele nu are voie sÄ se repete ĂŽn cadrul pÄtratului fabulos
    bagof(constraint([X,Y],X=\=Y),
          L1^L2^L3^L4^(append(L1, [X|L2], Vars), append(L3, [Y|L4], L2)),
          AllDifferent),
    append(AllDifferent,
           [constraint([X11,X12,X13],15 =:= X11 + X12 + X13),
            constraint([X21,X22,X23],15 =:= X21 + X22 + X23),
            constraint([X31,X32,X33],15 =:= X31 + X32 + X33),
            constraint([X11,X21,X31],15 =:= X11 + X21 + X31),
            constraint([X12,X22,X32],15 =:= X12 + X22 + X32),
            constraint([X13,X23,X33],15 =:= X13 + X23 + X33),
            constraint([X11,X22,X33],15 =:= X11 + X22 + X33),
            constraint([X13,X22,X31],15 =:= X13 + X22 + X31)],
           Constraints),
    solve_csp(Vars, Domains, Constraints, Solution),
    ground(Solution).

test_2b:-
    bagof(Solution, test_mac_2(Solution), Solutions),
    sort(Solutions, Sorted),
    Sorted == [[2,7,6,9,5,1,4,3,8], [2,9,4,7,5,3,6,1,8], [4,3,8,9,5,1,2,7,6],
               [4,9,2,3,5,7,8,1,6], [6,1,8,7,5,3,2,9,4], [6,7,2,1,5,9,8,3,4],
               [8,1,6,3,5,7,4,9,2], [8,3,4,1,5,9,6,7,2]].

%% ----------------------------------------------------------------------------

%% ----------------------------------------------------------------------------
%% Testul 3 : Čase doamne frumoase
%% ----------------------------------------------------
%% PROBLEMA: sÄ se aČeze Čase regine pe o tablÄ de Čah de dimensiune Čase ori
%% Čase
%%
%% Se va codifica astfel:
%%  - coloana pe care va fi plasata fiecare reginÄ va fi o variabilÄ
%%  - se vor impune constrĂ˘ngeri separate pentru fiecare pereche de regine
%%      constraint([Q1, Q2], (abs(Q1-Q2, Delta), Delta =\= 1, Delta =\= 0)).

%% Test pentru arc-consistenČÄ (gac3)

%% Am fixat prin 2 constrĂ˘ngeri dama 1 la linia 1 Či dama 2 la linia 3
%% Prin impunerea arc-consistenČei, domeniile devin nule
test_gac3_3(RevisedDomains):-
    Vars=[C1, C2, C3, C4, C5, C6],
    length(Domains, 6),
    maplist(=([1,2,3,4,5,6]), Domains),
    Constraints = [constraint([C1,C2],(abs(C1-C2, R12), R12=\=1, R12=\=0)),
                   constraint([C1,C3],(abs(C1-C3, R13), R13=\=2, R13=\=0)),
                   constraint([C1,C4],(abs(C1-C4, R14), R14=\=3, R14=\=0)),
                   constraint([C1,C5],(abs(C1-C5, R15), R15=\=4, R15=\=0)),
                   constraint([C1,C6],(abs(C1-C6, R16), R16=\=5, R16=\=0)),
                   constraint([C2,C3],(abs(C2-C3, R23), R23=\=1, R23=\=0)),
                   constraint([C2,C4],(abs(C2-C4, R24), R24=\=2, R24=\=0)),
                   constraint([C2,C5],(abs(C2-C5, R25), R25=\=3, R25=\=0)),
                   constraint([C2,C6],(abs(C2-C6, R26), R26=\=4, R26=\=0)),
                   constraint([C3,C4],(abs(C3-C4, R34), R34=\=1, R34=\=0)),
                   constraint([C3,C5],(abs(C3-C5, R35), R35=\=2, R35=\=0)),
                   constraint([C3,C6],(abs(C3-C6, R36), R36=\=3, R36=\=0)),
                   constraint([C4,C5],(abs(C4-C5, R45), R45=\=1, R45=\=0)),
                   constraint([C4,C6],(abs(C4-C6, R46), R46=\=2, R46=\=0)),
                   constraint([C5,C6],(abs(C5-C6, R56), R56=\=1, R56=\=0))],
    Hyperarcs = [hyperarc(C1,[],C1 is 1), hyperarc(C2,[],C2 is 3)],
    gac3(Vars, Domains, Constraints, Hyperarcs, RevisedDomains),
    !,
    maplist(var, Vars).

test_3a:-
    test_gac3_3(RevisedDomains),
    RevisedDomains == [[],[],[],[],[],[]].

%% Test pentru MAC (solve_csp)

test_mac_3(Solution):-
    Vars=[C1, C2, C3, C4, C5, C6],
    length(Domains, 6),
    bagof(X, between(1, 6, X), Dom),
    maplist(=(Dom), Domains),
    Constraints = [constraint([C1,C2],(abs(C1-C2, R12), R12=\=1, R12=\=0)),
                   constraint([C1,C3],(abs(C1-C3, R13), R13=\=2, R13=\=0)),
                   constraint([C1,C4],(abs(C1-C4, R14), R14=\=3, R14=\=0)),
                   constraint([C1,C5],(abs(C1-C5, R15), R15=\=4, R15=\=0)),
                   constraint([C1,C6],(abs(C1-C6, R16), R16=\=5, R16=\=0)),
                   constraint([C2,C3],(abs(C2-C3, R23), R23=\=1, R23=\=0)),
                   constraint([C2,C4],(abs(C2-C4, R24), R24=\=2, R24=\=0)),
                   constraint([C2,C5],(abs(C2-C5, R25), R25=\=3, R25=\=0)),
                   constraint([C2,C6],(abs(C2-C6, R26), R26=\=4, R26=\=0)),
                   constraint([C3,C4],(abs(C3-C4, R34), R34=\=1, R34=\=0)),
                   constraint([C3,C5],(abs(C3-C5, R35), R35=\=2, R35=\=0)),
                   constraint([C3,C6],(abs(C3-C6, R36), R36=\=3, R36=\=0)),
                   constraint([C4,C5],(abs(C4-C5, R45), R45=\=1, R45=\=0)),
                   constraint([C4,C6],(abs(C4-C6, R46), R46=\=2, R46=\=0)),
                   constraint([C5,C6],(abs(C5-C6, R56), R56=\=1, R56=\=0))],
    solve_csp(Vars, Domains, Constraints, Solution),
    ground(Solution).

test_3b:-
    bagof(Solution, test_mac_3(Solution), Solutions),
    sort(Solutions, Sorted),
    Sorted == [[2,4,6,1,3,5], [3,6,2,5,1,4], [4,1,5,2,6,3], [5,3,1,6,4,2]].

%% ----------------------------------------------------------------------------

%% ----------------------------------------------------------------------------
%% Testul 4 : N doamne frumoase
%% ----------------------------------------------------
%% PROBLEMA: sÄ se aČeze $N regine pe o tablÄ de Čah de dimensiune $N ori $N

%% Test pentru arc-consistenČÄ (gac3)

make_tuple(X, Y, (X,Y)).

test_gac3_4(RevisedDomains, N):-
    length(Vars, N),
    setof(X, between(1, N, X), Range),
    length(Domains, N),
    maplist(=(Range), Domains),
    length(Tuples, N),
    maplist(make_tuple, Vars, Range, Tuples),
    bagof(constraint([Qa, Qb], (abs(Qa-Qb, Delta), Delta =\= Diff, Delta =\= 0)),
          L1^L2^L3^L4^Ta^Tb^Ra^Rb^(append(L1, [Ta|L2], Tuples), Ta=(Qa,Ra),
                                   append(L3, [Tb|L4], L2), Tb=(Qb,Rb),
                                   abs(Ra-Rb, Diff)),
          Constraints),
    Vars = [Q1|_],
    Hyperarcs = [hyperarc(Q1,[],Q1 is N-1)],
    gac3(Vars, Domains, Constraints, Hyperarcs, RevisedDomains),
    !,
    maplist(var, Vars).

test_4a:-
    test_gac3_4(RevisedDomains, 4),
    RevisedDomains == [[3],[1],[4],[2]].

%% Test pentru MAC (solve_csp)

test_mac_4(Solution, N):-
    length(Vars, N),
    setof(X, between(1, N, X), Range),
    length(Domains, N),
    maplist(=(Range), Domains),
    length(Tuples, N),
    maplist(make_tuple, Vars, Range, Tuples),
    bagof(constraint([Qa, Qb], (abs(Qa-Qb, Delta), Delta =\= Diff, Delta =\= 0)),
          L1^L2^L3^L4^Ta^Tb^Ra^Rb^(append(L1, [Ta|L2], Tuples), Ta=(Qa,Ra),
                                   append(L3, [Tb|L4], L2), Tb=(Qb,Rb),
                                   abs(Ra-Rb, Diff)),
          Constraints),
    solve_csp(Vars, Domains, Constraints, Solution),
    ground(Solution).

test_4b:-
    bagof(Solution, test_mac_4(Solution, 4), Solutions),
    sort(Solutions, Sorted),
    Sorted == [[2,4,1,3], [3,1,4,2]].

%% ----------------------------------------------------------------------------

%% ----------------------------------------------------------------------------
%% Testul 5 : Puzzle aritmetic
%% ----------------------------------------------------
%% PROBLEMA: se dÄ o relaČie matematicÄ ĂŽntre douÄ numere ĂŽn care fiecare cifrÄ
%% a fost ĂŽnlocuitÄ cu o literÄ. SÄ se gÄseascÄ cifrele.
%%
%%   SEND +
%%   MORE
%%  -----
%%  MONEY
%%
%% Se va codifica astfel:
%%  - fiecare literÄ va reprezenta o variabilÄ
%%  - fiecare literÄ va avea domeniul [0..9], mai puČin cele care sunt prima cifrÄ
%%    dintr-un numÄr Či, deci, nu pot fi zero
%%  - constrĂ˘ngerea va fi datÄ de relaČia aritmeticÄ

%% Test pentru arc-consistenČÄ (gac3)

test_gac3_5(RevisedDomains):-
    Vars=[S, E, N, D, M, O, R, Y],
    bagof(X, between(0, 9, X), Dom),
    length(Vars, Len),
    length(Domains, Len),
    maplist(=(Dom), Domains),
    bagof(constraint([X1, X2], X1 =\= X2),
          L1^L2^L3^L4^(append(L1, [X1|L2], Vars), append(L3, [X2|L4], L2)),
          AllDifferent),
    append([constraint([S],S>0), constraint([M],(M>0, M=<1)),
            constraint(Vars,
                       (S+M)*1000+(E+O)*100+(N+R)*10+D+E =:=
                       M*10000 + O*1000 + N*100 + 10*E + Y)],
           AllDifferent,
           Constraints),
    Hyperarcs = [hyperarc(S,[],S =:= 9)],
    gac3(Vars, Domains, Constraints, Hyperarcs, RevisedDomains),
    !,
    maplist(var, Vars).

test_5a:-
    test_gac3_5(RevisedDomains),
    maplist(sort, RevisedDomains, Sorted),
    Sorted == [[9],[5,6],[6,7],[6,7],[1],[0],[8],[2,3]].

%% Test pentru MAC (solve_csp)

test_mac_5(Solution):-
    Vars=[S,E,N,D,M,O,R,Y],
    bagof(X, between(0, 9, X), Dom),
    length(Vars, Len),
    length(Domains, Len),
    maplist(=(Dom), Domains),
    bagof(constraint([X1, X2], X1 =\= X2),
          L1^L2^L3^L4^(append(L1, [X1|L2], Vars), append(L3, [X2|L4], L2)),
          AllDifferent),
    append([constraint([S],S>0), constraint([M],(M>0, M=<1)),
            constraint(Vars,
                       (S+M)*1000+(E+O)*100+(N+R)*10+D+E =:=
                       M*10000 + O*1000 + N*100 + 10*E + Y)],
           AllDifferent,
           Constraints),
    solve_csp(Vars, Domains, Constraints, Solution),
    ground(Solution).

test_5b:-
    bagof(Solution, test_mac_5(Solution), Solutions),
    Solutions = [[9,5,6,7,1,0,8,2]].

%% ----------------------------------------------------------------------------
%% ----------------------------------------------------------------------------

%% BONUS

test_einstein:-
    einstein(Vars, Nationality, Domains, Constraints),
    maplist(var, Vars),
    length(Vars, 25),
    length(Domains, 25),
    solve_csp(Vars, Domains, Constraints, _),
    !,
    Nationality == german.

%% ----------------------------------------------------------------------------
%% ----------------------------------------------------------------------------

test_gac:-
    test_0a, write('Testul 0a trecut\n'),
    test_1a, write('Testul 1a trecut\n'),
    test_2a, write('Testul 2a trecut\n'),
    test_3a, write('Testul 3a trecut\n'),
    test_4a, write('Testul 4a trecut\n'),
    test_5a, write('Testul 5a trecut\n'),
    write('Cu chiu, cu vai, toate testele pentru arc-consistenČÄ au trecut!\n'),
    !.

test_gac:-
    write('Nu au trecut toate testele pentru arc-consistenČÄ...\n'),
    write('EČti aproape, dar nu prea!\n'),
    fail.

test_mac:-
    test_0b, write('Testul 0b trecut\n'),
    test_1b, write('Testul 1b trecut\n'),
    test_2b, write('Testul 2b trecut\n'),
    test_3b, write('Testul 3b trecut\n'),
    test_4b, write('Testul 4b trecut\n'),
    test_5b, write('Testul 5b trecut\n'),
    write('Toate problemele au fost rezolvate corect!\n'),!.
test_mac:-
    write('Nu au trecut toate testele pentru MAC... SlÄbuČ!\n'),
    fail.

test:-
    test_gac,
    test_mac,
    write('Bravo, ai scÄpat Či de tema asta! ĂncÄ doi ani Či eČti inginer!\n'),
    once((test_einstein, write('Pfff... ai fÄcut Či bonusul!\n'))
         ;
         write('Faci, nu faci bonusul, seminČele aceleaČi gust are!')).