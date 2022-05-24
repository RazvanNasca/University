function myrealmax()

    maxExp = 2^10;  % initializam cu o putere a lui 2
    while (~isinf(maxExp))  % cat timp numarul nu ajunge la overflow
        bv = maxExp;    % retinem exponentul cel mai mare
        maxExp = 2*maxExp;  % marim exponentul
    end

    h = (2-eps)*bv;     % formula de pe net :)  (2-eps)^maxExp

    fprintf("My real maxim: %d \n", h);
    fprintf("System real maxim: %d \n", realmax);

end