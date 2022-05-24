from sklearn import linear_model


def function(trainInputs1, trainInputs2, trainOutputs):

    x = [[el1, el2] for el1, el2 in zip(trainInputs1, trainInputs2)]
    reg = linear_model.LinearRegression()
    reg.fit(x, trainOutputs)
    w0, w1, w2 = reg.intercept_, reg.coef_[0], reg.coef_[1]
    print('f(x) = ', w2, ' * x^2 + ', w1, ' * x + ', w0)
