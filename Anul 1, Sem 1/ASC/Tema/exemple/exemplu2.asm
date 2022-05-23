bits 32 

global start        

extern exit, printf, scanf  
import exit msvcrt.dll
import printf msvcrt.dll
import scanf msvcrt.dll
                          
segment data use32 class=data

    numar db "%d"
    n dd 0
    format1 db "Numarul este: %d !", 10, 0
    format2 db "Numarul este: %o !", 0
    

segment code use32 class=code
    start:
        
        xor eax, eax  ;golim EAX
        
        ;scanf(numar,eax)
        push dword n
        push dword numar
        call [scanf]
        add esp, 4 * 2
        
        mov eax, dword [n]
        
        ; printf(format, 10)
        push eax
        push dword format1
        call [printf]
        add esp, 4 * 1
        
        pop eax
        ;mov eax, 17
        
        push eax
        push dword format2
        call [printf]
        add esp, 4 * 2
    
        ; exit(0)
        push    dword 0      
        call    [exit]