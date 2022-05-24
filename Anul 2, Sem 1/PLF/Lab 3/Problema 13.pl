% colorare(N - Integer, M - Integer, Rez - List)
% N - numarul de tari
% M - Numarul de culori
% Rez - lista rezultat
colorare(N, M, Rez) :-  lista(M, L),
                        back(N, 0, L, Rez, []).

% lista(M - Integer, L - List)
% M - numarul pana la care se genereaza lista
% L - lista rezultata
lista(M, L) :- generare(M, [], L).

% generare(M - Integer, X - List, R - List)
% M - numarul pana la care se genereaza
% X - lista colectoare
% R - lista rezultat
% Model matematic:
% generare(M, X) = lista rezultat, m = 0
%                  generare(M-1, M + X), altfel
% Model de flux: (i, i, o), (i, i, i) nedeterminist
generare(0, X, X):- !.
generare(M, X, R) :- M1 is M - 1,
                     generare(M1, [M|X], R).

% candidat(L - List, R - Integer)
% L - Lista de unde extragem pe rand numere
% R - Numarul extras
% Model matematic:
% candidat(l1l2..ln) = 1. l1, n > 0
%                         2. candidat(l2l3..ln), n > 0
% Model de flux: (i, o) determinist
candidat([H|_],H).
candidat([_|T],R):- candidat(T,R).

% continuare(L - List)
% L - Lista pe care o verificam
continuare([_]) :- !.
continuare([H,C|_]) :- H \= C.


% back(N - Integer, Poz - Integer, Rez - List, Col - List)
% N - numarul de tari, pozitia maxima
% Poz - pozitia pe care adaug
% Rez - lista rezultat
% Col - lista colectoare
% Model matematic:
% back(N, Poz, Col) = 1. Col                                 , Poz = N
%                     2. back(N, Poz+1, Col + candidat(L))   , Poz < N
back(N, N, _, Col, Col).
back(N, Poz, L, Rez, Col) :- Poz < N,
                             candidat(L, E),
                             Col1 = [E|Col],
                             continuare(Col1),
                             Poz1 is Poz + 1,
                             back(N, Poz1, L, Rez, Col1).

