; [(10+d)-(a*a-2*b)]/c

bits 32

global start
extern exit
import exit msvcrt.dll

segment code use32 class=DATA
    a dw 3
    b dw 4
    c db 2
    d dw 2
    x dw 0
    
segment code use32 class=CODE

start:
    mov AX, [a]
    mul word [a]
    push DX
    push AX 
    pop EBX ; a*a in EBX
    
    mov AX, 2
    mul word [b]
    
    push DX
    push AX
    pop EAX
    sub EBX, EAX ; rezultatul e in EBX
    
    mov AX, [d]
    CWDE ; EAX <- AX
    add EAX, 10 
    
    sub EAX, EBX ; rezultatul este in EAX   
   
    div byte [c]
    
    mov [x], AX
    
    
    push dword 0
    call [exit]
    