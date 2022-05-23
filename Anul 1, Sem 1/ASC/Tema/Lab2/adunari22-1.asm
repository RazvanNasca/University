; (b-a)-(c+c+d)

bits 32

global start
extern exit
import exit msvcrt.dll

segment code use32 class=DATA
    a dw 2
    b dw 8
    c dw 4
    d dw 2
    x db 0
    
segment code use32 class=CODE

start:
    mov AX, [b]
    mov BX, [a]
    sub AX, BX
    
    mov BX, [c]
    add BX, [c]
    add BX, [d]
    
    sub AX, BX
    
    mov [x], AX
    
    
    push dword 0
    call [exit]
    