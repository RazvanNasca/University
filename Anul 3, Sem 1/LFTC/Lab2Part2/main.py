import re

atomLex = {"ID": 0,
           "CONST": 1,
           "#include": 2,
           "{": 3,
           "}": 4,
           "<<": 5,
           ">>": 6,
           "cin": 7,
           "cout": 8,
           "main()": 9,
           "namespace": 10,
           "std": 11,
           "int": 12,
           "double": 13,
           "<iostream>": 14,
           "using": 15,
           "+": 16,
           "-": 17,
           "*": 18,
           "=": 19,
           " ": 20,
           ";": 21}

LITERE = ["a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
          "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"]
NUMERE = ['0', '1', '2', '3', '4', '5', '6', '7', '8', '9']

FIP = []
TS_ID = []
TS_CONST = []

stariCONST = []
stariID = []
alfabetCONST = []
alfabetID = []
tranzitiiCONST = []
tranzitiiID = []
stariFinaleCONST = []
stariFinaleID = []
stareInitialaCONST = []
stareInitialaID = []
numarTranzitiiCONST = 0
numarTranzitiiID = 0


def addInTSID(word, size):
    for (it, nr) in TS_ID:
        if it == word:
            return nr
    TS_ID.append((word, size))
    return size


def addInTSCONST(word, size):
    for (it, nr) in TS_CONST:
        if it == word:
            return nr
    TS_CONST.append((word, size))
    return size


def verifyCONST(secventa):
    stareF = ""
    stareI = stareInitialaCONST[0]
    ok = True
    i = 0
    while i < len(secventa) and ok:
        caracter = secventa[i]
        urmCaracter = ""
        for tranzitie in tranzitiiCONST:
            if tranzitie[0] == stareI and tranzitie[2] == "numar" and caracter in NUMERE:
                urmCaracter = tranzitie[1]
                break
            if tranzitie[0] == stareInitialaCONST[0] and caracter == "-" and tranzitie[2] == "-":
                urmCaracter = tranzitie[1]
                break

        if urmCaracter == "":
            ok = False

        if urmCaracter in stariFinaleCONST and i == len(secventa) - 1:
            ok = True
            stareF = urmCaracter
            break

        stareI = urmCaracter
        stareF = urmCaracter

        i = i + 1

    if stareF not in stariFinaleCONST:
        ok = False

    return ok


def verifyID(secventa):
    stareF = ""
    stareI = stareInitialaID[0]
    ok = True
    i = 0
    while i < len(secventa) and ok:
        caracter = secventa[i]
        urmCaracter = ""
        for tranzitie in tranzitiiID:
            if tranzitie[0] == stareI and tranzitie[2] == "litera" and caracter in LITERE:
                urmCaracter = tranzitie[1]
                break
            if tranzitie[0] != stareInitialaID[0] and tranzitie[2] == "numar" and caracter in NUMERE and i > 0:
                urmCaracter = tranzitie[1]
                break

        if urmCaracter == "":
            ok = False

        if urmCaracter in stariFinaleID and i == len(secventa) - 1:
            ok = True
            stareF = urmCaracter
            break

        stareI = urmCaracter
        stareF = urmCaracter

        i = i + 1

    if stareF not in stariFinaleID:
        ok = False

    return ok


def citireDinFisierID():
    file = open("ID.txt", "r")
    line = file.readline().strip().split(' ')
    for word in line:
        stariID.append(word)

    stareInitialaID.append(file.readline().strip())
    numarTranzitiiID = int(file.readline())

    i = 1
    while i <= numarTranzitiiID:
        line = file.readline()
        words = line.strip().split(" ")
        tranzitiiID.append((words[0], words[1], words[2]))
        if words[2] not in alfabetID:
            alfabetID.append(words[2])
        if words[3] == '1':
            stariFinaleID.append(words[0])
        i = i + 1

    print("ID-uri citite cu succes!")


def citireDinFisierCONST():
    file = open("CONST.txt", "r")
    line = file.readline().strip().split(' ')
    for word in line:
        stariCONST.append(word)

    stareInitialaCONST.append(file.readline().strip())
    numarTranzitiiCONST = int(file.readline())

    i = 1
    while i <= numarTranzitiiCONST:
        line = file.readline()
        words = line.strip().split(" ")
        tranzitiiCONST.append((words[0], words[1], words[2]))
        if words[2] not in alfabetCONST:
            alfabetCONST.append(words[2])
        if words[3] == '1':
            stariFinaleCONST.append(words[0])
        i = i + 1

    print("CONST-uri citite cu succes!")

def main():

    citireDinFisierID()
    citireDinFisierCONST()

    file = open("code.txt", "r")
    lines = file.readlines()
    file.close()

    cntLines = 0

    for line in lines:
        words = line.strip().split(" ")
        cntLines += 1
        for word in words:
            CONST = verifyCONST(word)
            if CONST:
                size = len(TS_CONST)
                addInTSCONST(word, size)
                FIP.append((word, atomLex["ID"], size))
            else:
                if word in atomLex:
                    FIP.append((word, atomLex[word], "-"))
                else:
                    if len(word) > 8:
                        print("Error on line " + str(cntLines) + ": word is too large -> " + word)
                    ID = verifyID(word)
                    if ID:
                        size = len(TS_ID)
                        cod = addInTSID(word, size)
                        FIP.append((word, atomLex["CONST"], cod))
                    else:
                        print("Errors on line " + str(cntLines) + " for " + word)

    TS_CONST.sort()
    TS_ID.sort()

    print("FIP: ")
    print(FIP)

    print("CONST: ")
    print(TS_CONST)

    print("ID: ")
    print(TS_ID)

main()

