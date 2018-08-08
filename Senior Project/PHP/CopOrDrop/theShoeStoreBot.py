from pyvirtualdisplay import Display
from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import Select
import time


driver = webdriver.PhantomJS()
driver.get("http://calpuche.create.stedwards.edu/testWebsite/index.php")
assert "The Shoe Store" in driver.title
#ids = driver.find_elements_by_xpath('//*[@id]')
#for ii in ids:
    #   print ii.tag_name
   # print(ii.get_attribute('id'))    # id name as strings

elem = driver.find_element_by_id("post4.jpg").click()
print("Picked Item")

time.sleep(1)
ids = driver.find_elements_by_xpath('//*[@id]')

driver.find_element_by_xpath('//*[@id="inputSize"]/option[7]').click()

elem = driver.find_element_by_id("inputSize").send_keys("9.5")
print("picked size")

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
print("Inserted email")

elem = driver.find_element_by_id("inputAddress")
elem.clear()
elem.send_keys("7401 Amanda Ellis Way")
print("Inserted Address")

elem = driver.find_element_by_id("inputCity")
elem.clear()
elem.send_keys("Austin")
print("Inserted City")

elem = driver.find_element_by_id("inputState")
elem.clear()
elem.send_keys("Texas")
print("Inserted State")

elem = driver.find_element_by_id("inputZip")
elem.clear()
elem.send_keys("78749")
print("Inserted Zip")


elem = driver.find_element_by_id("CreditCardPayment").click()
print("Going to Next Page")

time.sleep(1)

elem = driver.find_element_by_id("CreditCardName")
elem.clear()
elem.send_keys("Carlos Alpuche")
print("Inserted Credit Card Name")

elem = driver.find_element_by_id("CreditCardNumber")
elem.clear()
elem.send_keys("12345678991011")
print("Inserted Credit Card Number")

elem = driver.find_element_by_id("inputMonth").send_keys("05")
print("Inserted Credit Card Month")


elem = driver.find_element_by_id("Year")
elem.clear()
elem.send_keys("2022")
print("Inserted Credit Card Year")

elem = driver.find_element_by_id("SecurityCode")
elem.clear()
elem.send_keys("222")
print("Inserted Security Code")

elem = driver.find_element_by_id("ConfirmOrder").click()
print("Order Complete")


    #assert "No results found." not in driver.page_source
driver.close()