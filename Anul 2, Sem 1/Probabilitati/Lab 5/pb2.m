N = 100;
lambda = 1/12;
vec = [];

for i = 1:N
  
  X = rand;
  myExp = -(1/lambda) * log(1-X);
  
  vec = [vec, myExp];
  
endfor

medie = mean(vec)

std(vec)