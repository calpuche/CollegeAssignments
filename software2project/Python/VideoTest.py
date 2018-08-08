# VideoTest.py
# Purpose: This aims to test the Video-Graphics ability of the SUT by attempting to take and verify a screenshot.
# Post: On successful generation, console returns with "SUCCESS"; if an issue occurs,"ERROR: " will be printed to the console
from PIL import ImageGrab
import os

def getAndVerifyIMG(imgFile):

    try:
        imgFile = os.stat("screenshotTest.png")
        fileSize = imgFile.st_size
        if (fileSize > 0):
            return True
    except OSError(["screenshotTest.png"]):
        return False

def main():
    #
    # IMG HANDLER
    #
    img = ImageGrab.grab()
    img.save("screenshotTest.png")

    try:
        if getAndVerifyIMG(img):
            print("SUCCESS")
    except FileNotFoundError as fnf:
        print("Error - ", fnf)

main()