% 2a cmmmmc lista
% [H|[C|T]]
% cmmmc(X,Y) = X*Y / cmmdc(X,Y)
% cmmdc(X: integer, Y: integer, D: integer)
% cmmmcLista(L: lista, R: integer)
% L: lista initiala
% R: cmmmmc-ul listei
% model de flux: (i,i), (i,o) determinist

cmmdc(X, 0, X).
cmmdc(X, Y, D) :- Z is X mod Y,
                  cmmdc(Y, Z, D).


cmmmc(X, Y, D) :- cmmdc(X, Y, Z),
                  D is (X * Y) / Z.

cmmmcLista([],0).
cmmmcLista([H], H).
cmmmcLista([H|[C|T]], R) :- cmmmc(H,C, R1),
                               cmmmcLista([R1|T], R).


% inserare(L: lista, elem: integer, poz: integer, cnt: integer, R:lista)
% L: lista initiala
% elem: elementul care urmeaza sa fie inserat
% poz: urmatoarea pozitie dupa care urmeaza sa fie inserat
% cnt: pozitia curenta
% R: lista rezultata
% model de flux: (i,i,i,i,o), (i,i,i,i,i), determinist

inserare([], _, POZ, CNT, []) :- CNT \= POZ, !.
inserare([], E, CNT, CNT, [E]) :- !.
inserare([H|T], E, POZ, CNT, R1) :-    CNT == POZ,
                                       POZ1 is POZ+5,
                                       CNT1 is CNT+1,
                                       R1 = [H,E|R],
                                       inserare(T, E , POZ1, CNT1, R),
                                       !.

inserare([H|T], E, POZ, CNT, R1) :-    CNT1 is CNT+1,
                                       R1 = [H|R],
                                       inserare(T, E, POZ, CNT1, R).



inserareFin(L, ELEM, X) :- inserare(L, ELEM, 5, 1, X).

