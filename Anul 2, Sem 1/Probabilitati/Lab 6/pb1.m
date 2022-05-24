N = 500;
valMedie = 165;
deviatia = 10;
vec = [];
inalt = [];

for i = 1:N
    rez = normrnd(valMedie, deviatia);
    vec = [vec, rez];
    
    if rez >= 160 && rez <= 170
      inalt = [inalt, rez];
    endif
    
endfor

#subpunctul 1
hist(vec, 10);
hold on;

#subpunctul 2
x = linspace(min(vec), max(vec), 10);
z = normpdf(x, valMedie, deviatia);

plot(x,z);

# subpunctul 3
Medie = mean(vec)
valMedie
dev = std(vec)
deviatia
frecventaRelativa = length(inalt) / N
frecventaTeoretica = normcdf(170, valMedie, deviatia) - normcdf(160, 165, 10)