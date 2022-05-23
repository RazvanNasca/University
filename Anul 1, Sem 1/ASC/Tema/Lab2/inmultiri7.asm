; [100*(d+3)-10]/d

bits 32

global start
extern exit
import exit msvcrt.dll

segment code use32 class=DATA
    c db 100
    d dw 2
    x dw 0
    
segment code use32 class=CODE

start:
    mov AX, [d]
    add AX, 3
    mul word [c] ; DX:AX 
    sub AX, 10
    div word [d]
    
    mov [x], AX
    
    
    push dword 0
    call [exit]
    