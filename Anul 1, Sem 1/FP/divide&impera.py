def interval(l):
    if len(l) > 1:
        mij = len(l)//2
        lleft = l[:mij]
        lright = l[mij:]

        interval(lleft)
        interval(lright)

        i = 0
        j = 0
        k = 0
        while i < len(lright):
            l[k] = lright[i]
            i += 1
            k += 1
        while j < len(lleft):
            l[k] = lleft[j]
            j += 1
            k += 1


def run():
    l = [1,2,3,4,5]
    interval(l)
    print(l)

run()
