; c-(d+d+d)+(a-b)

bits 32

global start
extern exit
import exit msvcrt.dll

segment code use32 class=DATA
    a db 1
    b db 3
    c db 7
    d db 2
    x db 0
    
segment code use32 class=CODE

start:
    mov AL, [c]
    mov BL, [d]
    sub AL, BL
    sub AL, BL
    sub AL, BL
    
    
    
    add AL, [a]
    mov BL, [b]
    sub AL, BL
    
   
    mov [x], AL
    
    push dword 0
    call [exit]
    