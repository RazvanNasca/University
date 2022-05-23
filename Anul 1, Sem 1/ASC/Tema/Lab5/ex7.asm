;S1: 1, 3, 5, 7
;S2: 2, 6, 9, 4
;D: 1, 2, 3, 6, 5, 9, 7, 4

bits 32 

global start        

extern exit               
import exit msvcrt.dll    
                          
segment data use32 class=data
    s1 db 1, 3, 5, 7
    l1 equ $-s1 ; lungimea sirul
    s2 db 2, 6, 9, 4
    l2 equ $-s2
    d times l1+l2 db 0
    

segment code use32 class=code
    start:
        mov ECX, l1 ; punem lungime primului sir in ECX pt a creea bucla loop
        mov ESI, 0 ; contor
        mov EDX, 0  ; tinem 2 contoare
                    ; 1 pentru a parcurge sirurile initiale, iar celalalt pentru a parcurge sirul rezultat
        jecxz Sfarsit
        Repeta:
            mov AL, [s1+ESI]
            mov BL, [s2+ESI]
            inc ESI
            
            mov [d+EDX], AL
            inc EDX
            mov [d+EDX], BL
            inc EDX
            
        loop Repeta
        Sfarsit:
     
            
        ; exit(0)
        push    dword 0      
        call    [exit]