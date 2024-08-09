package utility;

import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import factory.DriverFactory;


/**
 * This class provides Utility methods for Browser requirements such as clear cache.
 * </BR>
 * @author usharani A
 * 
 */
public class BrowserUtility    {
	
	/**
	 * This method closes the Current Window.
	 */
	public static void closeCurrentWindow() {
		if (DriverFactory.getDriver() != null) {
			closeCurrentWindow(DriverFactory.getDriver());
		}
	}
	
	/**
	 * This method is used to switch and close active window for current webdriver.
	 *  
	 * @param driver - webdriver 
	 *  
	 */
	
	public static void closeCurrentWindow(WebDriver driver) {
		try {
			if (driver != null) {
				String currentHandle = driver.getWindowHandle();
				Set<String> handles = driver.getWindowHandles();
				DriverFactory.quitDriver(driver);
				for (String winHandle : handles) {
					if (!currentHandle.equals(winHandle)) {
						driver.switchTo().window(winHandle);
						System.out.println("BrowserUtility.closeCurrentWindow()");
						driver.close();
					}
				}
				TimeUnit.SECONDS.sleep(5);
			}
		} catch (org.openqa.selenium.NoSuchElementException | InterruptedException e) {
			e.printStackTrace();
		}
	}
	

}