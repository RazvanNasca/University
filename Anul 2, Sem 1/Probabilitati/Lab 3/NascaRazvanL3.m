pkg load statistics;

CazuriFavorabile = 0;
i = 1;

while i < 5000
  
  zarBun = binornd(5, 2/6);  % probabilitatea ca un zar sa fie bun este de 2/6 deoarece doar 2 numere sunt divizibile cu 3 dintre cele de pe zar
  if zarBun == 2
    CazuriFavorabile = CazuriFavorabile + 1;
  end 
  
  i = i + 1;
  
endwhile
CazuriFavorabile = CazuriFavorabile/5000

binopdf(2,5,2/6)
