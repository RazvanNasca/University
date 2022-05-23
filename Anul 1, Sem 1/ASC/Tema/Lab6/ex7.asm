bits 32 

global start        

extern exit               
import exit msvcrt.dll    
                          
segment data use32 class=data

    s dd 12345678h, 1A2B3C4Dh, 0FE98DC76h
    l equ ($-s) / 4
    d times l db 0 

segment code use32 class=code
    start:
    
        mov EAX, [s]
        mov ECX, l
        mov ESI, 0
        
        jecxz Sfarsit
        Repeta:
        
            mov BL, 3
            mov EAX, 0
            
            mov AL, [s+ESI+3] ; mutam octetu superior in AL
            mov DL, AL
            
            CBW                 ; convertim AL la AX ca sa l putem imparti la 3
            idiv byte BL
            
            cmp AH, 0           ; daca octetul extras este divizibil cu 3
            jne final
                mov [d+ESI], DL
            final:
            add ESI, 4
            
            
        loop Repeta
        Sfarsit:
    
        ; exit(0)
        push    dword 0      
        call    [exit]