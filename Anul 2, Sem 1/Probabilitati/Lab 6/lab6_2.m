# g1 : [-2, 2] -> R, g1(x) = e^[-(x^2)]

N = 100;
a = -2;
b = 2;
M = 2;

f = @(x) exp(-x.^2);

rectangle("Position", [a, 0, (b - a), M]);
title("Function");

hold on;

x = unifrnd(a, b, 1, N);
y = unifrnd(0, M, 1, N);
for i = 1 : N
  px = x(i);
  py = y(i);
  plot(px, py, "*r");
endfor

plot(x, f(x), '*b');