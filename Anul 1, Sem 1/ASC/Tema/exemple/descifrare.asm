bits 32 

global start        

extern exit, fopen, fread, fclose, printf      
import exit msvcrt.dll    
import fopen msvcrt.dll    
import fread msvcrt.dll    
import fclose msvcrt.dll    
import printf msvcrt.dll    
                          
segment data use32 class=data

    nume_fisier dd "input.txt", 0 
    mod_acces dd "r", 0
    descf dd -1
    len equ 100
    text times len db 0
    nr_caract dd 0
    format_input dd "%s", 0
    format_output dd "%c", 10, 0
    
segment code use32 class=code
    start:
        
        ;eax = fopen(nume_fisier, mod_acces)
        push dword mod_acces
        push dword nume_fisier
        call [fopen]
        add esp, 4 * 2
        
        mov [descf], eax
        cmp eax, 0
        je final
        
            ; eax = fread(text, 1, len, descriptor_fis)
            push dword [descf]
            push dword len
            push dword 1
            push dword text
            call [fread]
            mov [nr_caract], eax
            add esp, 4 * 4
            
            ; fclose(descf)
            push dword [descf]
            call [fclose]
            add esp, 4 * 1
            
            mov ecx, dword [nr_caract]
            cmp ecx, 0
            je final
            
                xor esi, esi
                
                repeta:
                    xor eax, eax
                    mov al, byte [text + esi]
                    sub al, 3
                    
                    pushad
                    
                    push eax
                    push dword format_output
                    call [printf]
                    add esp, 4 * 2
                    
                    popad
                    
                    inc esi
                    ;cmp ecx, 0
                    ;je final
                loop repeta
                
        
        final:
        
        
        ; exit(0)
        push    dword 0      
        call    [exit]