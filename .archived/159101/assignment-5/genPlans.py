import random
import json

def genName(firstNames, lastNames):
    string = firstNames[random.randrange(0, len(firstNames))] + " "
    if random.getrandbits(1):
        string += firstNames[random.randrange(0, len(firstNames))] + " "
    string += lastNames[random.randrange(0, len(lastNames))]
    return string

def genNumber():
    num = random.randrange(10000000, 100000000)
    string = "0" + str(num)
    string = string[0 : 2] + " " + string[2 : ]
    string = string[0 : 6] + " " + string[6 : ]
    return string

def genData():
    string = str(random.randrange(50, 1000, 25))
    for i in range(2):
        string += ";" + str(random.randrange(50, 1000, 25))
    return string

def main():
    plans = open("plans.txt", "w")
    firstNames = open("nameDict.json")
    lastNames = open("lastnameDict.json")

    nameArr1 = json.load(firstNames)
    nameArr2 = json.load(lastNames)

    for i in range(0, random.randrange(100)):
        string = genName(nameArr1, nameArr2) + ";"
        string += genNumber() + ";"
        string += genData()

        #push the string to the plans.txt file.
        plans.write(string + "\n")
    
    plans.close()
    firstNames.close()
    lastNames.close()

main()