
syms x;
f = sin(x);
pade_approx(2, f, 4, 5);

g = cos(x);
pade_approx(2, g, 4, 5);
