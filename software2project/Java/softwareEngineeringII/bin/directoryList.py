# directoryList.py
#
# Simple program that gets a list of files in the current directory.
# This test simply determines if the program can access the file system.
# Prints SUCCESS if successful, FAIL if not
#
# This program runs on the client

import os

def main():

    result = os.listdir(".")        # Get the directory listing in the current directory
    length = len(result)            # Are there files?
    if length > 0:
        print("SUCCESS")
    else:
        print("FAIL")

main()