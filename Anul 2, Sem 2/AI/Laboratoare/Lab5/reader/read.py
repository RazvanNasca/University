import numpy as np

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

    for i in range(int(n)):
        for j in range(int(n)):
            if matrice[i][j] == 0:
                matrice[i][j] = np.inf

    return matrice
