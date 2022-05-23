
bits 32

global start
extern exit
import exit msvcrt.dll

segment code use32 class=DATA
    a dw 100110111000101b
    b dw 010100011110000b
    c dd 0
    d db 00001111b
    e dw 1111000000000000b
    f dw 0000000001100101b
    zero dd 0
    
segment code use32 class=CODE

start:
    
    mov EAX, [zero]
    mov EBX, [zero]
    
    mov AL, [d]
    mov BX, [a]
    shl BX, 5
    add AX, BX
    mov EBX, [zero]
    
    mov BX, [b]
    shl BX, 4
    add AX, BX
    mov EBX, [zero]
    
    mov BX, [b]
    shl BX, 4
    or BX, e
    add AX, BX
    mov EBX, [zero]
    
    mov EBX, [f]
    shl EBX, 16
    add EAX, EBX
    
    
    call [exit]
    