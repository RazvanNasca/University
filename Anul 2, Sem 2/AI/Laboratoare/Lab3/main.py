import os
import warnings
import matplotlib.pyplot as plt
from reader.read import *
from GA import GA


def main():
    # load a tetwork

    crtDir = os.getcwd()
    filePath = os.path.join(crtDir, 'data', 'dolphins.gml')
    network = readNet(filePath)

    # plot the network

    warnings.simplefilter('ignore')

    # initialise de GA parameters
    gaParam = {'popSize': 50, 'noGen': 100}
    # problem parameters
    problParam = {'min' : 10, 'max' : 35,'function': modularity, 'network': network, 'noNodes': network['noNodes']}

    ga = GA(gaParam, problParam)
    ga.initialisation()
    ga.evaluation()

    for g in range(gaParam['noGen']):
        # logic alg
        # ga.oneGeneration()
        ga.oneGenerationElitism()
        # ga.oneGenerationSteadyState()

        bestChromo = ga.bestChromosome()

        communities = {}
        for i in range(len(bestChromo.repres)):
            if bestChromo.repres[i] in communities.keys():
                communities[bestChromo.repres[i]].append(i)
            else:
                communities[bestChromo.repres[i]] = [i]

        print('Best solution in generation ' + str(g) + ' is: x = ' + str(bestChromo.repres) + ' f(x) = ' + str(bestChromo.fitness))

    chromosome = ga.bestChromosome()

    A = np.matrix(network["mat"])
    G = nx.from_numpy_matrix(A)
    pos = nx.spring_layout(G)  # compute graph layout
    plt.figure(figsize=(20, 20))  # image is 8 x 8 inches
    nx.draw_networkx_nodes(G, pos, node_size=800, cmap=plt.cm.RdYlBu, node_color=chromosome.repres)
    nx.draw_networkx_edges(G, pos, alpha=0.3)
    plt.show()

    communities = {}
    for i in range(len(chromosome.repres)):
        if chromosome.repres[i] in communities.keys():
            communities[chromosome.repres[i]].append(i)
        else:
            communities[chromosome.repres[i]] = [i]

    print("\nNumber of communities: " + str(len(communities)) + "\n")

    for com in communities:
        print("Community " + str(com) + ": " + str(communities[com]))


main()

