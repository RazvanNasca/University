import os
from reader.reader import *
from sklearn_tool import regression


def main():

    crtDir = os.getcwd()
    filePath = os.path.join(crtDir, 'data', 'world-happiness-report-2017.csv')

    gdp, outputs, freedom = reader(filePath, 'Economy..GDP.per.Capita.', 'Freedom', 'Happiness.Score')

    regression.function(gdp, freedom, outputs)


main()
