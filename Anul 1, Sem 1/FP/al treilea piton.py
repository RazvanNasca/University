print("Citeste primul numar")
aa = input()
print("Citeste al doilea numar")
bb = input()
a = int(aa)
b = int(bb)
d = 0
r = 0
if b == 0:
    d = a
else:
    while b != 0:
        r = a % b
        a = b
        b = r
    d = a
if a == 0:
    if b == 0:
        print (1)
else:
    print (d)
