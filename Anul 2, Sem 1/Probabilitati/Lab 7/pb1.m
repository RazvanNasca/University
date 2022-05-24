pkg load statistics

x = [20, 50, 15, 16];
a = 0.05;
n = length(x);
Rn = sqrt(n);

% a)
sigma = 5;
z1_a2 = norminv(1-a/2, 0, 1);
Xn = mean(x);

stanga1 = Xn - (sigma/Rn)*z1_a2;
dreapta1 = Xn + (sigma/Rn)*z1_a2;
out1 = [stanga1, dreapta1]

% b)
t1_a2 = tinv(1 - a/2, n-1);
abatere = std(x); % ???

stanga2 = Xn - (abatere/Rn)*t1_a2;
dreapta2 = Xn + (abatere/Rn)*t1_a2;
out2 = [stanga2, dreapta2]

% c)
c1_a2 = chi2inv(1 - a/2, n-1);
c_a2 = chi2inv(a/2, n-1);

stanga3 = sqrt((n-1)/c1_a2) * std(x);
dreapta3 = sqrt((n-1)/c_a2) * std(x);
out3 = [stanga3, dreapta3]

% d)
vbool = [1, 0, 0, 1];
XnBool = mean(vbool);
nbool = length(XnBool);
z1_a2 = norminv(1-a/2, 0, 1);

stanga4 = XnBool - sqrt((XnBool * (1 - XnBool)) / nbool) * z1_a2;
dreapta4 = XnBool + sqrt((XnBool * (1 - XnBool)) / nbool) * z1_a2;

if stanga4 < 0
  stanga4 = 0;
endif
  
if dreapta4 > 1
  dreapta4 = 1;
endif

out4 = [stanga4, dreapta4]

