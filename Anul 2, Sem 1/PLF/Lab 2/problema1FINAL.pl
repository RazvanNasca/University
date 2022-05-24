% Ex 1a
% suma(X: Lista, Y: Lista, Rez: Lista)
% X: prima lista
% Y: a doua lista
% Rez: suma
% Model de flux: (i i o), (i i i) determinist
suma(X, Y, Rez):- lista_numar(X, R1),
                  lista_numar(Y, R2),
                  R is R1 + R2,
                  numar_lista(R, Rez).


% lista(X: Lista, R: Lista)
% X: Lista initiala
% R: Lista rezultat
% Model de flux: (i o), (i i) determinist
lista_numar(X, R) :- numar(X, 0, R).


% numar(L: lista, P: Integer, R: Integer)
% model matematic: numar(l1l2..ln, P): numarul(l1l2..ln), n = 0
%                                      numar(l2l3..ln, p*10+l1), altfel
% L: lista
% P: numarul care se formeaza
% R: rezultatul
% Model de flux: (i i i), (i i o)
numar([], R, R).
numar([H|T], P, R) :- P1 is P * 10 + H,
                      numar(T, P1, R).


% numar_lista(X: Integer, R: lista)
% X: numarul ce trebuie convertit
% R: Lista rezultat
% Model de flux: (i i), (i o)
numar_lista(X, R) :- convert(X,L),
                     reverse(L, R).


% convert(N: Integer, L: Lista)
% Model matematic: convert(N, l1l2..ln): l1l2..ln, n = 0
%                                        convert(n/10, l1l2..n%10), altfel
% N: numarul ce trebuie convertit
% L: lista rezultat
convert(0,[]) :- !.
convert(N,[A|As]) :-  N1 is floor(N/10),
                      A is N mod 10,
                      convert(N1, As).


%b
% suma_aux(L: Lista, Rez: Lista, Suma: Lista)
% Model matematic: suma_aux(l1l2l..ln): 0, n = 0
%                                      suma_aux(l2l3..ln),
%                                      l1- lista
%                                      suma_aux(l2l3..ln),
%                                      altfel
% L - lista eterogena Rez -
%                                      lista rezultat Suma - suma
%                                      intermediara a sublistelor
suma_aux([], Suma, Suma) :- !.
suma_aux([H|T], Rez, Suma) :- is_list(H),
                              !,
                              suma(H, Suma, Rez1),
                              Suma1 = Rez1,
                              suma_aux(T, Rez, Suma1).
suma_aux([_|T], Rez, Suma) :- suma_aux(T, Rez, Suma).


suma_subliste(Lista, Rez) :- suma_aux(Lista, Rez, [0]).









%a varianta 2
sumaListe([L|M], [P|Q], R):-reverse([L|M], [L1|M1]),
                            reverse([P|Q], [P1|Q1]),
                            S is L1 + P1,
                            S > 9,
                            !,
                            R = [mod(S, 10)],
                            suma2(M1, Q1, Rez, 1),
                            reverse(Rez, R).
sumaListe([L|M], [P|Q], R):-reverse([L|M], [L1|M1]),
                            reverse([P|Q], [P1|Q1]),
                            S is L1 + P1,
                            S < 10,
                            R = [S],
                            suma2(M1, Q1, Rez, 0),
                            reverse(Rez, R).

suma2([],[],[],_):-!.
suma2([], L, L, _):-!.
suma2(L, [], L, _):-!.
suma2([L|M], [P|Q], R1, T):-S is L + P + T,
                           S > 9,
                           R1 = [mod(S, 10)|R],
                           suma2(M, Q, R, 1).
suma2([L|M], [P|Q], R1, T):-S is L + P + T,
                            S < 10,
                            R1 = [S|R],
                            suma2(M, Q, R, 0).






