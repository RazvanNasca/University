bits 32 

global start        

extern exit, fopen, fread, fclose, printf
import exit msvcrt.dll  
import fopen msvcrt.dll  
import fread msvcrt.dll
import fclose msvcrt.dll
import printf msvcrt.dll

; our data is declared here (the variables needed by our program)
segment data use32 class=data

    nume_fisier db "fisier.txt", 0  ; numele fisierului care va fi deschis
    mod_acces db "r", 0          ; modul de deschidere a fisierului - 
                                 ; r - pentru scriere. fisierul trebuie sa existe 
    descriptor_fis dd -1         ; variabila in care vom salva descriptorul fisierului - necesar pentru a putea face referire la fisier
    format1 db '%c' , 0           ; pt afisarea caracterului
    format2 db '%d' , 0           ; pt afisarea frecventei caracterului
    spatiu db ' ', 0
    len equ 100                  ; numarul maxim de elemente citite din fisier.                            
    text times len db 0          ; sirul in care se va citi textul din fisier  
    nr_caract db 0               ; numarul de caractere din fisier
    sir resb 26                  ; rezervam un sir de frecvente
    db -1
    maxim db 0                   ; frecventa maxima
    caract db 0                  ; caracterul cautat

; our code starts here
segment code use32 class=code
    start:
        ; apelam fopen pentru a deschide fisierul
        ; functia va returna in EAX descriptorul fisierului sau 0 in caz de eroare
        ; eax = fopen(nume_fisier, mod_acces)
        push dword mod_acces     
        push dword nume_fisier
        call [fopen]
        add esp, 4*2                ; eliberam parametrii de pe stiva

        mov [descriptor_fis], eax   ; salvam valoarea returnata de fopen in variabila descriptor_fis
        
        ; verificam daca functia fopen a creat cu succes fisierul (daca EAX != 0)
        cmp eax, 0
        je final
        
        ; citim textul in fisierul deschis folosind functia fread
        ; eax = fread(text, 1, len, descriptor_fis)
        push dword [descriptor_fis]
        push dword len
        push dword 1
        push dword text        
        call [fread]
        mov [nr_caract], AL            ; aflam nr de caractere 
        add esp, 4 * 4                 ; dupa apelul functiei fread EAX contine numarul de caractere citite din fisier
        
        
        ; apelam functia fclose pentru a inchide fisierul
        ; fclose(descriptor_fis)
        push dword [descriptor_fis]
        call [fclose]
        add esp, 4 * 1
        
      final:
      
      mov ECX, [nr_caract]
      cmp ECX, 0
      je ecx_0
        
        mov ESI, 0
        repeta_3:
            
            mov EAX, 0              ;golim EAX
            mov AL, [text+ESI]      ; mutam valoare de pe pozitia ESI in AL
            sub AL, 'a'             ; aflam codul ascii al caracterului
            mov BH, [sir+EAX]       ; mutam valoarea din sirul de frecventa in BH
            add BH, 1               ; incrementam BH
            mov [sir+EAX], BH       ; facem update la frecventa
            inc ESI                 ; marim contorul de pozitie
            
        loop repeta_3
        
        mov ECX, 26
        mov ESI, 0
        
        repeta_4:
        
            mov AL, [sir+ESI]
            cmp AL, [maxim]
            jb mai_mic
                
                mov [maxim], AL
                mov [caract], byte ESI
                
            mai_mic:
            inc ESI
        
        loop repeta_4
            
      ecx_0:
        
        add [caract], byte 'a'
           
        
        mov EBX, [caract]
        and EBX, 000000FFh
        push EBX
        push dword format1
        call [printf]
        add ESP, 4 * 2
        
        push dword spatiu
        push dword format2
        add ESP, 4 * 2
        
        mov EAX, [maxim]
        and EAX, 000000FFh ; pastram doar valoarea din AL
        
        push EAX
        push dword format2
        call [printf]
        add ESP, 4 * 2
        
        ; exit(0)
        push    dword 0      
        call    [exit]       