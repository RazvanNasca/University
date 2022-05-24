% Dandu-se o lista numerica, sa se elimine toate secventele de elemente in ordine crescatoare.
% Var1: Comparam 3 elemente
% Eliminare(l1l2l3..ln) = []                 , n = 0
%                         [l1]               , n = 1
%                         []                 , n = 2 si l1 < l2
%                         Eliminare(l2..ln)  , n > 2 si l1 < l2 < l3
%                         Eliminare(l3..ln)  , n > 2 si l1 < l2 >= l3
%                         l1 + Eliminare(l2..ln), altfel (n = 2 si l1
%                         >= l2 sau n >= 3 si l1 >= l2)
