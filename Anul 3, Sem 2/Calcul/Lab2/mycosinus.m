function seria = mycosinus(xx)
    % reducere la primul cadran
    [x, ~, cos_sign] = reducere_primul_cadran(xx);
    
    seria = 0; % construim seria taylor pentru cos
    taylor = 1;
    n = 0;
    while seria + taylor ~= seria
        n = n + 1;
        seria = seria + taylor;
        taylor = (-1)^n * ((x^(2*n)) / (factorial(2*n)));
    end
    seria = cos_sign * seria;
end