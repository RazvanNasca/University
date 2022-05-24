import numpy as np
from reader import read
from writer import write

from AntColony import AntColony

def main():

    distances = np.array(read.read())

    ants = 1
    bestAnts = 1
    iterations = 10
    decay = 0.95

    ant_colony = AntColony(distances, ants, bestAnts, iterations, decay, alpha=1, beta=1)
    shortest_path = ant_colony.run()

    finalResult = []
    for i in range (len(shortest_path[0])):
        finalResult.append(shortest_path[0][i][0])

    print ("shorted_path: ", finalResult, " length ", shortest_path[1])

    write.write(str(len(finalResult)), finalResult, shortest_path[1])

main()