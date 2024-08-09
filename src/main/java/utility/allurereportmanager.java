package utility;



import io.qameta.allure.Allure;


import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.ByteArrayInputStream;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import coreutility.SuperHelper;
import factory.DriverFactory;

public class allurereportmanager {
    private static WebDriver driver;


    public allurereportmanager() {
//         Initialize WebDriver (e.g., through dependency injection or a Singleton pattern)
         driver = DriverFactory.getDriver(); // Example, replace with actual initialization
    }
    
  
    
    public static void captureAndAttachScreenshot(String attachmentName) {
        try {
            // Capture screenshot as byte array
        	driver = DriverFactory.getDriver();
            byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

            // Attach screenshot to Allure report
            Allure.addAttachment(attachmentName, new ByteArrayInputStream(screenshot));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
   

}
