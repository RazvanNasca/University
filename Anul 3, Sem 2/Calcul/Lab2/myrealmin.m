function myrealmin()
    power = 0;  % initializam cu o putere mica

    while 2^power > 0
        power = power - 1;  % decrementam puterea astfel incat 2^power sa tinda la 0
    end

    power = power + 53;   % dupa while, power o sa aiba valoarea 1075
    g = 2^power;

    fprintf("My real minim: %d \n", g);
    fprintf("System real minim: %d \n", realmin);
    % realmin = 2^(-1022)
end