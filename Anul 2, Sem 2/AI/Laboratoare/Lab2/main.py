import random
from queue import PriorityQueue
from reader import read
from writer import write

def bfs(matrice, sursa):
    solutie = []
    viz = [False] * len(matrice)
    prioQueue = PriorityQueue()
    prioQueue.put((0, sursa))
    viz[sursa] = True
    while not prioQueue.empty():
        nodCurent = prioQueue.get()[1]
        solutie.append(nodCurent)
        for vecin in range(len(matrice)):
            if viz[vecin] == False:
                viz[vecin] = True
                prioQueue.put((matrice[nodCurent][vecin], vecin))
    return solutie


def lungimeRuta(matrice, solutie, destination, nrProblema):
    if nrProblema == 2:
        lungime = 0
    else:
        lungime = matrice[solutie[-1]][solutie[0]]

    for i in range(1, len(solutie)):
        lungime += matrice[solutie[i - 1]][solutie[i]]
        if solutie[i] == destination:
            break
    return lungime


def aflaVecini(solutie):
    vecini = []
    for i in range(1, len(solutie)):
        for j in range(i + 1, len(solutie)):
            vecin = solutie.copy()
            vecin[i] = solutie[j]
            vecin[j] = solutie[i]
            vecini.append(vecin)
    return vecini


def celMaiBunVecin(matrice, vecini, destination, nrProblema):
    MIN = lungimeRuta(matrice, vecini[0], destination, nrProblema)
    minVecin = vecini[0]
    for vecin in vecini:
        lungimeCurenta = lungimeRuta(matrice, vecin, destination, nrProblema)
        if lungimeCurenta < MIN:
            MIN = lungimeCurenta
            minVecin = vecin
    return minVecin, MIN


def hill_climbing(matrice, sursa, destination, nrProblema):
    solutieCurenta = bfs(matrice, sursa)
    lungimeCurenta = lungimeRuta(matrice, solutieCurenta, destination, nrProblema)
    vecini = aflaVecini(solutieCurenta)
    minVecin, MIN = celMaiBunVecin(matrice, vecini, destination, nrProblema)

    while MIN < lungimeCurenta:
        solutieCurenta = minVecin
        lungimeCurenta = MIN
        vecini = aflaVecini(solutieCurenta)
        minVecin, MIN = celMaiBunVecin(matrice, vecini, destination, nrProblema)


    finalsolutie = []
    for i in solutieCurenta:
        finalsolutie.append(i)
        if i == destination:
            break

    return finalsolutie, lungimeRuta(matrice, finalsolutie, destination, nrProblema)



def main():

    n, matrice, sursa, dest = read.read()

    n = int(n)
    sursa = int(sursa)
    dest = int(dest)

    solutie1, routelungime1 = hill_climbing(matrice, 0, 999999, 1)
    solutie2, routelungime2 = hill_climbing(matrice, sursa-1, dest-1, 2)

    write.write(matrice, solutie1, routelungime1, solutie2, routelungime2)


if __name__ == "__main__":
    main()