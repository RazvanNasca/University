from exceptii.exceptii import *

class LabUI:

    def __ui_add_stud(self):
        id = input("id:")
        nume = input("nume:")

        self.__RepoStud.add_stud(id,nume)

    def __ui_cauta_stud(self):
        id = input("id:")

        self.__RepoStud.cauta_stud(id)

    def __init__(self, RepoStud, RepoLab):
        self.__RepoStud = RepoStud
        self.__RepoLab = RepoLab

        self.__comenzi = {

            "add_stud" : self.__ui_add_stud,
            "cauta_stud" : self.__ui_cauta_stud

        }

    def run(self):

        while True:

            print("add_stud -> adaugare stud")
            print("cauta_stud -> cauta stud")
            cmd = input("Da ceva")

            if cmd == "exit":
                return
            else:
                if cmd in self.__comenzi:
                    try:
                        self.__comenzi[cmd]()
                    except RepoError as re:
                        print("Eroare in repo!\n" + str(re))
                    except ValidError as vale:
                        print("Eroare la validare!\n" + str(vale))
                    except ValueError as ve:
                        print("nu i buna valoarea")
                else:
                    print("comanda rea")