def read():
    fisierIntrare = open("dateIntrare.txt", "r")
    n = fisierIntrare.readline()

    matrice = []

    for i in range(int(n)):
        linie = fisierIntrare.readline()
        vect = linie.split(",")
        rez = []
        for j in range(int(n)):
            rez.append(int(vect[j]))
        matrice.append(rez)


    fisierIntrare.close()

    return int(n), matrice