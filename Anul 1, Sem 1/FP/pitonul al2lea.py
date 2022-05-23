import math
print ("Citeste numarul")
n = input()
m = int(n)
r = math.sqrt(m)
rr = int(r)
ok = 1
if m < 2 :
    ok = 0
else :
    if m == 2 :
        ok = 1
    else :
        if m % 2 == 0 :
            ok = 0
        else :
            for i in range (3, rr, 2):
                j = int(i)
                if m % j == 0:
                    ok = 0
if ok == 1 :
    print("Numerul este prim")
else :
    print("Numerul nu este prim")
