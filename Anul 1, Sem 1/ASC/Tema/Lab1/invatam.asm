bits 32 

global start        

extern exit               
import exit msvcrt.dll    
                          
segment data use32 class=data
        
    a1 db 256, -256, 128, -128
    a7 dw 512, -512
    a10 db $-$$
    a11 dw a11
    a2 dw ~(a11-a1)
    times 2 dd 1234h, 5678h

    
segment code use32 class=code
    start:
         
        
            
        ; exit(0)   
        push    dword 0      
        call    [exit]