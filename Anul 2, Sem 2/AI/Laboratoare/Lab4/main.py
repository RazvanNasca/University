import math
import os
import warnings
import matplotlib.pyplot as plt
from reader import reader
from GA import *
from math import *
from writer import writer


def main():
    # plot the network

    n , matrix = reader.read()

    # initialise de GA parameters
    fact = math.factorial(n)
    gaParam = {'popSize': fact, 'noGen': 4}
    # problem parameters
    problParam = {'function': modularity, 'matrix': matrix, 'noNodes': n}

    ga = GA(gaParam, problParam)
    ga.initialisation()
    ga.evaluation()

    for g in range(gaParam['noGen']):
        # logic alg
        ga.oneGeneration()

        bestChromo = ga.bestChromosome()

        communities = {}
        for i in range(len(bestChromo.repres)):
            if bestChromo.repres[i] in communities.keys():
                communities[bestChromo.repres[i]].append(i)
            else:
                communities[bestChromo.repres[i]] = [i]

        print('Best solution in generation ' + str(g) + ' is: x = ' + str(bestChromo.repres) + ' f(x) = ' + str(bestChromo.fitness))

    chromosome = ga.bestChromosome()
    print(chromosome)
    writer.write(matrix, chromosome.repres, chromosome.fitness)


main()

