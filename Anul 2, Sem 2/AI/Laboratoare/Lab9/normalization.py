from sklearn.preprocessing import StandardScaler
import numpy as np
import learn


# step2: impartire pe train si test
# step3: normalizare

# split data into train and test subsets

def normalisation(trainData, testData):
    scaler = StandardScaler()
    if not isinstance(trainData[0], list):
        # encode each sample into a list

        trainData = [[d] for d in trainData]
        testData = [[d] for d in testData]

        # rezTrainData = []
        # for i in trainData:
        #     line = []
        #     for j in i:
        #         line.append(j)
        #     rezTrainData.append(line)
        #
        # rezTestData = []
        # for i in testData:
        #     line = []
        #     for j in i:
        #         line.append(j)
        #     rezTestData.append(line)

        scaler.fit(trainData)  # fit only on training data
        normalisedTrainData = scaler.transform(trainData)  # apply same transformation to train data
        normalisedTestData = scaler.transform(testData)  # apply same transformation to test data

        # decode from list to raw values
        normalisedTrainData = [el[0] for el in normalisedTrainData]
        normalisedTestData = [el[0] for el in normalisedTestData]
    else:
        scaler.fit(trainData)  # fit only on training data
        normalisedTrainData = scaler.transform(trainData)  # apply same transformation to train data
        normalisedTestData = scaler.transform(testData)  # apply same transformation to test data
    return normalisedTrainData, normalisedTestData


def split(inputs, outputs, labels):
    np.random.seed(5)
    indexes = [i for i in range(len(inputs))]
    trainSample = np.random.choice(indexes, int(0.8 * len(inputs)), replace=False)
    testSample = [i for i in indexes if not i in trainSample]

    trainInputs = [inputs[i] for i in trainSample]
    trainOutputs = [outputs[i] for i in trainSample]
    testInputs = [inputs[i] for i in testSample]
    testOutputs = [outputs[i] for i in testSample]

    # normalise the features
    trainInputs, testInputs = normalisation(trainInputs, testInputs)

    learn.learn(trainInputs, trainOutputs, labels)
