; (a+b+b)-(c+d)

bits 32

global start
extern exit
import exit msvcrt.dll

segment code use32 class=DATA
    a db 8
    b db 2
    c db 4
    d db 2
    x db 0
    
segment code use32 class=CODE

start:
    mov AL, [a]
    mov BL, [b]
    add AX, BX
    add AX, BX
    
    mov BL, [c]
    sub AX, BX
    
    mov BL, [d]
    sub AX, BX
    
    
    mov [x], AL
    
    
    push dword 0
    call [exit]
    