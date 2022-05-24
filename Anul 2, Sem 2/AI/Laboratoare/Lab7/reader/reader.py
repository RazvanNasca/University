import csv


def reader(filename, firstField, secondField, output):
    data = []
    names = []
    with open(filename) as csv_file:
        csvReader = csv.reader(csv_file, delimiter=',')
        line = 0
        for row in csvReader:
            if line == 0:
                names = row
            else:
                data.append(row)
            line += 1

    variable1 = names.index(firstField)
    gdp = [float(data[i][variable1]) for i in range(len(data))]

    variable2 = names.index(secondField)
    freedom = [float(data[i][variable2]) for i in range(len(data))]
    
    variable3 = names.index(output)
    outputs = [float(data[i][variable3]) for i in range(len(data))]

    return gdp, outputs, freedom
