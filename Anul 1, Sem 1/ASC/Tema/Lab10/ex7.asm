bits 32 

global start        

extern exit, printf            
import exit msvcrt.dll 
import printf msvcrt.dll   
                          
segment data use32 class=data
    
    a dd 23
    b dw 5
    zero dd 0
    format db "%d mod %d = %d" 
    

segment code use32 class=code
    start:
        
        mov EAX, [zero]     ; golim registrii
        mov EBX, [zero]
        mov ECX, [zero]
        xor EDX, EDX
        
        mov AX, [a]         ; mutam in AX partea low a lui a
        mov DX, [a+2]       ; mutam in DX partea high a lui a
        mov ECX, [a]        ; facem o copie lui a
        mov BX, [b]         ; mutam  b in BX
        
        idiv byte BX        ; dividem AX la BX
        
        push word DX        ; adaugam pe stiva restul imp
        push word BX        ; adaugam pe stiva valoare lui b
        push dword ECX       ; adaugam pe stiva valoare lui a
        push dword format    
        call [printf]
        add esp, 4 * 2        
    
        ; exit(0)
        push    dword 0      
        call    [exit]