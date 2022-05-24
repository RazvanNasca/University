pkg load statistics

N = 100;
M = 100;
Prob = 0.5;
LocCurent = 0;
vector = repmat(0,1,M);

for j = 1:M
  for i = 1:N
    
    X = rand;
    if X < Prob
      LocCurent = LocCurent + 1;
      
      if LocCurent == N       % daca am ajuns la finalul ciclului, reincepem de la pozitia 0
        LocCurent = 0;
      end
      
    else
      LocCurent = LocCurent - 1;
      
      if LocCurent == -1      % structura circulara ne impune ca in stanga pozitiei 0 sa avem n-1, deci alocam valoarea n-1 in loc de -1
        LocCurent = N - 1;
          
      end
    end
    
  endfor
  
  vector(j) = LocCurent
  
endfor

H = histc(vector, 1:N);
bar(1:N, H / M, "hist", "FaceColor", "b");