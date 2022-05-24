from sklearn.datasets import load_iris
import normalization


def main():
    data = load_iris()
    inputs = data['data']
    outputs = data['target']
    feature_names = list(data['feature_names'])

    inputs = [[feature[feature_names.index('sepal length (cm)')], feature[feature_names.index('sepal width (cm)')],feature[feature_names.index('petal length (cm)')], feature[feature_names.index('petal width (cm)')]] for feature in inputs]

    labels = [label for label in set(outputs)]

    normalization.split(inputs, outputs, labels)

main()
