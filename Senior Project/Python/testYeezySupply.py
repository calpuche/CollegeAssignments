from selenium import webdriver
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import Select
import time


driver = webdriver.Safari()
driver.get("https://yeezysupply.com/")
print(driver.title)
assert "YEEZY SUPPLY" in driver.title

driver.find_element_by_xpath('//*[@id="js-main"]/div/a[52]').click()
time.sleep(2)


el = driver.find_element_by_xpath('//*[@id="P-12175240275"]/div[2]/form/div[2]/div/select')
for option in el.find_elements_by_tag_name('option'):
    if option.text == 'M':
        option.click() # select() in earlier versions of webdriver
        break

#select.select_by_visible_text("M")
#driver.find_element_by_xpath('//*[@id="P-12175240275"]/div[2]/form/input[3]')
    #assert "No results found." not in driver.page_source
