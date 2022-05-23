bits 32 

global start        

extern exit               
import exit msvcrt.dll    
                          
segment data use32 class=data

    s dd 12345678h, 1A2B3C4Dh, 0FE98DC76h
    l equ ($-s)/4   ; lungimea sirului
    d times l dd 0  ; declaram al 2-lea sir
    

segment code use32 class=code
    start:
    
        
        mov ECX, l      
        mov ESI, 0      ; incepe indexarea de la 0
        
        jecxz Sfarsit
        Repeta:
        
            mov EAX, 0          ; golim EAX
            mov EAX, [s+ESI]    ; extragem primul dublu-cuvant din sir
            mov BL, 0           ; golim BL
            
            prelucreazaNumar:   ;eticheta   
                shl EAX, 1      ; shiftam la stanga cu o pozitie...daca cel mai din stanga bit a fost 1, atunci CF = 1
                adc BL, 0       ; CF  se aduna la BL
                cmp EAX, 0      ; daca numarul din EAX a fost shiftat complet, se opreste repetitia
            jne prelucreazaNumar   ;daca nu face jump la eticheta
            
            mov DL, 2           ; mutam in DL valoarea 2 pentru a putea face impartirea
            mov AL, BL          
            CBW                 ; convertim de la byte la word
            idiv byte DL        ; impartim AL la DL
            
            cmp AH, 0           ; daca restul impartirii este zero => numarul din sir are un numar par de biti egali cu 1
            jne final           ; daca comparatia nu este egala, sare la final
                mov EAX, [s+ESI]    ; altfel adauga numarul in al 2lea sir
                mov [d+ESI], EAX
            final:
            
            add ESI, 4          ; incrementam indexul
            
        loop Repeta
        Sfarsit:
        
    
        ; exit(0)
        push    dword 0      
        call    [exit]