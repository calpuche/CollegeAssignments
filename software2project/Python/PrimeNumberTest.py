# PrimeNumberTest.py
# Purpose: This aims to test the CPU capabilities of the SuT by attempting to successfully find a large amount of prime numbers.
# Post: On successful generation, the console returns with "SUCCESS"; If an issue occurs, "ERROR: " will be printed to the console

import sys

def main():
    try:
        if findPrimes(1000):
            print ("SUCCESS")
    except:
        print ("ERROR - ", sys.exc_info())

def findPrimes(upperLim):
    primeList = []
    try:
        for num in range(0,upperLim + 1):
        # prime numbers are greater than 1
            if num > 1:
                for i in range(2,num):
                    if (num % i) == 0:
                        break
                else:
                    primeList.append(num)
        return True
    except ArithmeticError as ae:
        print ("ERROR - ", ae)

if __name__ == '__main__':
    main()