; (a+b)*(c+d)

bits 32

global start
extern exit
import exit msvcrt.dll

segment code use32 class=DATA
    a db 5
    b db 5
    c db 8
    d db 2
    zero db 0
    x dw 0
    
segment code use32 class=CODE

start:
    mov AX, [zero]
    mov BX, [zero]
    mov AL, [a]
    mov BL, [b]
    add AL, BL
    
    mov BX, [zero]
    mov BL, [c]
    add BL,  [d]
    
    mul BL
    
    mov [x], AX
    
    
    push dword 0
    call [exit]
    