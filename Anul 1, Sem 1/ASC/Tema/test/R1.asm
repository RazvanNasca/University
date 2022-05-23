bits 32 

global start        

extern exit, scanf, printf, fopen, fread, fclose               
import exit msvcrt.dll    
import scanf msvcrt.dll    
import printf msvcrt.dll    
import fopen msvcrt.dll    
import fread msvcrt.dll    
import fclose msvcrt.dll    
                          
segment data use32 class=data
    
    mod_acces dd "r", 0
    descf dd -1
    len equ 100
    text times len db 0
    nr_caract dd 0
    n dd "%d", 0
    format_n dd "%d", 0
    nume_fisier dd "%s", 0 
    format_fisier dd "%s", 0
    format_output dd "%c", 0
    nr_vocale dd 0
    nr_litere dd 0
    spatiu db 32
    a db 'a'
    e db 'e'
    i db 'i'
    o db 'o'
    u db 'u'

segment code use32 class=code
    start:
        
        ;citim numele fisierului
        push nume_fisier
        push format_fisier
        call [scanf]
        add esp, 4 * 2
        
        ;citim numarul
        push n
        push format_n
        call [scanf]
        add esp, 4 * 2
        
        ;eax = fopen(nume_fisier, mod_acces) deschiderea fisierului
        push dword mod_acces
        push dword nume_fisier
        call [fopen]
        add esp, 4 * 2
        
        mov [descf], eax
        cmp eax, 0
        je final
            
            
            ; eax = fread(text, 1, len, descriptor_fis) citirea fisierului
            push dword [descf]
            push dword len
            push dword 1
            push dword text
            call [fread]
            mov [nr_caract], eax
            add esp, 4 * 4
            
            ; fclose(descf) inchiderea fisierului
            push dword [descf]
            call [fclose]
            add esp, 4 * 1
            
            mov ecx, dword [nr_caract]
            cmp ecx, 0
            je final
                
                xor esi, esi ; esi = 0
            
                repeta:
                    
                    xor eax, eax
                    mov eax, [text+esi]
                    
                    cmp al, [spatiu]
                    jne verificare
                        ; caracterul e spatiu
                        
                        xor edx, edx
                        mov edx, dword [nr_vocale]
                        
                        cmp edx, [n]
                        jne next
                            
                            afisare:
                            
                            xor ebx, ebx
                            sub ecx, [nr_litere]
                            mov ebx, dword [text + esi]
                            
                            pushad
                            
                            push ebx
                            push dword format_output
                            call [printf]
                            add esp, 4 * 2
                            
                            popad
                            
                            xor edx, edx
                            mov edx, dword [nr_litere]
                            sub edx, 1
                            mov [nr_litere], edx
                            
                            cmp edx, 0
                            jne afisare
                            
                        next:
                        mov [nr_vocale], dword 0
                        mov [nr_litere], dword 0
                        
                    verificare:
                    
                    ; nr_litere++
                    xor edx, edx
                    mov edx, dword [nr_litere]
                    add edx, 1
                    mov [nr_litere], edx
                    
                    cmp al, [a]
                    jne urmatorul_dupa_a
                    
                        xor edx, edx
                        mov edx, dword [nr_vocale]
                        add edx, 1
                        mov [nr_vocale], edx
                    
                    urmatorul_dupa_a:
                    
                    cmp al, [e]
                    jne urmatorul_dupa_e
                    
                        xor edx, edx
                        mov edx, dword [nr_vocale]
                        add edx, 1
                        mov [nr_vocale], edx
                    
                    urmatorul_dupa_e:
                    
                    cmp al, [i]
                    jne urmatorul_dupa_i
                    
                        xor edx, edx
                        mov edx, dword [nr_vocale]
                        add edx, 1
                        mov [nr_vocale], edx
                    
                    urmatorul_dupa_i:
                    
                    cmp al, [o]
                    jne urmatorul_dupa_o
                    
                        xor edx, edx
                        mov edx, dword [nr_vocale]
                        add edx, 1
                        mov [nr_vocale], edx
                    
                    urmatorul_dupa_o:
                    
                    cmp al, [u]
                    jne urmatorul_pas
                    
                        xor edx, edx
                        mov edx, dword [nr_vocale]
                        add edx, 1
                        mov [nr_vocale], edx
                    
                    urmatorul_pas:
                    
                    inc esi 
                    
                dec ecx
                cmp ecx, 0
                jne repeta
            
        
        final:
        ; exit(0)
        push    dword 0      
        call    [exit]