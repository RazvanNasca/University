#AlteBule
def bubbleSort1(l):
    for j in range(1,len(l)):
        for i in range(len(l) - j):
            if l[i+1] > l[i]:
                l[i+1], l[i] = l[i], l[i+1]
#bulele
def bubbleSort(l):
    sorted = False
    while not sorted:
        sorted = True
        for i in range(len(l)-1):
            if l[i] > l[i+1]:
                l[i], l[i+1] = l[i+1], l[i]
                sorted = False

#selectie
def selectionSort(l):
    for i in range(len(l)-1):
        ind = i
        for j in range(i+1,len(l)):
            if l[j] < l[ind]:
                ind = j
        if i < ind:
            l[i], l[ind] = l[ind], l[i]
            

#selectieDirecta
def selectionSortDirect(l):
    for i in range(len(l) - 1):
        for j in range(i+1,len(l)):
            if l[i] > l[j]:
                l[i], l[j] = l[j], l[i]

#insertie
def insertie(l):
    for i in range(1,len(l)):
        ind = i - 1
        a = l[i]
        while ind >= 0 and a < l[ind]:
            l[ind+1] = l[ind]
            ind -= 1
        l[ind+1] = a

#MergeSort
def MergeSort(l):
    if len(l) > 1:
        mij = len(l) // 2
        lleft = l[:mij]
        lright = l[mij:]
        MergeSort(lleft)
        MergeSort(lright)

        i = 0
        j = 0
        k = 0

        while i < len(lleft) and j < len(lright):
            if lleft[i] < lright[j]:
                l[k] = lleft[i]
                i += 1
                k += 1
            else:
                l[k] = lright[j]
                j += 1
                k += 1
        while i < len(lleft):
            l[k] = lleft[i]
            i += 1
            k += 1
        while j < len(lright):
            l[k] = lright[j]
            j += 1
            k += 1

#QuickSort
def QuickSort(l, left, right):
    pos = partition(l,left,right)
    if pos - 1 >= left:
        QuickSort(l,left,pos-1)
    if pos + 1 <= right:
        QuickSort(l,pos+1,right)

def partition(l,left,right):
    pivot = l[left]
    i = left
    j = right

    while i != j:
        while pivot <= l[j] and i < j:
            j -= 1
        l[i] = l[j]
        while pivot >= l[i] and i < j:
            i += 1
        l[j] = l[i]
    l[i] = pivot
    return i

def cautareSec(l,el):
    if len(l) == 0:
        return 0
    poz = -1
    for i in range(len(l)):
        if el >= l[i]:
            poz = i
            #print(l[i])
    if poz == -1:
        return len(l)
    return poz

def run():
    l = [-2, 0, 1, 10, 13, 100]
    #selectionSort(l)
    #print(l)
    el = 110
    p = cautareSec(l,el)
    print(p)
run()
