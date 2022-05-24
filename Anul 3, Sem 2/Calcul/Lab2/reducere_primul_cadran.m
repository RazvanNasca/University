function [f, semn_sin, semn_cos] = reducere_primul_cadran(x)
    x = mod(x, 2*pi);
    
    if (x >= 0 && x < pi/2)
        % primul cadran +, +
        semn_sin = 1;
        semn_cos = 1;
        f = x; 
    elseif (x >= pi/2 && x < pi)
        % al doilea cadran +, -
        semn_sin = 1;
        semn_cos = -1;
        f = pi - x;
    elseif (x >= pi && x < 3*pi/2)
         % al treilea cadran -, -
        semn_sin = -1;
        semn_cos = -1;
        f = x - pi;
    else
        % al patrulea cadran -, +
        semn_sin = -1;
        semn_cos = 1;
        f = 2*pi - x;
    end
end