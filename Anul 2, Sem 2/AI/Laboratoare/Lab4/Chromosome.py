from random import randint, seed


def generateARandomPermutation(n):
    perm = [i for i in range(n)]
    pos1 = randint(1, n - 1)
    pos2 = randint(1, n - 1)
    perm[pos1], perm[pos2] = perm[pos2], perm[pos1]
    return perm


# permutation-based representation
class Chromosome:
    def __init__(self, problParam=None):
        self.__problParam = problParam  # problParam has to store the number of nodes/cities
        self.__repres = generateARandomPermutation(self.__problParam['noNodes'])
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
        pos1 = randint(1, self.__problParam['noNodes'] - 2)
        pos2 = randint(pos1 + 1, self.__problParam['noNodes'] - 1)

        if pos1 > pos2:
            pos1, pos2 = pos2, pos1

        newrepres = [self.__repres[0]]
        for i in range(1, pos1):
            newrepres.append(self.__repres[i])
        for i in range(pos2, self.__problParam['noNodes']):
            newrepres.append(self.__repres[i])

        for i in range(0, self.__problParam['noNodes']):
            if (c.__repres[i]) not in newrepres:
                newrepres.append(c.__repres[i])


        offspring = Chromosome(self.__problParam)
        offspring.repres = newrepres
        return offspring

    def mutation(self):
        # insert mutation
        pos1 = randint(2, self.__problParam['noNodes'] - 1)

        newrepres = [0]
        for i in range(pos1, self.__problParam['noNodes']):
            newrepres.append(self.__repres[i])
        for i in range(1, pos1):
            newrepres.append(self.__repres[i])


    def __str__(self):
        return "\nChromo: " + str(self.__repres) + " has fit: " + str(self.__fitness)

    def __repr__(self):
        return self.__str__()

    def __eq__(self, c):
        return self.__repres == c.__repres and self.__fitness == c.__fitness