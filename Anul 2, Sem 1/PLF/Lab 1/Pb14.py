''' Clase '''
class Nod:
    def __init__(self, e):
        self.e = e
        self.urm = None
    
class Lista:
    def __init__(self):
        self.prim = None
        
''' functii '''

def creeazaListaVida():
    lista = Lista()
    return lista

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

def tipar(lista):
    tipar_rec(lista.prim)
    
def tipar_rec(nod):
    if nod != None:
        print (nod.e)
        tipar_rec(nod.urm)
        
def elim(nod, elem, listaNoua):
    if nod == None:
        return listaNoua
    if nod.e != elem:
        listaNoua = Concat(nod.e, listaNoua)
    return elim(nod.urm, elem, listaNoua)

def Concat(val, listaNoua):
    print(val)
    nod = Nod(val)
    if listaNoua == None:
        listaNoua = nod
        return None
    else:
        if listaNoua.urm == None:
            listaNoua.urm = nod
        listaNoua.urm = Concat(nod, listaNoua.urm)
        return listaNoua

def Sublista(lista):
    lista = lista.prim.urm
    
def primLista(lista):
    return lista.prim

def eListaVida(lista):
    if lista.prim == None:
        return True
    return False

def main():
    list = creareLista()
    listaNoua = creeazaListaVida()
    
    element = int(input("elementul = "))
    listaNoua.prim = elim(list.prim, element, listaNoua.prim)
    tipar(listaNoua)
    
main() 
