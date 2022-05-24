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

FIP = []
TS_ID = []
TS_CONST = []

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

def main():
    file = open("code.txt", "r")
    lines = file.readlines()
    file.close()

    cntLines = 0

    for line in lines:
        words = line.strip().split(" ")
        cntLines += 1
        for word in words:
            CONST = re.search("^-?\d*\.?\d*$", word)
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
                    ID = re.search("^[a-zA-Z][1-9]*", word)
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

