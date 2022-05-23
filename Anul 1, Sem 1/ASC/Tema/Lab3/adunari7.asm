;c-(d+d+d)+(a-b) 

bits 32

global start
extern exit
import exit msvcrt.dll

segment code use32 class=DATA
    a db 5
    b dw 4
    c dd 8
    d dq 1122334455667788h
    zero dd 0
    x dw 0
    
segment code use32 class=CODE

start:
    mov EAX, [zero]
    mov EBX, [zero]
    mov ECX, [zero]
    mov EDX, [zero]
    
    mov EBX, [c]
    
    add EAX, [d]
    adc EDX, [d+8]
    
    
    sub EBX, EAX
    
    mov BL, [a]
    CBW
    sub BX, [b]
    CWDE
    
    sub EAX, EBX
    sub EDX, ECX
    
    
    push dword 0
    call [exit]
    