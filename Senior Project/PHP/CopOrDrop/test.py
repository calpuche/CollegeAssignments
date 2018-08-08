#!/home/calpuche/virtualenv/python35/3.5/bin
from pyvirtualdisplay import Display
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import Select
import time


driver = webdriver.PhantomJS()
driver.get("http://calpuche.create.stedwards.edu/CopOrDrop/index.php")
assert "Cop or Drop" in driver.title
elem = driver.find_element_by_name("username")
elem.clear()
elem.send_keys("Selenium@Selenium.com")
elem = driver.find_element_by_name("password")
elem.clear()
elem.send_keys("Q9@UYQ&amp;eHF*bp#&amp;8d3x3")
elem.send_keys(Keys.RETURN)
time.sleep(2)

print("Successfully Logged In")

ids = driver.find_elements_by_xpath('//*[@id]')
#for ii in ids:
    #   print ii.tag_name
    #print(ii.get_attribute('id'))    # id name as strings

elem = driver.find_element_by_id("Yeezy V2").click()

time.sleep(2)
ids = driver.find_elements_by_xpath('//*[@id]')
#for ii in ids:
    #   print ii.tag_name
    #print(ii.get_attribute('id'))    # id name as string

elem = driver.find_element_by_id("inputSize").send_keys("9.5")
print("Picked Item")

elem = driver.find_element_by_id("firstName")
elem.clear()
elem.send_keys("Carlos")
print("Inserted First Name")
elem = driver.find_element_by_id("lastName")
elem.clear()
elem.send_keys("Alpuche")
print("Inserted Last Name")
elem = driver.find_element_by_id("inputEmail")
elem.clear()
elem.send_keys("calpuche@stedwards.edu")
print("Inserted Email")
elem = driver.find_element_by_id("inputAddress")
elem.clear()
elem.send_keys("3001 South Congress Ave.")

elem = driver.find_element_by_id("inputCity")
elem.clear()
elem.send_keys("Austin")

elem = driver.find_element_by_id("inputState")
elem.clear()
elem.send_keys("Texas")

elem = driver.find_element_by_id("inputZip")
elem.clear()
elem.send_keys("78703")
print("Inserted Address")
elem = driver.find_element_by_id("confirmOrder").click()

    #assert "No results found." not in driver.page_source
driver.close()