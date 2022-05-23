import datetime

def varsta():
    zi = int(input())
    luna = int(input())
    an = int(input())
    print(str(datetime.datetime.now() - datetime.datetime(an,luna,zi)))


varsta()
