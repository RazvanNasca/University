pkg load statistics

K = 10;
M = 10;
Prob = 0.5;
LocCurent = 0;
vector = repmat(0,1,K);

for j = 1:M
  for i = 1:K
    
    X = rand;
    if X < Prob
      LocCurent = LocCurent + 1;
    else
      LocCurent = LocCurent - 1;
    end
      
  endfor
  
  vector(j) = LocCurent
  
endfor

N = histc(vector, -K:K);
bar(-K:K, N / (K*M), "hist", "FaceColor", "b");