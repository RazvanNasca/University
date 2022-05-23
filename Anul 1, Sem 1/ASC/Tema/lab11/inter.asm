bits 32 

global intercalare        
     

segment code use32 class=code
    intercalare:
        cld                 ; DF = 0 sensul de la stanga la dreapta
        repeta: 
            mov ESI, EDX    ; mutam sirul din EDX in ESI
            movsb           ; EDI = ESI   incrementeaza ESI si EDI
            mov EDX, ESI       
            mov ESI, EBX
            movsb
            mov EBX, ESI    ; adaugam pe rand in sirul destinatie caracterele din sir
        loop repeta
        
        ret
            