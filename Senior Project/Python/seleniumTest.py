from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import Select
import time


driver = webdriver.Safari()
driver.get("http://calpuche.create.stedwards.edu/CopOrDrop/index.php")
assert "Cop or Drop" in driver.title
elem = driver.find_element_by_name("username")
elem.clear()
elem.send_keys("clankfan@yahoo.com")
elem = driver.find_element_by_name("password")
elem.clear()
elem.send_keys("password")
elem.send_keys(Keys.RETURN)
time.sleep(2)
ids = driver.find_elements_by_xpath('//*[@id]')
for ii in ids:
    #   print ii.tag_name
    print(ii.get_attribute('id'))    # id name as strings

elem = driver.find_element_by_id("Yeezy V2").click()

time.sleep(2)
ids = driver.find_elements_by_xpath('//*[@id]')
for ii in ids:
    #   print ii.tag_name
    print(ii.get_attribute('id'))    # id name as string

driver.find_element_by_xpath('//*[@id="inputSize"]/option[7]').click()


elem = driver.find_element_by_id("firstName")
elem.clear()
elem.send_keys("Test Selenium FN")

elem = driver.find_element_by_id("lastName")
elem.clear()
elem.send_keys("Test Selenium LN")

elem = driver.find_element_by_id("inputEmail")
elem.clear()
elem.send_keys("TestSelenium@selenium.com")

elem = driver.find_element_by_id("inputAddress")
elem.clear()
elem.send_keys("7802 Test Selenium St.")

elem = driver.find_element_by_id("inputCity")
elem.clear()
elem.send_keys("Test")

elem = driver.find_element_by_id("inputState")
elem.clear()
elem.send_keys("Selenium")

elem = driver.find_element_by_id("inputZip")
elem.clear()
elem.send_keys("10100")

elem = driver.find_element_by_id("confirmOrder").click()

    #assert "No results found." not in driver.page_source
#driver.close()