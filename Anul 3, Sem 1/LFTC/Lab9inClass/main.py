import re

terminal = []

def main():
    file = open("input.txt", "r")
    lines = file.readlines()
    file.close()

    for line in lines:
        words = line.strip().split("->")
        for word in words:
            letters = re.findall('[a-z]', word)
            for letter in letters:
                if letter != ' ' and letter not in terminal:
                    terminal.append(letter)

    print(terminal)


main()