package stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import io.qameta.allure.Allure;
import scripts.Cucumberseleniumgluescripts;
import utility.allurereportmanager;
import utility.EnvHelper;
import utility.FileUtility;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashSet;
import java.util.Set;

import javax.imageio.ImageIO;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import constants.EnvConstants;
import coreutility.SuperHelper;
import factory.DriverFactory;

public class hooks extends Cucumberseleniumgluescripts {
	private static WebDriver driver;
	private static boolean allStepsPassed = true;
	private static String teststatus = "Pass";
	private static ThreadLocal<Scenario> currentScenario = new ThreadLocal<>();
	private static ThreadLocal<Throwable> currentException = new ThreadLocal<>();

	private static Set<String> currentScenarioTags;
	private static int scenarioCount = 0;
	private static int completedScenarios = 0;

	public hooks() {
//         Initialize WebDriver (e.g., through dependency injection or a Singleton pattern)
		driver = DriverFactory.getDriver(); // Example, replace with actual initialization
	}

	@Before
	public void before(Scenario scenario) throws Exception {
		try {
			// String testKey;
//	      scenarioCount++;
			System.out.println("*****************Before Scenario *****************");
			TEST_CASE_ID = scenario.getName();

			System.out.println("Scenario: " + TEST_CASE_ID);
			currentScenarioTags = new HashSet<>(scenario.getSourceTagNames());
			System.out.println("Tags: " + currentScenarioTags);
			// Retrieve and print the test key from tags if it exists
			for (String tag : currentScenarioTags) {
				if (tag.startsWith("@TestCaseKey=")) {
					jiratestkey = tag.substring(13); // Extract the key after '@key='
					System.out.println("Test Key: " + jiratestkey.trim());
				}
			}

			hooks.setScenario(scenario);
			 Allure.parameter("Name", "Usha");
			  EnvConstants.screenshotPath=FileUtility.createResultFolder(TEST_CASE_ID);
			loadData();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@After
	public void afterScenario() throws Exception {

		System.out.println("*****************After Scenario*****************");
		SuperHelper.seCloseBrowser();
//		createPdfFromFolder(TEST_CASE_ID + ".pdf");
//		ZephyrScaleAPI.updateTestResult(jiratestkey, allurereportmanager.getTestStatus()); // 1 for Pass in Zephyr
		// Increment completed scenarios
//        completedScenarios++;
//        System.out.println("After scenario: " + scenario.getName());
//
//        // Check if all scenarios have completed
//        if (completedScenarios == scenarioCount) {
//            try {
//                afterAllScenarios();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

	}

	@AfterStep
	public void afterStep() {
		Scenario scenario = getScenario();
		Throwable exception = getException();
	
		if (exception != null) {
			try {
				System.out.println("*****************After Step*****************");
				// Capture screenshot
				driver = SuperHelper.getWebDriver();
				byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

				// Attach the screenshot to the scenario and Allure report
				scenario.attach(screenshot, "image/png", "Screenshot");
				Allure.addAttachment("Screenshot", new ByteArrayInputStream(screenshot));

//				// Attach exception details
//				StringWriter sw = new StringWriter();
//				PrintWriter pw = new PrintWriter(sw);
//				exception.printStackTrace(pw);
//				String exceptionAsString = sw.toString();
//				scenario.attach(exceptionAsString.getBytes(), "text/plain", "Exception");
//                Allure.addAttachment("Exception", new ByteArrayInputStream(exceptionAsString.getBytes()));

				allStepsPassed = false;
				teststatus = "Fail";
				System.out.println("Hooks..............Scenario failed: " + scenario.getName());
//				throw new RuntimeException(exception); // This will exit the scenario
				
				  scenario.log("Step failed: " + exception.getMessage());
//				  throw new RuntimeException("Step failed: " + exception.getMessage());

			} catch (Exception e) {
//				e.printStackTrace();
				Allure.addAttachment("**********Exception in afterStep", e.toString());
			} finally {
				clear();
			}
		} else {
			teststatus = "Pass";
		}
	}

	public static void attachExceptionToScenario(Scenario scenario, Exception e, String screenshotname) {
		System.out.println("Scenario failed: " + e.toString());
//        StringWriter sw = new StringWriter();
//        PrintWriter pw = new PrintWriter(sw);
//        e.printStackTrace(pw);
//        String exceptionAsString = sw.toString();
//        scenario.attach(exceptionAsString.getBytes(), "text/plain", "Exception");
		allurereportmanager.captureAndAttachScreenshot("screenshotname");
	}

	public static boolean isAllStepsPassed() {
		return allStepsPassed;
	}

	public static String getTestStatus() {
		return teststatus;
	}

	public static void setScenario(Scenario scenario) {
		currentScenario.set(scenario);
	}

	public static Scenario getScenario() {
		return currentScenario.get();
	}

	public static void setException(Throwable exception) {
		System.out.println("setException             " + exception);
		currentException.set(exception);
	}

	public static Throwable getException() {
//        	System.out.println("getException             " + currentException);
		return currentException.get();
	}

	public static void clear() {
		currentScenario.remove();
		currentException.remove();
	}

	public static void captureAndAttachScreenshotToAllure(String attachmentName) {
		try {
			// Capture screenshot as byte array

			if (driver == null) {
				driver = getWebDriver();
			}

			byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
			// Convert byte array to BufferedImage
	        BufferedImage originalImage = ImageIO.read(new ByteArrayInputStream(screenshot));
	     // Resize the screenshot (adjust width and height as needed)
	        int targetWidth = 800; // Desired width
	        int targetHeight = 600; // Desired height
	        
	        Image resizedImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_SMOOTH);
	        BufferedImage resizedBufferedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);

	        Graphics2D g2d = resizedBufferedImage.createGraphics();
	        g2d.drawImage(resizedImage, 0, 0, null);
	        g2d.dispose();

	        // Convert BufferedImage back to byte array
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        ImageIO.write(resizedBufferedImage, "png", baos);
	        byte[] resizedScreenshot = baos.toByteArray();
	        
	        // Attach screenshot to Allure report
			Allure.addAttachment(attachmentName, new ByteArrayInputStream(resizedScreenshot));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void handleexceptioncucumbersteps(Exception e,String attachmentName) throws Exception {
		setException(e);
		captureAndAttachScreenshotToAllure(attachmentName);
		Assert.fail("Step Failed - " + e); 
		throw e;
	}

}
