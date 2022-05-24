function myeps()
    f=1;
    while(1+f~=1)
        f=f/2;
    end
    f=2*f;
    
    fprintf("My epsilon: %d \n", f);
    fprintf("System epsilon: %d \n", eps);
end