# step4: invatare model (cu tool linear_model.LogisticRegression() -- [link](https://scikit-learn.org/stable/modules/generated/sklearn.linear_model.LogisticRegression.html) -- si cu implementare proprie)

# identify (by training) the classifier

# using sklearn
from sklearn import linear_model


def learn(trainInputs, trainOutputs, labels):
    classifier = linear_model.LogisticRegression()
    classifier.fit(trainInputs, trainOutputs)
    w0 = classifier.intercept_
    w1 = [classifier.coef_[i][0] for i in range(len(labels))]
    w2 = [classifier.coef_[i][1] for i in range(len(labels))]
    w3 = [classifier.coef_[i][2] for i in range(len(labels))]
    w4 = [classifier.coef_[i][3] for i in range(len(labels))]
    print('classification models: (tool)\n')
    for i in range(len(labels)):
        print('y' + str(i + 1) + '(feat1, feat2, feat3, feat4) = ', w0[i], ' + ', w1[i], ' * feat1 + ', w2[i],
              ' * feat2 + ', w3[i], ' * feat3 + ', w4[i], ' * feat4\n')


# using developed code
from MyLogisticRegression import MyLogisticRegression


def myLearn(trainInputs, trainOutputs, testInputs, testOutputs, labels):
    # train the classifier (fit in on the training data)
    classifier = MyLogisticRegression()
    classifier.fit(trainInputs, trainOutputs)
    # parameters of the liniar regressor
    w0 = classifier.intercept_
    w1 = [classifier.coef_[i][0] for i in range(len(labels))]
    w2 = [classifier.coef_[i][1] for i in range(len(labels))]
    w3 = [classifier.coef_[i][2] for i in range(len(labels))]
    w4 = [classifier.coef_[i][3] for i in range(len(labels))]
    print('classification models: (manual)\n')
    for i in range(len(labels)):
        print('y' + str(i + 1) + '(feat1, feat2, feat3, feat4) = ', w0[i], ' + ', w1[i], ' * feat1 + ', w2[i],
              ' * feat2 + ', w3[i], ' * feat3 + ', w4[i], ' * feat4\n')

    # step5: testare model, plot rezultate, forma outputului si interpretarea lui

    # makes predictions for test data
    # computedTestOutputs = [w0 + w1 * el[0] + w2 * el[1] for el in testInputs]

    # makes predictions for test data (by tool)
    computedTestOutputs = classifier.predict(testInputs)

    rez1 = [labels[i] for i in testOutputs]
    print("Test outputs: ", rez1)
    rez2 = [labels[i] for i in computedTestOutputs]
    print("Computed test outputs", rez2)

    # step6: calcul metrici de performanta (acc)

    # evalaute the classifier performance
    # compute the differences between the predictions and real outputs
    # print("acc score: ", classifier.score(testInputs, testOutputs))
    error = 0.0
    for t1, t2 in zip(computedTestOutputs, testOutputs):
        if t1 != t2:
            error += 1
    error = error / len(testOutputs)
    print("classification error (manual): ", error)

    from sklearn.metrics import accuracy_score
    error = 1 - accuracy_score(testOutputs, computedTestOutputs)
    print("classification error (tool): ", error)
