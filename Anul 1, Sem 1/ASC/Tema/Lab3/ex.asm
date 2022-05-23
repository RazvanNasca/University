; 

bits 32

global start
extern exit
import exit msvcrt.dll

segment code use32 class=DATA
    a db 1 
    b db 3
    
segment code use32 class=CODE

start:
    
    mov AX, 0
    mov AX, [b]
    
    
    push dword 0
    call [exit]
    