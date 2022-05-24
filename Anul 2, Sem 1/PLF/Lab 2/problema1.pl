% a
suma(X, Y, Rez):- lista_numar(X, R1),
                  lista_numar(Y, R2),
                  R is R1 + R2,
                  numar_lista(R, Rez).

lista_numar(X, R) :- numar(X, 0, R).

numar([], R, R).
numar([H|T], P, R) :- P1 is P * 10 + H,
                      numar(T, P1, R).



% b
convert(0,[]) :- !.
convert(N,[A|As]) :-  N1 is floor(N/10),
                      A is N mod 10,
                      convert(N1, As).


numar_lista(X, R) :- convert(X,L),
                     reverse(L, R).

suma_subliste(Lista, Rez) :- suma_aux(Lista, Rez, [0]).

suma_aux([], Suma, Suma) :- !.
suma_aux([H|T], Rez, Suma) :- is_list(H),
                              !,
                              suma(H, Suma, Rez1),
                              Suma1 = Rez1,
                              suma_aux(T, Rez, Suma1).
suma_aux([_|T], Rez, Suma) :- suma_aux(T, Rez, Suma).

