; a/2+b*b-a*b*c+e+x

bits 32

global start
extern exit
import exit msvcrt.dll

segment code use32 class=DATA
    a db 2
    b db 4
    c db 8
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
    CBW
    mov BL, 2
    
    idiv BL ; AL = AX/BL
    
    mov BL, AL
    mov Al, [b]
    imul byte [b]
    
    mov DX, AX
    mov AL, BL
    CBW
    adc AX, DX
    mov BX, AX ; in BX e rezultatul de pana acum
    
    mov AL, [a]
    imul byte[b]
    imul byte [c] ; in EAX
    
    add EAX, [e]
    mov EDX, EAX
    mov EAX, [zero]
    mov AX, BX
    CWDE
    adc EAX, EDX ; rezultatul e in EAX
    
    mov EBX, EAX
    
    mov EAX, [zero]
    mov EDX, [zero]
    
    mov EAX, [x]
    mov EDX, [x+4]
    
    adc EAX, EBX 
    
    
    push dword 0
    call [exit]
    