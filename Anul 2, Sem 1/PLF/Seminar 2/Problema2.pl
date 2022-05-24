% inversam o lista
% Model matematic:
%  Inversam(l1l2l3..ln, R) = R                      , lista vida
%                            Inversa(l2l3..ln, l1+R), altfel
%  Inversa_principal(L) = inversa(L, [])
%  Implementare:
%  Inversa(L: lista, R: lista, Rez: Lista)
%  model flux(i,i,o)
%  L - lista de inversat
%  R - lista colectoare
%  Rez - lista rezultat

Inversa([], R, R).
Inversa([H|T], R, Rez) :- Inversa(T, H|R, Rez).

%InversPrincipal(L: lista, R: lista)
% model flux (I,o)
% L - lista de inversat
% R - lista rezultat

InversaPrincipala(L,R) :- Inversa(L, [], R).
