from random import randint
from utils.utils import generateNewValue
from random import random

class Chromosome:
    def __init__(self, problParam=None):
        self.__problParam = problParam
        self.__repres = [generateNewValue(problParam['min'], problParam['max']) for _ in range(problParam['noNodes'])]
        self.__fitness = 0.0

    @property
    def repres(self):
        return self.__repres

    @property
    def fitness(self):
        return self.__fitness

    @repres.setter
    def repres(self, l=[]):
        self.__repres = l

    @fitness.setter
    def fitness(self, fit=0.0):
        self.__fitness = fit

    def crossover(self, c):
        # multi-point crossover
        beginning = randint(1,2)
        r = randint(beginning, len(self.__repres) - 1)
        newrepres = []
        for i in range(len(self.__repres)):
            if i % r == 0:
                newrepres.append(self.__repres[i])
            else:
                newrepres.append(c.__repres[i])

        offspring = Chromosome(c.__problParam)
        offspring.repres = newrepres
        return offspring

    def mutation(self):
        # random reseting
        pos = randint(0, len(self.__repres) - 1)
        prob = random()
        if prob < 0.5:
            self.__repres[pos] = generateNewValue(self.__problParam['min'], self.__problParam['max'])

    def __str__(self):
        return '\nChromo: ' + str(self.__repres) + ' has fit: ' + str(self.__fitness)

    def __repr__(self):
        return self.__str__()

    def __eq__(self, c):
        return self.__repres == c.__repres and self.__fitness == c.__fitness