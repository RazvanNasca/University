
bits 32

global start
extern exit
import exit msvcrt.dll

segment code use32 class=DATA
    a dw 100110111000101b
    b dw 010100011110000b
    c dd 0
    d dw 000011111110000b
    zero dd 0
    
segment code use32 class=CODE

start:
    
    mov EAX, [zero]
    mov EBX, [zero]
    
    mov AX, [a]
    shr AX, 11 ; shiftam la dreapta 11 pozitii
    
    or AX, d 
    
    mov EBX, [b]
    shl EBX, 4 ; shiftam la stanga 4 pozitii 
    
    add EAX, EBX
    
    mov EBX, [zero]
    mov EBX, [a]
    shl EBX, 16
    add EAX, EBX
    
    
    call [exit]
    