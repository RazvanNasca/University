import re

def main():
    file = open("code.txt", "r")
    lines = file.readlines()
    file.close()

    atomSet = {""}
    for line in lines:
        words = line.strip().split(" ")
        for word in words:
            atomSet.add(word)
    atomSet.pop()

    f = open("atoms.txt", "w")
    for atom in atomSet:
        f.write(atom + "\n")

    f.close()

    print(atomSet)

main()

