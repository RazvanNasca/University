def write(matrice, solution1, routeLength1):
    fisierIesire = open("dateIesire.txt", "w")

    fisierIesire.write(str(len(matrice)))
    fisierIesire.write("\n")

    rez1 = ""
    for i in range(0, len(solution1) - 1):
        rez1 += str(solution1[i] + 1)
        rez1 += ","
    rez1 += str(solution1[len(solution1) - 1] + 1)

    fisierIesire.write(str(rez1))
    fisierIesire.write("\n")
    fisierIesire.write(str(routeLength1))
    fisierIesire.write("\n")
