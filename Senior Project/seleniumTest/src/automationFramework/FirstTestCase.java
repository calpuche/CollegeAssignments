package automationFramework;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class FirstTestCase {
	private WebDriver driver;
	  private String baseUrl;
	  @Before
	  public void setUp() throws Exception {
		Properties props = System.getProperties();
		props.setProperty("webdriver.chrome.driver", "/Users/calpuche/downloads/chromedriver");
	    driver = new ChromeDriver();
	    baseUrl = "http://adidas.com/";
	    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	  }
	  @Test
	  public void ultraboostTest() throws Exception {
	    driver.get(baseUrl + "us/ultraboost-shoes/BB6059.html");
	   // driver.findElement(By.id("pdp_size_select_mask")).click();
	    //driver.findElement(By.linkText("09.5")).click();
	    //driver.findElement(By.id("pdp_addtocart_button")).click();
	    //Thread.sleep(1500);
	    //driver.findElement(By.id("header_cart_button")).click();
	    //driver.findElement(By.id("cart_paypal_button")).click();
	    }
}
