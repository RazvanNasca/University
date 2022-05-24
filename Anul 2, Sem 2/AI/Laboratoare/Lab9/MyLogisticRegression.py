import numpy as np
from math import exp
import random


def sigmoid(x):
    return 1 / (1 + exp(-x))


class MyLogisticRegression:
    def __init__(self):
        self.intercept_ = []
        self.coef_ = []

    # use the gradient descent method
    # simple stochastic GD
    def fit(self, x, y, learningRate=0.002, noEpochs=1000):
        self.coef_ = []
        labels = np.unique(y)
        for l in range(len(labels)):
            # self.coef_ = [0.0 for _ in range(1 + len(x[0]))]    #beta or w coefficients y = w0 + w1 * x1 + w2 * x2 + ...
            self.coef_.append([random.random() for _ in range(len(x[0]) + 1)])  # beta or w coefficients
            binaryY = []
            for o in y:
                if o == l:
                    binaryY.append(1)
                else:
                    binaryY.append(0)
            for epoch in range(noEpochs):
                # TBA: shuffle the trainind examples in order to prevent cycles
                for i in range(len(x)):  # for each sample from the training data
                    ycomputed = sigmoid(self.eval(x[i], self.coef_[l]))  # estimate the output
                    crtError = ycomputed - binaryY[i]  # compute the error for the current sample
                    for j in range(0, len(x[0])):  # update the coefficients
                        self.coef_[l][j + 1] = self.coef_[l][j + 1] - learningRate * crtError * x[i][j]
                    self.coef_[l][0] = self.coef_[l][0] - learningRate * crtError * 1

        self.intercept_ = [f[0] for f in self.coef_]
        self.coef_ = [f[1:] for f in self.coef_]

    def eval(self, xi, coef):
        yi = coef[0]
        for j in range(len(xi)):
            yi += coef[j + 1] * xi[j]
        return yi

    def predictOneSample(self, sampleFeatures):
        computed = []
        for i in range(len(self.intercept_)):
            coefficients = [self.intercept_[i]] + [c for c in self.coef_[i]]
            computedFloatValue = self.eval(sampleFeatures, coefficients)
            computed.append(sigmoid(computedFloatValue))
        computedLabel = np.argmax(computed)
        return computedLabel

    def predict(self, inTest):
        computedLabels = [self.predictOneSample(sample) for sample in inTest]
        return computedLabels
