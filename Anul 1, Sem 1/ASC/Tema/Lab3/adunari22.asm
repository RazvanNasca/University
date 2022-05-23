;c+b-(a-d+b) 

bits 32

global start
extern exit
import exit msvcrt.dll

segment code use32 class=DATA
    a db 5
    b dw 4
    c dd 8
    d dq 2
    zero dd 0
    
segment code use32 class=CODE

start:
    mov EAX, [zero]
    mov EBX, [zero]
    mov ECX, [zero]
    mov EDX, [zero]
    
    mov EBX, [c]
    mov AX, [b]
    CWDE ; EAX <- AX
    add EBX, EAX
    
    mov EAX, [zero]
    mov AL, [a]
    CBW ; AX <- AL 
    CWDE ; EAX <- AX 
    mov EDX, [a+8]
    
    sub EAX, [d]
    sbb EDX, [d+8]
    
    add EAX, [b]
    
    sub EBX, EAX
    
    
    push dword 0
    call [exit]
    