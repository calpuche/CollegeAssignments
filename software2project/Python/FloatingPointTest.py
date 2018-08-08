# StorageTest.py
# Purpose: This aims to test the Storage of the SuT by attempting to successfully perform floating point arithmetic.
# Post: On successful generation, the console returns with "SUCCESS"; If an issue occurs, "ERROR: " will be printed to the console

import sys
import random

from datetime import datetime
LOG_PATH = "log.txt"

def main() :

    result = ""

    try :
        result = run()
    except KeyError as ke :
        result = str(ke)

    except IndexError as ie :
        result = str(ie)

    except RuntimeError as re :
        result = str(re)

    finally:               # finally is optional and always executes

        try:
            logResult(result)
        except IOError as ioe:

            print("%s" % str(ioe))
        else:
            print(result)

#====================================================================================
# run()
# Runs the test.  Returns SUCCESS if the test is successful, and ERROR with a message
# if it fails
#====================================================================================
def run():

    try:
        computeFloats()
    except RuntimeError as re:
        result = "ERROR: RuntimeError caught in FloatingPointTest.py" + str(re)
        raise RuntimeError(result)

    except FloatingPointError as fpe:
        result = "ERROR: FloatingPointError caught in FloatingPointTest.py" + str(fpe)
        raise FloatingPointError(result)

    return "SUCCESS: FloatingPointTest.py"

def validateFloatSum(sum):
    if sum > 0:
        return True

def computeFloats():
    temp = 0.0
    for num in range(0, 1000):
        temp = num
        rand = random.randrange(1,9)
        num = (float(num)/float(rand))
        temp = temp + num
    if validateFloatSum(temp):
        return True
    else:
        raise Exception

def logResult(result) :

    testDateTime = datetime.now().strftime("%Y-%m-%d %H:%M:%S")

    # Create a new file
    try :
        file = open(LOG_PATH, "a")
        message = str(testDateTime) + " " + result + "\n"
        file.write(message)
    except IOError as x:
        message = "ERROR: Cannot open file " + LOG_PATH + " for append"
        raise IOError(message)

if __name__ == '__main__':
    main()