N = 100;
nrPuncte = 0;
hold on;
axis square;
rectangle('Position',[0,0,1,1]);
for i = 1 : N
  X = rand;
  Y = rand;
  
  % verificam daca apartine cercului
  if pdist([[X,Y];[1/2,1/2]]) < 1/2
    plot(X,Y,'*r');
    nrPuncte = nrPuncte + 1;
  end
  
endfor

disp(nrPuncte / N);
