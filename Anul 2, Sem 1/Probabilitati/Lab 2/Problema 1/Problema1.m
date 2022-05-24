% x m n
m = 3
n = 2

vf = [repmat("f",1,m)];
vb = [repmat("b",1,n)];

vfinal = ["x", vf,vb];

%doua vecine    sau    1 vecin si 1 vecina    sau   2 vecini   sau 1 vecin   sau 1 vecina
N = 10
X = 1
nrCazuri = 0
for i = 1:N
  a = randperm(m+n+1);  % generam permutarile si le stocam in vectorul a, indexari de la 1
  vec = [vfinal(a)];
  
  poz = find(a==X)      % cauta pozitia lui X  || find(vec == "x")
  
  if(poz != 1 & poz != length(a))
    stanga = poz-1;
    dreapta = poz+1;
    if(vec(stanga) == "f" & vec(dreapta) == "f")
      disp("X are doua vecine la spectacol");
      nrCazuri = nrCazuri + 1
    end
  end
  
  %disp(nrCazuri)
  
  %disp(nrCazuri/N)
  
 end