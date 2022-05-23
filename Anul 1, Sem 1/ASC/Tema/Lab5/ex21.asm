;A: 2, 1, -3, 3, -4, 2, 6
;B: 4, 5, 7, 6, 2, 1
;R: 1, 2, 6, 7, 5, 4, -3, -4

bits 32 

global start        

extern exit               
import exit msvcrt.dll    
                          
segment data use32 class=data
    s1 db 2, 1, -3, 3, -4, 2, 6
    l1 equ $-s1 ; lungimea sirul 1
    s2 db 4, 5, 7, 6, 2, 1
    l2 equ $-s2
    d times l1+l2 db 0
    

segment code use32 class=code
    start:
        mov EDX, 0
    
        mov ECX, l1 ; punem lungime sirului in ECX pt a creea bucla loop
        mov ESI, l1 ; contor
        jecxz Sfarsit
        
        Repeta:
            mov AL, [s1+ESI]
            mov [d+EDX], AL
            
            inc EDX
            dec ESI
            
        loop Repeta
        
        mov ECX, l2
        mov ESI, 0
        Repeta_Altceva:
            mov AL, [s2+ESI]
            
            cmp AL, 00
            jge increment ; daca valoarea din AL este mai mare sau egala cu zero 
                          ; atunci functia sare direct la incrementare fara a mai efectua operatia
                mov [d+EDX], AL
                inc EDX

            increment:
                inc ESI
            
        loop Repeta_Altceva        
        
        Sfarsit:
     
            
        ; exit(0)
        push    dword 0      
        call    [exit]