# consider some real labels and some predicted labels (obtained by the ML algorithm - a classifier)
# we want ot estimate the error of prediction (classification)

# Problem specification:
# input: realLabels, computedLabels - arrays of the same length containing binary labels (some discrete values)
# output: accuracy, precision, recall - real values in range [0,1]


# a balanced data set (each class containes the same numer of samples)

realOutputs = [[3, 9.5, 4, 5.1, 6, 7.2, 2, 1], [2, 7, 4.5, 6, 3, 8, 3, 1.2]]
computedOutputs = [[3, 10, 4, 5.1, 7, 7.9, 20, 1],[2, 7, 4.5, 6, 3, 8, 3, 1.2]]

realLabels = ['mere', 'pere', 'pere', 'nuci', 'mere', 'nuci', 'mere', 'pere', 'nuci']
computedLabels = ['mere', 'nuci', 'pere', 'mere', 'pere', 'nuci', 'nuci', 'mere', 'pere']


from math import sqrt


def regresion(realOutputs, computedOutputs):

    mae = 0
    rmse = 0

    for listReal, listComputed in zip(realOutputs, computedOutputs):
        mae += sum(abs(r - c) for r, c in zip(listReal, listComputed)) / len(listReal)
        rmse += sqrt(sum((r - c) ** 2 for r, c in zip(listReal, listComputed)) / len(listReal))

    return mae/len(realOutputs), rmse/len(computedOutputs)

# version 2 - native code
def evalClassificationV2(realLabels, computedLabels):
    # noCorrect = 0
    # for i in range(0, len(realLabels)):
    #     if (realLabels[i] == computedLabels[i]):
    #         noCorrect += 1
    # acc = noCorrect / len(realLabels)
    acc = sum([1 if realLabels[i] == computedLabels[i] else 0 for i in range(0, len(realLabels))]) / len(realLabels)

    rez = []
    for i in range(0, len(realLabels)):
        if realLabels[i] not in rez:
            rez.append(realLabels[i])

    TP = 0
    FP = 0
    TN = 0
    FN = 0

    for i in range(0, len(rez)):
        for j in range(len(realLabels)):
            if realLabels[j] == rez[i] and computedLabels[i] == rez[i]:
                TP += 1
            if realLabels[j] != rez[i] and computedLabels[i] == rez[i]:
                FP += 1
            if realLabels[j] != rez[i] and computedLabels[i] != rez[i]:
                TN += 1
            if realLabels[j] == rez[i] and computedLabels[i] != rez[i]:
                FN += 1

        # TP = sum([1 if (realLabels[j] == rez[i] and computedLabels[j] == rez[i]) else 0 for j in range(len(realLabels))])
        # FP = sum([1 if (realLabels[j] != rez[i] and computedLabels[j] == rez[i]) else 0 for j in range(len(realLabels))])
        # TN = sum([1 if (realLabels[j] != rez[i] and computedLabels[j] != rez[i]) else 0 for j in range(len(realLabels))])
        # FN = sum([1 if (realLabels[j] == rez[i] and computedLabels[j] != rez[i]) else 0 for j in range(len(realLabels))])

    precisionPos = TP / (TP + FP)
    recallPos = TP / (TP + FN)

    precisionNeg = TN / (TN + FN)
    recallNeg = TN / (TN + FP)

    return acc, precisionPos, precisionNeg, recallPos, recallNeg


mae, rmse = regresion(realOutputs, computedOutputs)
print('mae: ', mae, ' rmse: ', rmse)

acc, precPos, precNeg, recallPos, recallNeg = evalClassificationV2(realLabels, computedLabels)
print('acc: ', acc, ' precision positive: ', precPos, ' precision negative: ', precNeg, ' recall positive: ', recallPos, ' recall negative ', recallNeg)