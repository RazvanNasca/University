from domain.domain import *
from exceptii.exceptii import *

class RepoStud:
    def __init__(self, fileN):
        self.__entities = fileN

    def __loadFromFile(self):
        try:
            f = open(self.__entities, "r")
        except IOError:
            # fisierul nu exista
            return
        line = f.readline().strip()
        rez = []
        while line != "":
            atrib = line.split(" ")
            st = Student(atrib[0], atrib[1])
            rez.append(st)
            line = f.readline().strip()

        f.close()
        return rez

    def __storeToFile(self, studs):

        with open(self.__entities, "w") as f:
            for stud in studs:
                strf = str(stud.get_id_stud()) + " " + stud.get_nume() + "\n"
                f.write(strf)
            f.close()

    def add_stud(self, id, nume):
        # adauga in lista daca id-ul nu se afla in lista

        all = self.__loadFromFile()

        stud = Student(id,nume)

        for i in all:
            if int(i.get_id_stud()) == int(stud.get_id_stud()):
                print("id existent!\n")
                return
        all.append(stud)
        self.__storeToFile(all)

    def cauta_stud(self,id):

        all = self.__loadFromFile()

        for stud in all:
            if int(stud.get_id_stud()) == int(id):
                raise RepoError("id Existent!")
        print("nu ii deja")
        return

class RepoLab:
    def __init__(self, fileN):
        self.__entities = fileN

    def __loadFromFile(self):
        try:
            f = open(self.__entities, "r")
        except IOError:
            # fisierul nu exista
            return
        line = f.readline().strip()
        rez = []
        while line != "":
            atrib = line.split(" ")
            lab = Lab(atrib[0], atrib[1], atrib[2])
            rez.append(lab)
            line = f.readline().strip()

        f.close()
        return rez

    def __storeToFile(self, labs):

        with open(self.__entities, "w") as f:
            for lab in labs:
                strf = str(lab.get_id_stud()) + " " + str(lab.get_nr_lab()) + " " + str(lab.get_nr_pb()) + "\n"
                f.write(strf)
            f.close()