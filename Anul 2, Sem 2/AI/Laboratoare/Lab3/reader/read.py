import numpy as np
import networkx as nx


def readNet(fileName):
    net = {}
    H = nx.read_gml(fileName)
    # H = nx.read_gml(fileName, label='id')
    Matrix = nx.adjacency_matrix(H).todense()
    array = np.squeeze(np.asarray(Matrix))

    # construct matrix
    mat = []
    for i in array:
        lin = []
        for j in i:
            lin.append(int(j))
        mat.append(lin)

    n = len(mat)
    net['noNodes'] = n
    net["mat"] = mat

    # determinate noEdges
    degrees = []
    noEdges = 0
    for i in range(n):
        d = 0
        for j in range(n):
            if mat[i][j] == 1:
                d += 1
            if j > i:
                noEdges += mat[i][j]
        degrees.append(d)
    net["noEdges"] = noEdges
    net["degrees"] = degrees

    return net


def modularity(communities, param):
    noNodes = param['noNodes']
    mat = param['mat']
    degrees = param['degrees']
    noEdges = param['noEdges']
    M = 2 * noEdges
    Q = 0.0
    for i in range(0, noNodes):
        for j in range(0, noNodes):
            if communities[i] == communities[j]:
               Q += (mat[i][j] - degrees[i] * degrees[j] / M)
    return Q * 1 / M
