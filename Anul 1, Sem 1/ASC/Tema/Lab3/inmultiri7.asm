;(a-2)/(b+c)+a*c+e-x 

bits 32

global start
extern exit
import exit msvcrt.dll

segment code use32 class=DATA
    a db 5
    b db 4
    c dw 8
    e dd 3
    x dq 10
    zero dd 0
    
segment code use32 class=CODE

start:
    mov EAX, [zero]
    mov EBX, [zero]
    mov ECX, [zero]
    mov EDX, [zero]
    
    mov AL, [a]
    sub AL, 2
    CBW
    mov BX, AX ; BX <- AX
    
    mov AL, [b]
    CBW
    sub AX, [c]

    mov CX, AX
    mov AX, BX
    
    idiv BX
    mov BX, AX ; BX <- AX
    
    mov AL, [a]
    CBW
    imul word [c] ; DX:AX ← AX * c
    
   ; mov CX, DX
   ; mov DX, BX
   ; mov BX, AX
   ; mov AX, DX  ; DX:AX <- CX:BX
    
    add AX, BX
    
    mov BX, [e]
    mov CX, [e+4]
    
    add AX, BX
    add DX, CX
    
    mov EBX, [x]
    mov ECX, [x+4]
    
    CWDE ; EAX <- AX
    
    add EBX, EAX
    mov EAX, [zero]
    mov AX, DX
    CWDE
    add ECX, EAX 
    
    
    push dword 0
    call [exit]
    