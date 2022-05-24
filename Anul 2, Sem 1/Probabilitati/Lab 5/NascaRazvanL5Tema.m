N = 1000;
nrAruncari = 0;
vec = [];

for i = 1:N
  
  moneda1 = rand; 
  moneda2 = rand;
  
  if moneda1 <= 0.5 && moneda2 <= 0.3  # daca ambele monede pica stema <=> succes
      vec = [vec, nrAruncari];          # stocam aruncarile fara succes de pana succes
      nrAruncari = 0;
  else
      nrAruncari = nrAruncari + 1;    # incrementam numarul de aruncari care nu au avut succes
  endif
  
endfor

media = mean(vec)
