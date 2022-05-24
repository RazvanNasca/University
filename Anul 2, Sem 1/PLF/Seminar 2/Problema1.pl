% NrAparitii(L: lista, E: element, R: integer)
% Model de flux: (i,i,o), (i,i,i)
% L: lista in care numaram aparitiile elementului
% E: elementul pentru care se face numarul de aparitii
% R: numarul de aparitii a elementului E in lista L

nrAparitii([], E, 0) :- !.
nrAparitii([H|T], E, R) :- H \= E,
                           !,
                           nrAparitii(T, E, R).

nrAparitii([_|T], E, R) :- nrAparitii(T, E, R1),
                           R is R1 + 1.

% Sterge(L: lista rezultata, Li: lista initiala, R: Lista)
% model flux: (i,i,o) (sau (i,i,i))
% L - lista din care stergem
% Li - lista initiala
% R - lista rezultat

sterge([], _ ,[]).
sterge([Cap|Coada], Li, [Cap|R]) :- nrAparitii(Li, Cap, Nr),
                                    Nr > 1,
                                    sterge(Coada, Li, R).

sterge([Cap|Coada], Li, R) :- nrAparitii(Li, Cap, Nr),
                              Nr = 1,
                              sterge(Coada, Li, R).

stergeFin(L,R) :- sterge(L,L,R).
