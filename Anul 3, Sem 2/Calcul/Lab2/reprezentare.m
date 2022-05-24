function y = reprezentare(x)
   if isa(x, 'single')  % verificam daca numarul e reprezentat pe 32 sau 64 de biti
        precizie = 32;
   else 
        precizie = 64;
   end

   result = '';
   repr_byte = typecast(x, 'uint8');    %  reprezentam pe 8 bytes sub forma de vector

   for i = length(repr_byte):-1:1   % parcurgem descrescator
        byte = repr_byte(i);
        binary = dec2bin(byte);
        bin_str = num2str(binary);
    
        while length(bin_str) < 8   % daca sunt mai putin de 8 biti completam cu zero-uri
            bin_str = strcat('0', bin_str);
        end
        
        result = strcat(result, bin_str);
   end


    if precizie == 32
        y = strcat(result(1), '-', result(2:9), '-', result(10:32));
    else
        y = strcat(result(1), '-', result(2:12), '-', result(13:64));
    end

end