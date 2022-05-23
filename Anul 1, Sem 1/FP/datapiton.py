import math

def prim( xx ):
    x = xx
    r = int(math.sqrt(x))
    if x < 2:
        return 0
    if x == 2:
        return 1
    if x % 2 == 0:
        return 0;
    for i in range( 3, r+1, 2):
        ii = int(i)
        if x % ii == 0:
            return 0
    return 1
   

nn = input()
n = int(nn)

if prim( n ) == 1:
    n+=1

while prim(n) == 0:
    n+=1

print(n)
        
