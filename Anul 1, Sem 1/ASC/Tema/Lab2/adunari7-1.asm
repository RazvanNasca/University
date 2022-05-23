; (c+c+c)-b+(d-a)

bits 32

global start
extern exit
import exit msvcrt.dll

segment code use32 class=DATA
    a dw 1
    b dw 3
    c dw 2
    d dw 2
    x dw 0
    
segment code use32 class=CODE

start:
    mov AX, [c]
    add AX, [c]
    add AX, [c]
    
    mov BX, [b]
    sub AX, BX
    
    mov BX, [d]
    sub BX, [a]
    
    add AX, BX
    
    
    mov [x], AX
    
    push dword 0
    call [exit]
    