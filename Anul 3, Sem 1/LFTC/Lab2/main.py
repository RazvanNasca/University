#   stare, {' ', stare}
#   stare
#   CONST
#   {stare, stare, ID, val}
#
#   stare = 'q', CONST
#   CONST = '1' | '2' | '3' | '4' | '5' | '6' | '7' | '8' | '9' | '0'
#   ID = [a-z]
#   val = '1' | '0'

stari = []
alfabet = []
tranzitii = []
stariFinale = []
stareInitiala = []
numarTranzitii = 0

def citireDeLaTastatura():
    print("Multimea starilor: ")
    words = input().strip().split(" ")
    for word in words:
        stari.append(word)

    print("Stare initiala: ")
    stareInitiala = input()
    while stareInitiala not in stari:
        print("Starea initiala trebuie sa fie in lista starilor! Reincercati! ")
        stareInitiala = input()

    print("Stari finale: ")
    stariFinale = input().strip().split(" ")
    ok = 0
    while ok == 0:
        ok = 1
        for stare in  stariFinale:
            if stare not in stari:
                ok = 0
        if ok == 0:
            print("Starile finale trebuie sa fie in lista starilor! Reincercati! ")
            stariFinale = input().strip().split(" ")

    print("Numarul tranzitiilor: ")
    numarTranzitii = int(input())

    print("Tranziti( stare intiala, stare finala, valoare): ")
    i = 1
    while i <= numarTranzitii:
        tranzitie = input().strip().split(" ")
        tranzitii.append((tranzitie[0], tranzitie[1], tranzitie[2]))
        i = i + 1

    print("Date citite cu succes!")


def citireDinFisier():
    file = open("input.txt", "r")
    line = file.readline().strip().split(' ')
    for word in line:
        stari.append(word)

    stareInitiala.append(file.readline().strip())
    numarTranzitii = int(file.readline())

    i = 1
    while i <= numarTranzitii:
        line = file.readline()
        words = line.strip().split(" ")
        tranzitii.append((words[0], words[1], words[2]))
        if words[2] not in alfabet:
            alfabet.append(words[2])
        if words[3] == '1':
            stariFinale.append(words[0])
        i = i + 1

    print("Date citite cu succes!")


def modalitateDeCitire():
    print("Introduce cifra: ")
    cmd = input()
    if cmd == "1":
        citireDeLaTastatura()
    else:
        if cmd == "2":
            citireDinFisier()
        else:
            print("Comanda invalida! Reincearca!")
            modalitateDeCitire()


def afiseazaElementeAF():
    while 1:
        print("\n1. Multimea starilor.\n2. Alfabetul de intrare.\n3. Tranzitiile.\n4. Starile finale.\n0. Exit.")
        print("Intorduceti cifra: ")
        cmd = input()
        if cmd == "1":
            print(stari)
        else:
            if cmd == "2":
                print(alfabet)
            else:
                if cmd == "3":
                    print(tranzitii)
                else:
                    if cmd == "4":
                        print(stariFinale)
                    else:
                        if cmd == "0":
                            return
                        else:
                            print("Comanda invalida! Reincercati! ")


def verificareSecventa(secventa):
    stareF = ""
    stareI = stareInitiala[0]
    ok = True
    i = 0
    while i < len(secventa) and ok:
        caracter = secventa[i]
        urmCaracter = ""
        for tranzitie in tranzitii:
            if tranzitie[0] == stareI and tranzitie[2] == caracter:
                urmCaracter = tranzitie[1]
                break

        if urmCaracter == "":
            ok = False

        if urmCaracter in stariFinale and i == len(secventa) - 1:
            ok = True
            stareF = urmCaracter
            break

        stareI = urmCaracter
        stareF = urmCaracter

        i = i + 1

    if stareF not in stariFinale:
        ok = False

    return ok



def celMaiLungPrefix(secventa):
    stareF = ""
    stareI = stareInitiala[0]
    prefix = ""
    salvare = ""

    i = 0
    while i < len(secventa):
        caracter = secventa[i]
        urmatorul = ""
        for tranzitie in tranzitii:
            if tranzitie[0] == stareI and tranzitie[2] == caracter:
                urmatorul = tranzitie[1]
                prefix += tranzitie[2]
                break

        if urmatorul == "":
            break

        if urmatorul in stariFinale:
            salvare = prefix

        if urmatorul in stariFinale and i == len(secventa) - 1:
            stareF = urmatorul
            break

        stareI = urmatorul
        stareF = urmatorul

        i = i + 1

    if stareF not in stariFinale:
        return salvare
    return prefix


def meniu():
    while 1:
        print("\n1. Afisarea elementelor automatului finit.\n2. Verifica daca o secventa e acceptata de automatul finit.\n3. Determina cel mai lung prefix dintr-o secventa data care este acceptata de automat.\n0. Exit.")
        print("Intorduceti cifra: ")
        cmd = input()
        if cmd == "1":
            afiseazaElementeAF()
        else:
            if cmd == "2":
                print("Introduceti secventa")
                secventa = input()
                print(verificareSecventa(secventa))
            else:
                if cmd == "3":
                    print("Introduceti secventa")
                    secventa = input()
                    print(celMaiLungPrefix(secventa))
                else:
                    if cmd == "0":
                        return
                    else:
                        print("Comanda invalida! Reincercati! ")

def main():
    print("Modalitati de citire: ")
    print("1. De la tastatura.")
    print("2. Din fisier.")
    modalitateDeCitire()
    meniu()

main()
