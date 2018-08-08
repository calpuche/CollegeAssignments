# StorageTest.py
# Purpose: This aims to test the CPU performance of the SuT by attempting to successfully generate a file of 1 million random characters.
# Post: On successful generation, the console returns with "SUCCESS"; If an issue occurs, "ERROR: " will be printed to the console

import random
import string
import sys

def main():
    try:
        if generateFile(1000000):
            print("SUCCESS")
    except:
        print("ERROR - ", sys.exc_info())


def generateFile(charSize):
    try:
        file = open("storageTest.txt","w")
        count = 0
        text = ""
        while (count<charSize):
            randomChar = random.choice(string.ascii_letters)
            text += randomChar
            count += 1
        # makes sure that file name is consistent, overwritten every time
        file.write(text)
        # close the file
        file.close()
        # return True once we get to this point in the execution
        return True
    except FileNotFoundError:
        return False
    except OSError:
        return False
main()