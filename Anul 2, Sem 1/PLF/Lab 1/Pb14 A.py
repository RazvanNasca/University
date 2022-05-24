''' Clase '''
class Nod:
    def __init__(self, e):
        self.e = e
        self.urm = None
    
class Lista:
    def __init__(self):
        self.prim = None
        
''' functii '''
def creareLista():
    lista = Lista()
    lista.prim = creareLista_rec()
    return lista

def creareLista_rec():
    x = int(input("x = "))
    if x == 0:
        return None
    else:
        nod = Nod(x)
        nod.urm = creareLista_rec()
        return nod


def Incluziune(A, B):
    if eListaVida(A) == True:
        return True
    if Exista(A.prim, B) == False:
        return False
    return Incluziune(Sublista(A), B)

def Sublista(lista):
    lista = lista.prim.urm

def eListaVida(lista):
    if lista == None:
        return True
    return False

def Exista(elem, lista):
    return ExistaRec(elem, lista.prim)

def ExistaRec(elem, lista):
    if lista == None:
        return False
    if elem == lista.e:
        return True
    return ExistaRec(elem, lista.urm)

def main():
    print("Prima multime:\n")
    A = creareLista()
    print("A doua multime:\n")
    B = creareLista()

    print(Incluziune(A,B))
    
main() 
