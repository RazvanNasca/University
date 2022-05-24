# S->AB
# A->Ca
# B->aB
# B->b
# B->#
# C->Ba

# S->AB
# A->a
# A->#
# B->b
# B->#


import re

productions = []
first = {}

def verify_first():
    for elem in first:
        if re.search("[A-Z]", first[elem]):
            return False
    return True

def has_eps(letter):
    if '#' in first[letter]:
        return True
    return False

def main():
    file = open("input.txt", "r")
    lines = file.readlines()
    file.close()

    for line in lines:
        words = line.strip().split("->")
        productions.append((words[0], words[1]))

    productions.sort(key=lambda p: p[1][0], reverse=True)

    for production in productions:
        if production[0] not in first:
            first[production[0]] = production[1][0]
        else:
            first[production[0]] += production[1][0]

    ok = verify_first()
    while not ok:
        for elem in first:
            if re.search("[A-Z]", first[elem]): #daca gasim litera mare
                string = ""
                no_eps = True
                for letter in first[elem]: #parcurgem toate literele
                    if re.search("[a-z]", letter): #daca e litera mica
                        string += letter
                        no_eps = False
                    if re.search("[A-Z]", letter): #daca e litera mare
                        if not has_eps(letter):
                            no_eps = False
                        aux = first[letter]
                        string += aux.replace('#', '')

                if no_eps:
                    string += "#"

                first[elem] = string

        ok = verify_first()

    print(first)

main()