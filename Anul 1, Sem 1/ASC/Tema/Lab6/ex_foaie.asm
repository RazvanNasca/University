bits 32 

global start        

extern exit               
import exit msvcrt.dll    
                          
segment data use32 class=data
    
    a dw 0ABCDh, 0FEDCh
    zero dw 0

segment code use32 class=code
    start:
    
        mov EAX, [zero]
        mov EBX, [zero]
        
        mov esi, a
        lodsb
        lodsw
        
        
    
        ; exit(0)
        push    dword 0      
        call    [exit]