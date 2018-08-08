# MemoryTest.py
#
# Author: Eddie
# Simple script that performs a memory test on the SUT hardware
# This test determines if the program can produce a list with one million items
# Prints SUCCESS if successful, FAIL if not
#
# This program will run on a SUT

import random
import sys


def generateList(someList):
    try:
        count = 0
        while (count<1048576):
            someList.append(random.randint(0,9))
            count+=1
        return someList
    except MemoryError as me:
        print("ERROR - ", me), False

def main():
    try:
        memList = []
        memList = generateList(memList)
        if len(memList) > 0:
            print("SUCCESS")
    except:
        print("ERROR - ", sys.exc_info())
main()