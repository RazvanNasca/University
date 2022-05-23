class Student:
    def __init__(self, id_stud, nume):
        self.__id_stud = id_stud
        self.__nume = nume

    def get_id_stud(self):
        # obtine id-ul studentului

        return self.__id_stud

    def get_nume(self):
        # obtine numele studentului

        return self.__nume

class Lab:

    def __init__(self, id_stud, nr_lab, nr_pb):
        self.__id_stud = id_stud
        self.__nr_lab = nr_lab
        self.__nr_pb = nr_pb

    def get_id_stud(self):
        # obtine id-ul studentului

        return self.__id_stud

    def get_nr_lab(self):
        # obtine numele studentului

        return self.__nr_lab

    def get_nr_pb(self):
        # obtine numele studentului

        return self.__nr_pb