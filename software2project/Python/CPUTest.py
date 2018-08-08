import random
# import Python.customModules.valueList

# CPUTest.py
# Purpose: This aims to test the CPU of the SuT by attempting to successfully generate 1 million random integers.
# Post: On successful generation, the console returns with "SUCCESS"; If an issue occurs, "ERROR: " will be printed to the console

# guidelines:
    # every section of code should run in a DEFINED main method, just in case specific tests would like to be imported as modules in the future

def main():
    index = 10
    try:
        while index>0:
            if generateNums(1):
                index-=1
        print("SUCCESS")

    except ArithmeticError as ae:
        print("ERROR - ", ae)

def generateNums(UPPERBOUND):
    try:
        for i in range(UPPERBOUND):
            random.randint(0,9)
            i+=1
        return True
    except ArithmeticError as ae:
        print("Error - ", ae)
        return False
main()