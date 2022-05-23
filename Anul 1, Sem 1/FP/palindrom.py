def palindrom(x):
    n = x
    m = 0
    while n != 0:
        m = m * 10 + n%10
        n = int(n/10)
    return m

xx = int(input())
p = palindrom(xx)



if xx == p:
    print("Numarul este palindrom")
else:
    print("Numarul nu este palindrom")
