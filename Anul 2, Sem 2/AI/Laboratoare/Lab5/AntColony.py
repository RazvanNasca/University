import random as rn
import numpy as np
from numpy.random import choice as np_choice

class AntColony(object):

    def __init__(self, matrix, ants, bestAnt, iterations, decay, alpha=1, beta=1):

        self.matrix  = matrix
        self.pheromone = np.ones(self.matrix.shape) / len(matrix)
        self.all_inds = range(len(matrix))
        self.ants = ants
        self.bestAnt = bestAnt
        self.iterations = iterations
        self.decay = decay
        self.alpha = alpha
        self.beta = beta

    def run(self):
        shortestPath = None
        bestPath = ("", np.inf)
        print(bestPath)
        for i in range(self.iterations):
            allPath = self.getAllPath()
            self.spreadPheronome(allPath, self.bestAnt, shortestPath=shortestPath)
            shortestPath = min(allPath, key=lambda x: x[1])
            print (shortestPath)
            if shortestPath[1] < bestPath[1]:
                bestPath = shortestPath
            self.pheromone = self.pheromone * self.decay
        return bestPath

    def spreadPheronome(self, allPath, bestAnt, shortestPath):
        sortedPaths = sorted(allPath, key=lambda x: x[1])
        for path, dist in sortedPaths[:bestAnt]:
            for move in path:
                self.pheromone[move] += 1.0 / self.matrix[move]

    def getAllPath(self):
        allPath = []
        for i in range(self.ants):
            path = self.genPath(0)
            length = 0
            for elem in path:
                length += self.matrix[elem]
            allPath.append((path, length))
        return allPath

    def genPath(self, start):
        path = []
        visited = set()
        visited.add(start)
        prev = start
        for i in range(len(self.matrix) - 1):
            move = self.pickMove(self.pheromone[prev], self.matrix[prev], visited)
            path.append((prev, move))
            prev = move
            visited.add(move)
        path.append((prev, start))
        return path

    def pickMove(self, pheromone, dist, visited):
        pheromone = np.copy(pheromone)
        pheromone[list(visited)] = 0

        row = pheromone ** self.alpha * (( 1.0 / dist) ** self.beta)

        norm_row = row / row.sum()
        move = np_choice(self.all_inds, 1, p=norm_row)[0]
        return move