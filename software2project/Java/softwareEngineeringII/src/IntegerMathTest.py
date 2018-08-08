# IntegerMathTest.py Purpose: This aims to test the Memory of the SuT by attempting to successfully generate a list
# of 1 million random integers. Post: On successful generation, the console returns with "SUCCESS"; If an issue
# occurs, "ERROR: " will be printed to the console
import random
import sys


def main():
    try:
        if generateList(1000000):
            print("SUCCESS")
    except:
        print("ERROR - ", sys.exc_info())

def generateList(UPPERBOUND):
    try:
        count = 0
        num_list = []
        while count < UPPERBOUND:
            num_list.append(random.randint(0, 9))
            count += 1
        return True
    except ArithmeticError as ae:
        return ("ERROR - ", ae), False
main()