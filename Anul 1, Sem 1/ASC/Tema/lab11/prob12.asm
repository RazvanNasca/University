; Intercaleaza 2 siruri

bits 32 

global start        

extern exit, printf               
import exit msvcrt.dll    
import printf msvcrt.dll    

extern intercalare
                          
segment data use32 class=data

    a db 'aceg'
    lung equ $-a
    b db 'bdfh'
    lrez equ  2 * lung
    d1 times lrez dd 0
    d2 times lrez dd 0
    format db '%s', 13, 10, 0
    

segment code use32 class=code
    start:
    
    
        mov ECX, lung   ; pentru parcurgere
        xor EDX, EDX    ; initializam cu zero
        xor EBX, EBX    ; initializam cu zero
        jecxz final     ; sare daca ECX este zero
        
        mov EDX, a
        mov EBX, b      ; copiem sirurile
        ;mov ESI, a      ; incepem cu sirul a
        mov EDI, d1     ; sirul creat
        call intercalare     ; apelam intercalarea
        
        push d1 
        push dword format
        call [printf]
        add esp, 8
      

      
        mov ECX, lung   ; pentru parcurgere
        xor EDX, EDX    ; initializam cu zero
        xor EBX, EBX    ; initializam cu zero
        jecxz final     ; sare daca ECX este zero
        
        mov EDX, b
        mov EBX, a      ; copiem sirurile
        mov ESI, b      ; incepem cu sirul b
        mov EDI, d2     ; sirul creat
        call intercalare     ; apelam intercalarea
        
        push d2 
        push dword format
        call [printf]
        add esp, 8
        
        final:
        
        ; exit(0)
        push    dword 0      
        call    [exit]