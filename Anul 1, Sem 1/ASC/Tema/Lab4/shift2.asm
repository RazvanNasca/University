
bits 32

global start
extern exit
import exit msvcrt.dll

segment code use32 class=DATA
    a dw 100110111000101b
    b dw 010100011110000b
    c dd 0
    
    d dw 000011111110000b 
    e dw 000000000001111b
    f dw 0000000111110000b
    g dw 1111111000000000b
    zero dd 0
    
segment code use32 class=CODE

start:
    
    mov EAX, [zero]
    mov EBX, [zero]
    
    mov BX, [b]
    shr BX, 5 ; shiftam la dreapta 5 pozitii
    or BX, e ; umplem de zerouri pe pozitile 4-15
    
    mov AX, BX
    mov EBX, [zero]
    
    mov BX, [a]
    shl BX, 4
    or BX, f ; umplem de zerouri pe pozitile diferite de 4-8
    
    add AX, BX
    mov EBX, [zero]
    
    mov BX, [a]
    shl BX, 3
    or BX, g ; completam cu zerouri pe pozitile 0-9
    
    add AX, BX
    mov EBX, [zero]
    
    mov EBX, [b]
    shl EAX, 16
    
    add EAX, EBX
    
    
    
    call [exit]
    