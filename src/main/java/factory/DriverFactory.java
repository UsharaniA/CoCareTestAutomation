
package factory;

import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import constants.EnvConstants;
import utility.EnvHelper;


/**
 * @author usharani A
 *
 * This class works on the Factory Pattern to load the required Driver based on the requested Browser.
 *
 */
public class DriverFactory {

	private static Map<String, WebDriver> drivers = new HashMap<String, WebDriver>();
	
	static {
		
//		new File(EnvHelper.getValue(EnvConstants.driversFolderPath)).mkdirs();
//			new File(EnvHelper.getValue(EnvConstants.inputDataPath)).mkdirs();
			new File(EnvHelper.getValue(EnvConstants.reportsPath)).mkdirs();
//			new File(EnvHelper.getValue(EnvConstants.logsPath)).mkdirs();
	}
	
	/**
	 * 
	 * This method provides the current webdriver.
	 *
	 */
	public static WebDriver getDriver() {
		WebDriver driver = null;
		for (String key : drivers.keySet()) {
			driver = drivers.get(key); 
			if (driver != null) {
				break;
			}
		}
		
		return driver;
	}
	
	
	/**
	 * This method returns the webdriver based on the requested Browser.
	 * 
	 * @param browserName - Name of the Browser, @see BrowserConstants
	 * @return WebDriver
	 * 
	 * @see BrowserConstants
	 */
	public static WebDriver getDriver(String browserName) {
		
		return getDriver(browserName, null);
	}
	 
	/**
	 * This method returns the webdriver based on the requested Browser.
	 * 
	 * @param browserName - Name of the Browser, @see BrowserConstants
	 * @param downloadFilePath - download path to be set in case of Chrome Browser
	 * @return WebDriver
	 * 
	 * @see BrowserConstants
	 */
	
	public static WebDriver getDriver(String browserName, String downloadFilePath) {
		WebDriver driver = null;
		driver = drivers.get(EnvConstants.chromeDriver);
		 // Retrieve the headless configuration
        boolean isHeadless = Boolean.parseBoolean(EnvHelper.getValue("headless.driver"));
     // Build the full path
        String fullDriversPath = System.getProperty("user.dir")  + EnvHelper.getValue(EnvConstants.driversFolderPath)+EnvHelper.getValue(EnvConstants.chromeDriver);
        System.out.println("fullDriversPath:"+ fullDriversPath);
		System.setProperty("webdriver.chrome.driver", fullDriversPath);
//      System.setProperty("webdriver.chrome.driver", "D:\\Eclipse\\chromedriver\\chromedriver.exe");

//		System.out.println(EnvHelper.getValue(EnvConstants.driversFolderPath)+EnvHelper.getValue(EnvConstants.chromeDriver));
//		WebDriverManager.chromedriver().setup();		
		
		if (downloadFilePath!=null && !downloadFilePath.equals("")) {
			ChromeOptions options = new ChromeOptions();
//			HashMap<String, Object> chromePrefs = new HashMap<>();
//			chromePrefs.put(
//					"profile.default_content_settings.popups", 0);
//			chromePrefs.put("download.default_directory", downloadFilePath);
//			options.setExperimentalOption("prefs", chromePrefs);
//			options.addArguments("--test-type");
			  // Set desired capabilities using ChromeOptions
	        options.addArguments("--start-maximized"); // Start browser maximized
	        options.addArguments("--disable-popup-blocking"); // Disable popup blocking
	        options.addArguments("--disable-extensions"); 
	        options.addArguments("--remote-allow-origins=*");
	        options.addArguments("--incognito"); // Open Chrome in incognito mode
	        
	     // Add headless argument if headless mode is enabled
	        if (isHeadless) {
	            options.addArguments("--headless");
	            options.addArguments("--disable-gpu"); // Required for headless mode in some environments
	            options.addArguments("--window-size=1920,1080"); // Optional: Define the window size
	        }
			
//			options.setCapability(ChromeOptions.CAPABILITY, options);
			driver = new ChromeDriver(options);
		} else {
			driver = new ChromeDriver();
		}
		drivers.put(EnvConstants.chromeDriver, driver);
	
        return driver;
}
		
		

	
	
//	/**
//	 * This method sets the compatibility settings for the current Browser with current URL.
//	 * @param driver - current webdriver
//	 * @param url - url used to open the Browser.
//	 * 
//	 * @throws InterruptedException
//	 * @throws AWTException
//	 */
//	public static void setBrowserCompatibility(WebDriver driver, String url) throws InterruptedException, AWTException {
//		
//		Class<InternetExplorerDriver> ieDriverClass = org.openqa.selenium.ie.InternetExplorerDriver.class;
//		// Compatibility setting for IE Browser
//		if (ieDriverClass.isInstance(driver)) {
//		
//			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//			driver.get(url);
//	
//			Robot robot = new Robot();
//			robot.keyPress(KeyEvent.VK_ALT);
//			robot.keyPress(KeyEvent.VK_T);
//			TimeUnit.SECONDS.sleep(1); // Sleep 1 Second
//			robot.keyPress(KeyEvent.VK_B);
//			TimeUnit.SECONDS.sleep(1);
//			robot.keyRelease(KeyEvent.VK_ALT);
//	
//			robot.keyPress(KeyEvent.VK_ALT);
//			robot.keyPress(KeyEvent.VK_A);
//			robot.keyRelease(KeyEvent.VK_ALT);
//			TimeUnit.SECONDS.sleep(1);
//			robot.keyPress(KeyEvent.VK_ALT);
//			robot.keyPress(KeyEvent.VK_C);
//			robot.keyRelease(KeyEvent.VK_ALT);
//			TimeUnit.SECONDS.sleep(1);
//			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
//			driver.manage().window().maximize();
//
//		}
//	}
	
	/**
	 * This method quits all the drivers in current session.
	 */
	public static void closeAllDrivers() {
		for (String key : drivers.keySet()) {
			drivers.get(key).close();
			drivers.get(key).quit();
			quitDriver(drivers.get(key));
		}
	}
	

	/**
	 * This method quits the provided webdriver.
	 * @param driver - webdriver to quit
	 * @return true/false
	 * 
	 */
	public static boolean quitDriver(WebDriver driver) {
		boolean success = false;
		final String KILL = "taskkill /F /IM " ;
		String processName = null;
		String currentDriverName = null;
		Class<ChromeDriver> chromeDriverClass = org.openqa.selenium.chrome.ChromeDriver.class;

	
		if (driver != null) {
		 if (chromeDriverClass.isInstance(driver)) {
				processName = EnvHelper.getValue(EnvConstants.chromeDriver);
				currentDriverName = EnvConstants.chromeDriver;
			} 
			if (processName!=null) {
				try {
					driver.quit();
					drivers.remove(currentDriverName);
					Runtime.getRuntime().exec(KILL + processName);
					success = true;
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				driver.quit();
				success = true;
			}
		}
		return success;
	}
	
	/**
	 * This methods takes the screenshot of the provided driver and saves to a File.
	 * @param driver - current driver under which the screenshot to be taken
	 * @return - Screenshot File to be saved
	 */
	public static File getScreenshot(WebDriver driver) {
		
		File screenShotFile = null;
		if (driver!= null) {
			screenShotFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		}
		return screenShotFile;
	}
	
	/**
	 * This methods takes the screenshot of the current driver and saves to a File.
	 * 
	 * @return - Screenshot File to be saved
	 */
	public static File getScreenshot() {
		File screenShotFile =  null;
			if (getDriver() != null) {
				screenShotFile = ((TakesScreenshot)getDriver()).getScreenshotAs(OutputType.FILE);
			}
			return screenShotFile;
		}
	
}
