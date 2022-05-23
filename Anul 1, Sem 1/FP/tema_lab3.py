def citire(v):
    print("Introduceti un numar natural nenul: ")
    n = int(input())
    if n < 1:
        print("Introdu un numar natural nenul!!")
        exit()
    print("Introduceti un sir de numere: ")
    for i in range(0,n):
        x = int(input())
        v.append(x)
    sume_partiale(v)

def sume_partiale(v):
    s = 0
    n = len(v)
    for i in range(0,n):
        s = s + v[i]
        suma.append(s)

def P11(v):
    st = 0
    dr = 0
    smin = 0
    imin = -1
    smax = suma[0]
    n = len(v)

    for i in range(0,n):
        if suma[i] < smin:
            smin = suma[i]
            imin = i
            
        if smax < (suma[i] - smin):
            smax = suma[i] - smin
            st = imin + 1
            dr = i
    print("Secventa de suma maxima este: ", end = " ")
    
    for i in range(st, dr+1):
        print(v[i],end=" ")
    print("")

def P13(v):
    suma_ceruta = 5
    init = 0
    fin = 0
    lmax = 0
    gasit  = 0
    n = len(v)

    for j in range(0, n):
        if suma[j] == suma_ceruta:
            fin = j
            lmax = j + 1
            gasit = 1

    for i in range (1, n-1):
        for j in range (i+1, n):
            if suma[j] - suma[i-1] == suma_ceruta and lmax < j - i + 1:
                init = i
                fin = j
                lmax = j - i + 1
    if gasit == 1:
        print("Secventa de lungime maxima de suma 5 este: ", end=" ")
        for i in range(init, fin+1):
            print(v[i],end=" ")
    else:
        print("Nu exista o astfel de secventa!")
    print("")

def P1(v):
    secvmax = -1
    secvcrt = 1
    st = 0
    stsecv = 0
    nrcrt = v[0]
    n = len(v)
    dr = n-1

    for i in range(1, n):
        if nrcrt >= v[i]:
            if secvcrt > secvmax:
                secvmax = secvcrt
                dr = i - 1
                st = stsecv
            secvcrt = 1
            stsecv = i
        else:
            secvcrt += 1
    print("Secventa strict crescatoare de lungime maxima este: ", end=" ")       
    for i in range(st, dr+1):
        print(v[i], end=" ")
    print("")


#v = []
#citire(n)

suma = []


def run():
    v = []
    commands={
    "1":citire,
    "2":P11,
    "3":P13,
    "4":P1
    }
    while True:
        print("")
        print("Intoduceti un numar din lista de mai jos")
        print("1. Citire")
        print("2. Secventa de lungime maxima de suma maxima")
        print("3. Secventa maxima de suma 5")
        print("4. Secventa maxima strict crescatoare")
        print("5. Iesire")
        print("")
        cmd = input()
        if cmd == "5":
            exit()
        if cmd in commands:
            commands[cmd](v)
        else:
            print("Comanda invalida!")



run()          
        
















    

