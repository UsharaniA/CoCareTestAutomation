package coreutility;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
//import org.jsoup.Jsoup;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.itextpdf.io.image.ImageDataFactory;
//import com.itextpdf.html2pdf.HtmlConverter;
//import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import constants.EnvConstants;
//import constants.KeyConstants;
import factory.DriverFactory;
import io.qameta.allure.Allure;
import utility.BrowserUtility;
import utility.DateTimeUtils;
//import utility.DateTimeUtils;
import utility.EnvHelper;
import utility.ExcelDataReader;
//import utility.ExtentReportsUtility;
//import utility.ExtentReportsUtility;



/**
 * This class provides the methods for Selenium and Mainframe methods using Selenium.
 * <PRE>
 * Methods starting with "se" are for standard Selenium only. 
 * Methods starting with "seMF" are specifically for Selenium Mainframe.
 * </PRE>
 * @author usharani A .
 *
 */
public class SuperHelper extends ExcelDataReader {
	
	public static String reportsPathFolder = "";
//	public class SuperHelper extends Utility {
	

	public static WebDriver driver;
	 
	/**
	 * This method returns the webdriver.
	 * 
	 * @return WebDriver
	 */
	public static WebDriver getWebDriver() {
		return DriverFactory.getDriver();
	}

	/**
	 * This method closes the current Browser.
	 */
	public static void seCloseBrowser() {

		BrowserUtility.closeCurrentWindow();
	}

	/**
	 * Method to close and quit specified driver instance
	 * 
	 * @param driver
	 *            Provide webdriver instance
	 * 
	 */
	public static void seCloseBrowser(WebDriver driver) {
		BrowserUtility.closeCurrentWindow(driver);
	}

	/**
	 * <p>
	 * Open a specified web browser to a target URL, including validation.
	 * </p>
	 * <p>
	 * <b>NOTE:</b> The specified browser must be registered in the RFT
	 * configuration component.
	 * </p>
	 * 
	 * @param browserName
	 * 
	 *            <PRE>
	 *  String representing the type of browser to open<BR> 
	 *            <B>Chrome</B> as BrowserConstants.Chrome <BR>
	 *            <B>Internet Explorer</B> as BrowserConstants.InternetExplorer<BR>
	 *            <B>HeadLess</B> as BrowserConstants.Headless <BR>
	 *            <B>Internet Explorer with Compatibility</B> as BrowserConstants.InternetExplorer_Compatible
	 *            </PRE>
	 * 
	 * @param url
	 *            String representing the URL to be opened
	 * @return boolean
	 */
	public static boolean seOpenBrowser(String browserName, String url) {
		return seOpenBrowser(browserName, url, null);
	}

	/**
	 * <p>
	 * Open a specified web browser to a target URL, including validation.
	 * </p>
	 * <p>
	 * <b>NOTE:</b> The specified browser must be registered in the RFT
	 * configuration component.
	 * </p>
	 * 
	 * @param browserName
	 * 
	 *            <PRE>
	 *  String representing the type of browser to open<BR> 
	 *            <B>Chrome</B> as BrowserConstants.Chrome <BR>
	 *            <B>Internet Explorer</B> as BrowserConstants.InternetExplorer<BR>
	 *            <B>HeadLess</B> as BrowserConstants.Headless <BR>
	 *            <B>Internet Explorer with Compatibility</B> as BrowserConstants.InternetExplorer_Compatible
	 *            </PRE>
	 * 
	 * @param url
	 *            String representing the URL to be opened
	 * @param downloadFilePath
	 *            path to download File
	 * @return boolean
	 */
	public static boolean seOpenBrowser(String browserName, String url, String downloadFilePath) {
		boolean success = false;
	//	logger.debug("Testing log in seopenBrowser");
		try {
			if (downloadFilePath != null && !downloadFilePath.trim().equals("")) {
				driver = DriverFactory.getDriver(browserName, downloadFilePath);
			} else {
				driver = DriverFactory.getDriver(browserName);
			}
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			driver.get(url);
			driver.manage().window().maximize();
		
	
//			ExtentReportsUtility.log(EnvConstants.PASS, "Launch Base URL in '" + browserName + "' browser",
//					"Expected URL '" + url + "' launched in '" + browserName + "' browser.");
			success = true;

		} catch (Exception e) {
			success = false;
			e.printStackTrace();
		}

		return success;
	}
	
	/**
	 * <p>
	 * Verify Web Attributes from web page
	 * 
	 * @param testObject
	 *            required test object need to test
	 * @param attribute
	 *            required attribute type need to validate.
	 * @param expectedValue
	 *            required Expected data need to validate
	 * @param step
	 * @return  true/false
	 */
	
	public static boolean seVerifyElementAttribute(WebElement testObject, String attribute, String expectedValue,
			String step) {
		return seVerifyElementAttribute(testObject, attribute, expectedValue, step,false);
	}

	/**
	 * <p>
	 * Verify Web Attributes from web page
	 * 
	 * @param testObject
	 *            required test object need to test
	 * @param attribute
	 *            required attribute type need to validate.
	 * @param expectedValue
	 *            required Expected data need to validate
	 * @param step step Name 
	 * @param screenshot true/false 
	 * @return true/false
	 *            
//	 */
	public static boolean seVerifyElementAttribute(WebElement testObject, String attribute, String expectedValue,
			String step, boolean screenshot) {
		String attributeValue = "";
		String stepDetails = "";
		int successFlag = EnvConstants.FAIL;

		try {
			if (testObject != null) {
				attributeValue = testObject.getAttribute(attribute).toString();

				if (attributeValue.equals(expectedValue)) {
					successFlag = EnvConstants.PASS;
					stepDetails = "Attribute value = \"" + attributeValue + "\" matches the Expected value = \""
							+ expectedValue + "\"";
//					if (screenshot) {
//						stepDetails = "Attribute value = \"" + attributeValue + "\" matches the Expected value = \""
//								+ expectedValue + "\""+ seCaptureScreenshot(driver, attributeValue);
//					}
				} else {
					successFlag = EnvConstants.FAIL;
//					stepDetails = "Attribute value = \"" + attributeValue + "\" do not match the Expected value = \""
//							+ expectedValue + "\"" + seCaptureScreenshot(driver, attributeValue);
				}

	

			}
		} catch (Exception e) {
			e.printStackTrace();
//			stepDetails = "Web Element not found" + seCaptureScreenshot(driver, attributeValue);
		
		}

		return getStatus(successFlag);
	}

	/**
	 * <p>
	 * Set Text in Web Text field element
	 * </p>
	 * 
	 * @param testObject
	 *            required test object need to test
	 * @param text
	 *            Enter text field name
	 * @param step
	 *            required Expected data need to validate
	 */
	public static boolean seSetText(WebElement testObject, String text) {
		return seSetText(false, testObject, text);
	}

	/**
	 * <p>
	 * Set Text in Web Text field element
	 * </p>
	 * 
	 * @param screenshot
	 *            Set true to take snap shot
	 * @param testObject
	 *            required test object need to test
	 * @param text
	 *            Enter text to be Set.
	 * @param step
	 *            required Expected data step
	 */

	public static boolean seSetText(boolean screenshot, WebElement testObject, String text) {
		int successFlag = EnvConstants.FAIL;
	
		try {

			if (testObject.isDisplayed()) {
				testObject.clear();
				testObject.sendKeys(text);
				successFlag = EnvConstants.PASS;
				

			} 

		} catch (Exception e) {
			 handleException(e);
		}
		return getStatus(successFlag);
	}

	/**
	 * <p>
	 * Set Text in UserId / Login Id Text field
	 * </p>
	 * 
	 * @param userIdField
	 *            User Id/ Login Id Text Field
	 * @param text
	 *            User Id / Login Id value
	 */
	protected static boolean seSetUserId(WebElement userIdField, String text) {
		return seSetUserId(userIdField, text, null);
	}

	/**
	 * <p>
	 * Set Text in UserId / Login Id Text field
	 * </p>
	 * 
	 * @param userIdField
	 *            User Id/ Login Id Text Field
	 * @param text
	 *            User Id / Login Id value
	 * @param step
	 *            required Expected data need to validate
	 */
	protected static boolean seSetUserId(WebElement userIdField, String text, String step) {
		int successFlag = EnvConstants.FAIL;
		
		try {
			if (userIdField.isDisplayed()) {
				userIdField.clear();
				userIdField.sendKeys(text);
				successFlag = EnvConstants.PASS;
				
				if  (step != null ) {
					seCaptureScreenshot(step ,"Enter Text: " + text);
				}
		
			} 
		} catch (Exception e) {
			 handleException(e); // Call the exception handling method
		}
		return getStatus(successFlag);
	}

	/**
	 * <p>
	 * Set Password in Password Text field
	 * </p>
	 * 
	 * @param pwdField
	 *            Password Field
	 * @param text
	 *            Enter text field name
	 */
	public static boolean seSetPassword(WebElement pwdField, String text) {
		return seSetPassword(pwdField, text, null);
	}

	/**
	 * <p>
	 * Set Password in Password Text field
	 * </p>
	 * 
	 * @param pwdField
	 *            Password Field
	 * @param text
	 *            Enter text field name
	 * @param step
	 *            required Expected data need to validate
	 */
	public static boolean seSetPassword(WebElement pwdField, String text, String step) {
		int successFlag = EnvConstants.FAIL;
		if (step == null || (step != null && step.trim().equals(""))) {
			step = "Enter Password.";
		}

		try {
			if (pwdField.isDisplayed()) {
				pwdField.clear();
				pwdField.sendKeys(text);
				successFlag = EnvConstants.PASS;

			}
		} catch (Exception e) {
			 handleException(e); // Call the exception handling method
		}
		return getStatus(successFlag);
	}

	/**
	 * <p>
	 * Click Web Element
	 * </p>
	 * 
	 * @param b
	 *            Enter Test Object of Web element property
	 * @param submit
	 *            Enter Button name (e.g LoginButton or Submit Button)
	 */
	public static boolean seClick(WebElement testObject, String buttonName) {
		return seClick(false, testObject, buttonName, null);
	}

	/**
	 * <p>
	 * Click Web Element
	 * </p>
	 * 
	 * @param screenshot
	 *            Enter True or false to capture snap shot before clicking web
	 *            element
	 * @param testObject
	 *            Enter Test Object of Web element property
	 * @param buttonName
	 *            Enter Button name (e.g LoginButton or Submit Button)
	 */
	public static boolean seClick(boolean screenshot, WebElement testObject, String buttonName) {
		return seClick(screenshot, testObject, buttonName, null);
	}

	/**
	 * <p>
	 * Click web element
	 * </p>
	 * 
	 * @param screenshot
	 *            Enter True or false to capture snap shot before clicking web
	 *            element
	 * @param testObject
	 *            required test object need to test
	 * @param buttonName
	 *            name Enter buttonName name to click.
	 * @param step
	 *            Enter steps to perform
	 */
	public static boolean seClick(boolean screenshot, WebElement testObject, String buttonName, String step) {
		int successFlag = EnvConstants.FAIL;

		if (step == null) {
			step = "Click '" + buttonName + "' ";
		}
		try {
			if (testObject.isDisplayed()) {
				testObject.click();
				successFlag = EnvConstants.PASS;
//				ExtentReportsUtility.log(successFlag, step,
//						"Expected button \"" + buttonName + "\" clicked successfully");

			} else {
				 System.out.println("----------Unable to click the button---------");

//				ExtentReportsUtility.log(successFlag, step, "Unable to click the button \"" + buttonName
//						+ "\" successfully" + seCaptureScreenshot(driver, buttonName));
			}

			if (screenshot) {
				seCaptureScreenshot(buttonName);
//				ExtentReportsUtility.log(successFlag, step, seCaptureScreenshot(driver, buttonName));
			}
		} catch (Exception e) {
			 handleException(e); // Call the exception handling method
		}
		return getStatus(successFlag);
		
	}
	
	public static void handleException(Exception e) {
	    if (e instanceof NoSuchElementException) {
	        System.out.println("-------------------");
	        System.out.println("NoSuchElementException caught: " + e.getMessage());
	        System.out.println("-------------------");
	        Allure.addAttachment("Exception", "NoSuchElementException: " + e.getMessage());
	    } else {
	        System.out.println("-------------------");
	        System.out.println("General exception caught: " + e.getMessage());
	        System.out.println("-------------------");
	        Allure.addAttachment("Exception", "General Exception: " + e.getMessage());
	    }
	    throw new RuntimeException("Failed to find the element " );
	}
	
	public static  void sefindelementClick(String datatofind) {
		//System.out.println(datatofind);
		 WebElement testObject = driver.findElement(By.xpath(datatofind));
		 
		 if (testObject.isDisplayed()) {
			    seHighlightElement(testObject);
				testObject.click();
		 }
		
	}
	
	
	public static boolean seClicktabledata(WebElement testObject,String datatofind) {	
		int successFlag = EnvConstants.FAIL;
	    // Locate all rows in the table
	    List<WebElement> rows = testObject.findElements(By.tagName("tr"));
	    seHighlightElement(testObject);

	    // Iterate over the rows
	    for (WebElement row : rows) {
	        // Locate all cells in each row
	        List<WebElement> cells = row.findElements(By.tagName("td"));
	        System.out.print(cells + "\t");
	        // Iterate over the cells
	        for (WebElement cell : cells) {
	            // Get the text from each cell
	            String cellText = cell.getText();
	            
	           
	            if (cellText.equals(datatofind)) {
	            	cell.click();
	            	successFlag = EnvConstants.PASS;
	            	System.out.print(cellText + "\t"); // Print tab-separated
	            	break;
	            }
	            }
	        }
	        System.out.println(); // Move to the next line for the next row
	    
	        return getStatus(successFlag);
		}
	
	

//	    public static void resizeScreenshot(File inputFile, File outputFile, int width, int height) throws Exception {
//	        BufferedImage originalImage = ImageIO.read(inputFile);
//	        java.awt.Image resizedImage = originalImage.getScaledInstance(width, height, Image.SCALE_SMOOTH);
//	        BufferedImage resizedBufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
//
//	        Graphics2D g2d = resizedBufferedImage.createGraphics();
//	        g2d.drawImage(resizedImage, 0, 0, null);
//	        g2d.dispose();
//
//	        ImageIO.write(resizedBufferedImage, "png", outputFile);
//	    }
	

	/**
	 * <p>
	 * Method to Capture screen Shot
	 * </p>
	 * 
	 * @param driver
	 *            Enter driver instance
	 * @param screenName
	 *            Enter image name
	 * @return String screenshot File Path
	 */
	public static String seCaptureScreenshot( String screenName,String Description) {
		String targetScreenshotFilePath = "";
		File screenshotSourceFile;
		try {
			// Capture screen shot
			if (driver == null) {
				driver = getWebDriver();
			}
			screenshotSourceFile = DriverFactory.getScreenshot(driver);
//System.out.println("testreportpath" + screenshotPath);
			File dir = new File(reportsPathFolder + screenshotPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			targetScreenshotFilePath = screenshotPath
					+ "\\" + screenName + ".png";
			
//			System.out.println("targetScreenshotFilePath  :" + targetScreenshotFilePath);
			// Load the captured screenshot into a BufferedImage
	        BufferedImage image = ImageIO.read(screenshotSourceFile);
	     // Get current width and height
//            int currentWidth = image.getWidth();
//            int currentHeight = image.getHeight();
//            System.out.println("Current Width: " + currentWidth);
//            System.out.println("Current Height: " + currentHeight);
	        int targetWidth = 800; // Desired width
	        int targetHeight = 383; // Desired height
	        java.awt.Image resizedImage = image.getScaledInstance(targetWidth, targetHeight,java.awt.Image.SCALE_SMOOTH);
	        BufferedImage resizedBufferedImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_ARGB);
	        Graphics2D g2d = resizedBufferedImage.createGraphics();
	        g2d.drawImage(resizedImage, 0, 0, null);
	        g2d.dispose();
	        
//	        if (!Description.equals("")) {
//	        // Annotate the screenshot
//	        annotateScreenshot(resizedBufferedImage, Description, 100, 100); // Customize the annotation text and position
//	     
//          }// Save the annotated image to the target path
//	        ImageIO.write(resizedBufferedImage, "png", new File(targetScreenshotFilePath));
	        
	     // Convert BufferedImage back to byte array
	        ByteArrayOutputStream baos = new ByteArrayOutputStream();
	        ImageIO.write(resizedBufferedImage, "png", baos);
	        byte[] resizedScreenshot = baos.toByteArray();

	        Allure.addAttachment(screenName, new ByteArrayInputStream(resizedScreenshot));
	        			File destination = new File(targetScreenshotFilePath);
			FileUtils.copyFile(screenshotSourceFile, destination);

		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}

		return targetScreenshotFilePath;
	}
	
	
	public static String seCaptureScreenshot( String screenName) {
		String targetScreenshotFilePath = "";
		File screenshotSourceFile;
		try {
			// Capture screen shot
			if (driver == null) {
				driver = getWebDriver();
			}
			screenshotSourceFile = DriverFactory.getScreenshot(driver);
//System.out.println("testreportpath" + screenshotPath);
			File dir = new File(reportsPathFolder + screenshotPath);
			if (!dir.exists()) {
				dir.mkdirs();
			}
			targetScreenshotFilePath = screenshotPath
					+ "\\" + screenName + ".png";
	
			
			File destination = new File(targetScreenshotFilePath);
			FileUtils.copyFile(screenshotSourceFile, destination);

		} catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
		}

		return targetScreenshotFilePath;
	}
	
	public static void annotateScreenshot(BufferedImage image, String text, int x, int y) {
	   
		System.out.println("**************Anotate***************");
		System.out.println("Image dimensions: " + image.getWidth() + "x" + image.getHeight());
		System.out.println("Text position: (" + 1 + ", " + y + ")");
		 Graphics2D g2d = image.createGraphics();

	        // Set font for annotation
	        g2d.setFont(new Font("Arial", Font.BOLD, 36)); // Increase font size for visibility

	        // Get font metrics to calculate the text size
	        int textWidth = g2d.getFontMetrics().stringWidth(text);
	        int textHeight = g2d.getFontMetrics().getHeight();

	        // Draw a solid background rectangle for the text
	        g2d.setColor(Color.WHITE); // Opaque white background
	        g2d.fillRect(x - 5, y - textHeight, textWidth + 10, textHeight + 10); // Adjust the rectangle size

	        // Draw the annotation text on the image
	        g2d.setColor(Color.RED); // Text color
	        g2d.drawString(text, x, y); // Position for text

	        g2d.dispose(); // Dispose of the graphics context
		}
	
	public static void createPdfFromFolder(String pdfFileName) throws Exception {
	    Path pdfPath = screenshotPath.resolve(pdfFileName);
	    File screenshotPathfolder = screenshotPath.toFile();

	    // Create the PDF path within the specified folder
	    File[] listOfFiles = screenshotPathfolder.listFiles((dir, name) -> name.toLowerCase().endsWith(".png"));

	    if (listOfFiles == null || listOfFiles.length == 0) {
	        throw new IOException("No PNG files found in the specified folder.");
	    }

	    // Sort files by last modified time
	    Arrays.sort(listOfFiles, Comparator.comparingLong(File::lastModified));

	    // Create PDF document
	    PdfWriter writer = new PdfWriter(new FileOutputStream(pdfPath.toFile()));
	    PdfDocument pdfDocument = new PdfDocument(writer);
	    Document document = new Document(pdfDocument, PageSize.A4);

	    for (File file : listOfFiles) {
	        String fileName = file.getName();

	        // Add filename as a comment
	        Paragraph comment = new Paragraph(fileName);
	        document.add(comment);

	        // Load image
	        Image img = new Image(ImageDataFactory.create(file.getAbsolutePath()));
//	        img.scaleToFit(PageSize.A4.getWidth(), PageSize.A4.getHeight());
	       
	        // Scale the image while maintaining aspect ratio
            img.scaleToFit(PageSize.A4.getWidth() - document.getLeftMargin() - document.getRightMargin(),
                            PageSize.A4.getHeight() - document.getTopMargin() - document.getBottomMargin());
            
            // Center the image
            img.setHorizontalAlignment(com.itextpdf.layout.property.HorizontalAlignment.CENTER);
            
	        
	        document.add(img);

	        // Add space between images
	        document.add(new Paragraph("\n")); // Adjust as needed
	    }

	    document.close();
	    pdfDocument.close();
	    writer.close();
	}
	
	


	    public static void  AllureReportToPdfConverter() {
	        String allureReportPath = "D:/Eclipse/cocare/build/cucumber-reports.html"; // Path to the Allure report HTML
	        String pdfFilePath = "output.pdf"; // Output PDF file path

//	        String inputHtmlPath = "D:/Eclipse/cocare/build/cucumber-reports.html";
//	        String outputPdfPath = "output.pdf";
//
//	        try {
//	            // Read the HTML file into a Jsoup Document
////	        	org.jsoup.nodes.Document htmlDocument = Jsoup.parse(new File(inputHtmlPath), "UTF-8");
////	        	
////	        	// Get the HTML content as a string
////	            String htmlContent = htmlDocument.html();
//
//
//	            // Optionally modify the HTML content if needed
//	            // For example, adding custom styles or removing elements
//	            // htmlDocument.body().append("<style>...</style>");
//
//	            // Convert the HTML to PDF using iText
//	        	 HtmlConverter.convertToPdf(new FileInputStream(inputHtmlPath), new FileOutputStream(outputPdfPath));
//	         //   HtmlConverter.convertToPdf(htmlContent, new FileOutputStream(outputPdfPath));
//
//	            System.out.println("PDF generated successfully!");
//	        } catch (IOException e) {
//	            e.printStackTrace();
//	        }
	    }
	    
	 
	public static String getConsolidatedReportsPath(){
		
		return EnvHelper.getValue(EnvConstants.reportsPath)+DateTimeUtils.getTimeStamp()+"_CONSOLIDATED";
	}
	
	
	
	public static String getReportsPath(){
		
		return EnvHelper.getValue(EnvConstants.reportsPath);
	}
	
	/**
	 * Method to take snapshot with WebElement area present.
	 * 
	 * @param element
	 *            Enter Web element to capture snapshot
	 * @param screenName
	 *            Enter image name
	 * @return String screenshot File Path
	 */
//	public static String seCaptureScreenshotWithWebElement(WebElement element, String screenName) {
//		String targetScreenshotFilePath = "";
//		File screenshotSourceFile;
//		try {
//			// Capture screen shot
//			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
//			seHighlightElement(element);
//			screenshotSourceFile = DriverFactory.getScreenshot(driver);
//
//			File dir = new File(EnvHelper.getValue(EnvConstants.screenshotsPath));
//			if (!dir.exists()) {
//				dir.mkdirs();
//			}
//			targetScreenshotFilePath = reportsPathFolder + EnvHelper.getValue(EnvConstants.screenshotsPath)
//					+ DateTimeUtils.getTimeStampMiliSec() + "_" + screenName + ".png";
//			File destination = new File(targetScreenshotFilePath);
//			FileUtils.copyFile(screenshotSourceFile, destination);
//			targetScreenshotFilePath = ExtentReportsUtility.logScreenshot(targetScreenshotFilePath);
//			targetScreenshotFilePath = targetScreenshotFilePath.replaceAll(reportsPathFolder, "");
//		} catch (Exception e) {
//			return e.getMessage();
//		}
//
//		return targetScreenshotFilePath;
//	}

	/**
	 * <p>
	 * Verify Text from web page in Text field / Text Box / Drop down field Text
	 * Comparison automatically trims trail and leading spaces.
	 * </p>
	 * 
	 * @param testObject
	 *            required test object need to test
	 * @param expectedValue
	 *            required Expected data to validate
	 * @param fieldName
	 *            Enter field Name to be validate
	 */
	public static boolean seVerifyFieldValue(WebElement testObject, String expectedValue, String fieldName) {
		return seVerifyFieldValue(testObject, expectedValue, fieldName, false);
	}

	/**
	 * <p>
	 * Verify Text from web page in Text field / Text Box / Drop down field Text
	 * Comparison automatically trims trail and leading spaces. This method
	 * supports Partial Comparision of the Text.
	 * </p>
	 * 
	 * @param testObject
	 *            required test object need to test
	 * @param expectedValue
	 *            required Expected data need to validate
	 * @param fieldName
	 *            Enter field Name to be validate
	 * @param comparePartialValue
	 *            true or false
	 */
	public static boolean seVerifyFieldValue(WebElement testObject, String expectedValue, String fieldName,
			boolean comparePartialValue) {
		WebElement target;
		String textType = "";
		String attributeValue = "";
		String targetValue = "";
		boolean success = false;
		try {
			target = testObject;
			targetValue = target.getTagName();
			if (targetValue.contains("input")) {
				textType = "TextBox";
			} else if (targetValue.contains("label")) {
				textType = "Text";
			} else if (targetValue.contains("select")) {
				textType = "DropDown/ListBox";
			}
		} catch (Exception e) {

		}
		try {
			switch (textType) {

			case "TextBox":
				attributeValue = seGetElementTextBoxValue(testObject);
				break;
			case "DropDown/ListBox":
				attributeValue = seGetDropDownValue(testObject);
				break;
			default:
				attributeValue = seGetElementValue(testObject);
				break;
			}

			if (comparePartialValue) {
				success = attributeValue.trim().contains(expectedValue.trim());
			} else {
				success = attributeValue.trim().equals(expectedValue.trim());
			}

//			if (success) {
//				ExtentReportsUtility.log(EnvConstants.PASS, "VerifyFieldValue : " + fieldName,
//						textType + " value \"" + attributeValue + "\" for the field \"" + fieldName
//								+ "\" matches the expected  value \"" + expectedValue.trim() + "\"");
//			} else {
//				success = false;
//				ExtentReportsUtility.log(EnvConstants.FAIL, "VerifyFieldValue : " + fieldName,
//						textType + " value \"" + attributeValue + "\" for the field \"" + fieldName
//								+ "\" do not match the expected  value \"" + expectedValue.trim() + "\""
//								+ seCaptureScreenshot(driver, fieldName));
//			}
		} catch (Exception e) {
			System.out.println("No data found.");
			e.printStackTrace();
//			ExtentReportsUtility.log(EnvConstants.FAIL, "VerifyFieldValue : " + fieldName,
//					"Web element NOT found" + seCaptureScreenshot(driver, fieldName));
		}
		return success;
	}

	/**
	 * This method get a WebElement's value attribute
	 * 
	 * @param testObj
	 *            object to be used
	 * 
	 */
	public static String seGetElementTextBoxValue(WebElement testObj) {
		String target = null;
		try {
			target = testObj.getAttribute("value");
		} catch (org.openqa.selenium.NoSuchElementException e) {
			e.printStackTrace();
		}
		return target;
	}

	/**
	 * This method get a WebElement's value attribute
	 * 
	 * @param testObj
	 *            object name
	 */
	public static String seGetDropDownValue(WebElement testObj) {
		String value = null;
		try {
			Select target = new Select(testObj);
			value = target.getFirstSelectedOption().getText();
		} catch (org.openqa.selenium.NoSuchElementException e) {
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * This method get a WebElement's value attribute
	 * 
	 * @param testObj
	 *            object name, from repository
	 */
	public static String seGetElementValue(WebElement testObj) {
		String targetValue = "";
		try {
			targetValue = testObj.getText();
			seHighlightElement(testObj);
		} catch (org.openqa.selenium.NoSuchElementException e) {
			e.printStackTrace();
		}
		return targetValue;
	}

	/**
	 * This method to highlight
	 * 
	 * @param element
	 *            webelement to be highlighted
	 * 
	 */

	public static void seHighlightElement(WebElement element) {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);

		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			System.out.println(e.getMessage());
		}

		js.executeScript("arguments[0].setAttribute('style','border: solid 2px white')", element);

	}

	/**
	 * <p>
	 * Perform input of text to a specified field, validating results.
	 * </p>
	 * 
	 * @param testObject
	 *            required value of the TestObject to be action upon
	 * @param text
	 *            required value to be input to the field
	 * @param stepName
	 *            [optional] String representing user provided test step name
	 * @return boolean indicating the success
	 */
	public static boolean seSelectText(WebElement testObject, String text, String stepName) {
		return seSelectText(false, testObject, text, stepName);
	}

	/**
	 * <p>
	 * Perform input of text to a specified field, validating results.
	 * </p>
	 * 
	 * @param screenshot
	 *            boolean indicating whether to force a snapshot
	 * @param testObject
	 *            required value of the TestObject to be action upon
	 * @param text
	 *            required value to be input to the field
	 * @param stepName
	 *            [optional] String representing user provided test step name
	 * @return boolean indicating the success
	 */
	public static boolean seSelectText(boolean screenshot, WebElement testObject, String text, String stepName) {
		int success = EnvConstants.FAIL;
		String stepDetails = "";
		Select selectObj = null;
		if (stepName.equals("")) {
			stepName = "Select \" " + text + " \" From Drop down";
		}
		try {
			if (testObject != null) {
				success = EnvConstants.PASS;

				selectObj = new Select(testObject);
				selectObj.selectByVisibleText(text); // Select the value in Drop

			}

		} catch (StaleElementReferenceException elementHasDisappeared) {
			
			  Allure.addAttachment("Exception", "StaleElementReferenceException: " + elementHasDisappeared.getMessage());

		} catch (Exception e) {
			e.printStackTrace();
			 Allure.addAttachment("Exception", "NoSuchElementException: " + e.getMessage());
			
		}

		// Validate if the selected box is correct/requested
		if (selectObj != null && selectObj.getFirstSelectedOption().getText().equals(text)) {
			success = EnvConstants.PASS;
		} else {
			success = EnvConstants.FAIL;
		}

		return getStatus(success);
	}

	
	public static boolean  SelectFirstDropdownOption(WebElement testObject) {
		int success = EnvConstants.FAIL;
		Select selectObj = null;
		try {	
			if (testObject != null) {
		selectObj = new Select(testObject);	
	        // Select the first option by index (index starts from 0)
		
		// Get all the options in the dropdown
        List<WebElement> options = selectObj.getOptions();

        // Iterate over each option and print its text
        for (WebElement option : options) {
            System.out.println(option.getText());}
		selectObj.selectByIndex(1);
		success = EnvConstants.PASS;}
		} catch (Exception e) {
			e.printStackTrace();
			 Allure.addAttachment("Exception", "NoSuchElementException: " + e.getMessage());
			
		}
		return getStatus(success);

	   
	}
	/**
	 * This method will wait till document reaches the ready status
	 * 
	 */
	public static void seWaitForPageLoad() {
		long timeout = 5000;
		long end = System.currentTimeMillis() + timeout;
		while (System.currentTimeMillis() < end) {
			if (String.valueOf(((JavascriptExecutor) driver).executeScript("return document.readyState"))
					.equals("complete")) {
				break;
			}
		}
	}

	/**
	 * This method will wait until the Expected Condition is met for the time
	 * Provided.
	 * 
	 * <PRE>
	 * eg:- Usage of ExpectedCondition expectedCondition = ExpectedConditions.elementToBeClickable(webElement);
	 * 				ExpectedCondition expectedCondition = ExpectedConditions.elementToBeSelected((By.xpath("..."));
	 * </PRE>
	 * 
	 * eg:- seWaitForWebElement(1,expectedCondition);
	 * 
	 * @param timeOutInSeconds
	 *            Timeout in Seconds.
	 * @param expectedCondition
	 *            ExpectedConditions to be met.
	 * 
	 */
	public static void seWaitForWebElement(long timeOutInSeconds, ExpectedCondition<?> expectedCondition) {
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		wait.until(expectedCondition);
	}

	/**
	 * Compare Expected data with screen data
	 * 
	 * @param expectedValue
	 *            Enter expected data to be verify.
	 * @param step
	 *            Enter Description.
	 * @return boolean
	 * @throws InterruptedException
	 */
	public static boolean seCompareText(WebElement testObject, String expectedValue, String step)
			throws InterruptedException {
		return seCompareText(false, testObject, expectedValue, step);
	}

	/**
	 * Compare Expected data with screen data
	 * 
	 * @param screenshot
	 *            True/ False
	 * @param expectedValue
	 *            Enter expected data to be verify.
	 * @param step
	 *            Enter Description.
	 * @return boolean
	 * @throws InterruptedException
	 */
	public static boolean seCompareText(boolean screenshot, WebElement testObject, String expectedValue, String step)
			throws InterruptedException {
		seWaitForPageLoad();
		if (step.equals("")) {
			step = "Compare '" + expectedValue + "\" text";
		}
		seWaitForPageLoad();
		String fieldData = testObject.getText().trim();
		int successFlag = EnvConstants.FAIL;
		try {
			if (fieldData.equals(expectedValue)) {
				successFlag = EnvConstants.PASS;
//				if (!screenshot) {
//					ExtentReportsUtility.log(successFlag, step,
//							"Actual Data = " + fieldData + " | Expected Data = '" + expectedValue + "'");
//				} else {
//					ExtentReportsUtility.log(successFlag, step, "Actual Data = " + fieldData + " | Expected Data = '"
//							+ expectedValue + "'" + seCaptureScreenshot(driver, expectedValue));
//				}
			} else {
//				ExtentReportsUtility.log(successFlag, step, "Actual Data = " + fieldData + " | Expected Data = '"
//						+ expectedValue + "'" + seCaptureScreenshot(driver, expectedValue));
			}
//			RESULT_STATUS = getSuccessValue(successFlag);
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
//			RESULT_STATUS = getSuccessValue(successFlag);
//			ExtentReportsUtility.log(EnvConstants.FAIL,
//					"Web Element NOT found" + seCaptureScreenshot(driver, expectedValue));
		}
		return getSuccessValue(successFlag);

	}

	/**
	 * Get text field Data
	 * 
	 * @param testObject
	 *            Enter testObject to retrieve field data
	 * @return String
	 * @throws InterruptedException
	 */
	public static String seGetText(WebElement testObject) {
		seWaitForPageLoad();
		seWaitForPageLoad();
		String fieldData = testObject.getText().trim();
		return fieldData;
	}

	/**
	 * This method is used to compare two string with provided operator
	 * 
	 * @param sourceStr
	 *            Enter Source String
	 * @param targetStr
	 *            Enter Target String
	 * @param operator
	 *            Enter Operator (e.g "=","<" or ">")
	 * @param stepSummary
	 * @return boolean
	 */
	public static boolean seCompareStrings(String sourceStr, String targetStr, String operator, String stepSummary) {
		int successFlag = EnvConstants.FAIL;
		String resultOperator = null;
		String testStepResultText = "";
		if (stepSummary.equals("")) {
			stepSummary = "Comparing Strings";
		}

		if (operator.equals("=")) {
			testStepResultText = "Expected = " + sourceStr + " | Actual = " + targetStr;
		} else {
			testStepResultText = "Checking if " + sourceStr + " " + operator + " " + targetStr;
		}

		int result = sourceStr.compareTo(targetStr);
		if (result < 0) {
			resultOperator = "<";
		} else if (result == 0) {
			resultOperator = "=";
		} else if (result > 0) {
			resultOperator = ">";
		}
		if (!resultOperator.equals(operator)) {
			testStepResultText = sourceStr + " is not " + operator + " " + targetStr;
			successFlag = EnvConstants.FAIL;
//			ExtentReportsUtility.log(EnvConstants.FAIL, stepSummary, testStepResultText);
		} else {
			successFlag = EnvConstants.PASS;
			testStepResultText = sourceStr + " is " + operator + " " + targetStr;

//			ExtentReportsUtility.log(EnvConstants.PASS, stepSummary, testStepResultText);
		}
		return getSuccessValue(successFlag);
	}

	/**
	 * This method clicks the Alert window
	 * 
	 * @return boolean success
	 * 
	 */
	public static boolean seClickAlert() {
		return seClickAlert(null, null, false);
	}

	/**
	 * This method clicks the Alert window and validates the text in Alert.
	 * 
	 * @param text
	 *            - Text to Validate
	 * @param stepName
	 *            - Step Name
	 * @return boolean success
	 * 
	 */
	public static boolean seClickAlert(String text, String stepName) {
		return seClickAlert(text, stepName, false);
	}

	/**
	 * This method clicks the Alert window and validates the text in Alert.
	 * 
	 * @param text
	 *            - Text to Validate
	 * @param stepName
	 *            - Step Name
	 * @param screenshot
	 *            - true/false screenshot to take
	 * @return boolean success
	 * 
	 */
	public static boolean seClickAlert(String text, String stepName, boolean screenshot) {
		int success = EnvConstants.FAIL;
		String stepDetails = null;
		if (stepName == null) {
			stepName = "Click Alert";
		}
		try {
			Alert alert = driver.switchTo().alert();
			if (text != null) {
				if (alert.getText().equalsIgnoreCase(text)) {
					success = EnvConstants.PASS;
				}
			} else {
				text = "Click Alert";
			}
			alert.accept();
			stepDetails = "Alert Clicked Successfully";

//			if (screenshot) {
//				ExtentReportsUtility.log(success, stepName, stepDetails + seCaptureScreenshot(driver, text));
//			} else {
//				ExtentReportsUtility.log(success, stepName, stepDetails);
//			}
		} catch (org.openqa.selenium.NoSuchElementException e) {
			stepDetails = "\n" + " *** Alert Click Failed*** ";
//			ExtentReportsUtility.log(success, stepName, stepDetails + seCaptureScreenshot(driver, text));
			e.printStackTrace();
		}
		return getStatus(success);
	}

	/**
	 * This method used to click a Web element using JavascriptExecutor
	 * 
	 * @param screenshot
	 * @param webelement
	 * @param stepName
	 * @return boolean
	 */
	public static boolean seClickUsingJavaScript(boolean screenshot, WebElement testObject,String stepname) {
		boolean objectFound = false;
		try {
//			WebElement element = driver.findElement(webelement);
			((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", testObject);
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", testObject);
//			ExtentReportsUtility.log(EnvConstants.PASS, stepName, "Clicked at the Web element: " + webelement);
			objectFound = true;
			
			if (screenshot) {
				seCaptureScreenshot(stepname);
//				ExtentReportsUtility.log(successFlag, step, seCaptureScreenshot(driver, buttonName));
			}
		} catch (NoSuchElementException nse) {
			nse.printStackTrace();
//			ExtentReportsUtility.log(EnvConstants.FAIL, stepName, "Unable to click Web element");
			objectFound = false;
		} catch (Exception e) {
			e.printStackTrace();
			objectFound = false;
//			ExtentReportsUtility.log(EnvConstants.FAIL,
//					"Web Element NOT found" + seCaptureScreenshot(driver, stepName));
		}
		return objectFound;
	}

	/**
	 * create and return a Two dimensional array of web table
	 * 
	 * @param actTable
	 * @return String[][]
	 */
	@SuppressWarnings({ "unused" })
	private String[][] seGetTableCellValues(By actTable) {

		try {

			WebElement table = driver.findElement(actTable);
			List<WebElement> tr = table.findElements(By.tagName("tr"));
			List<WebElement> th;
			th = tr.get(0).findElements(By.xpath("./child::*"));
			String[][] tabValues = new String[tr.size()][th.size()];
			for (int r = 0; r < tr.size(); r++) {
				List<WebElement> cell;
				cell = tr.get(r).findElements(By.xpath("./child::*"));
				for (int c = 0; c < cell.size(); c++) {
					String val = "";
					try {
						val = cell.get(c).getText();
					} catch (Exception e) {
						e.printStackTrace();
					}
					tabValues[r][c] = val;
				}
			}
			return tabValues;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * <p>
	 * Perform input of keystrokes to a specified field, validating results.
	 * 
	 * @see KeyConstants eg:- KeyConstants.ENTER
	 *      </p>
	 * 
	 * @param testObject
	 *            required WebElement TestObject to be action upon
	 * @param keys
	 *            the value to be input to the field
	 * @return boolean indicating the success
	 */
//	public static boolean seInputKeys(WebElement testObject, String keys) {
//		return seInputKeys(false, testObject, keys, null);
//	}

	/**
	 * <p>
	 * Perform input of keystrokes to a specified field, validating results.
	 * 
	 * @see KeyConstants eg:- KeyConstants.ENTER
	 *      </p>
	 * 
	 * @param testObject
	 *            required WebElement TestObject to be action upon
	 * @param keys
	 *            the value to be input to the field
	 * @param stepName
	 *            [optional] String representing user provided test step name
	 * @return boolean indicating the success
	 */
//	public static boolean seInputKeys(WebElement testObject, String keys, String stepName) {
//		return seInputKeys(false, testObject, keys, stepName);
//
//	}

	/**
	 * <p>
	 * Perform input of keystrokes to a specified field, validating results.
	 * 
	 * @see KeyConstants eg:- KeyConstants.ENTER
	 *      </p>
	 * 
	 * @param screenshot
	 *            boolean indicating whether to force a snapshot
	 * @param testObject
	 *            required WebElement TestObject to be action upon
	 * @param inputKeys
	 *            the value to be input to the field
	 * @param stepName
	 *            [optional] String representing user provided test step name
	 * @return boolean indicating the success
	 */
//	public static boolean seInputKeys(boolean screenshot, WebElement testObject, String inputKeys, String stepName) {
//		int success = EnvConstants.FAIL;
//
//		if (stepName.equals(null)) {
//			stepName = "Enter INPUT KEYS '" + inputKeys + "'";
//		}
//
//		if (isElementPresent(testObject)) {
//
//			// This works in JDK 1.8 and above since using LAMBDA Expressions
//			if (Arrays.stream(Keys.values()).anyMatch((t) -> t.name().equals(inputKeys))) {
//				testObject.sendKeys(Keys.valueOf(inputKeys));
//			} else {
//				testObject.sendKeys(inputKeys);
//			}
//			success = EnvConstants.PASS;
//			seWaitForPageLoad();
//			if (screenshot) {
////				ExtentReportsUtility.log(success, stepName, "Expected Key '" + inputKeys + "pressed successfully."
////						+ seCaptureScreenshot(driver, inputKeys));
//			} else {
////				ExtentReportsUtility.log(success, stepName, "Expected Key '" + inputKeys + "pressed successfully.");
//			}
//		} else {
////			ExtentReportsUtility.log(success, stepName, "Expected Key '" + inputKeys + " NOT pressed successfully"
////					+ seCaptureScreenshot(driver, inputKeys));
//		}
//		return getStatus(success);
//	}
	
	/**
	 * This method is to switch to New window
	 * 
	 */
	public void seSwitchToNewWindow() {
		try {
			String parentHandler = driver.getWindowHandle();
			for (String winHandle : driver.getWindowHandles()) {
				if (!winHandle.equals(parentHandler)) {
					driver.switchTo().window(winHandle);
				}
			}
		} catch (org.openqa.selenium.NoSuchElementException e) {
			e.printStackTrace();

		}
	}
	
	/**
	 * This methods checks the presence of WebElement.
	 * 
	 * @param testObject
	 * @return boolean
	 */
	public static boolean isElementPresent(WebElement testObject) {
		String screenName = "ElementPresence";
		try {
			String sClass = testObject.toString();
			if (!sClass.equals("[]")) {
				return true;
			} else {
				return false;
			}
		} catch (org.openqa.selenium.NoSuchElementException e) {
			return false;
		} catch (Exception e) {
//			ExtentReportsUtility.log(EnvConstants.FAIL,
//					"Web Element NOT found" + seCaptureScreenshot(driver, screenName));
			return false;
		}
	}
	
	
	
	// Selenium Methods End Here
	

	/**
	 * This method returns the boolean based on the Logger Success.
	 * 
	 * @param successValue
	 * @return boolean
	 */
	public static boolean getSuccessValue(int successValue) {

		if (successValue == EnvConstants.PASS)
			return true;
		else
			return false;
	}

	/**
	 * This method returns boolean value.
	 * 
	 * @param statusValue
	 * @return boolean
	 */
	private static boolean getStatus(int statusValue) {

		if (statusValue == EnvConstants.FAIL || statusValue == EnvConstants.FATAL
				|| statusValue == EnvConstants.ERROR) {
			return false;
		} else {
			return true;
		}

	}
//
//	/**
//	 * It returns Variable name of object
//	 * 
//	 * @param testObject
//	 *            Enter test object need to perform action
//	 * @param testClass
//	 *            Enter class name where object is belong
//	 * @return Returns variable name of object
//	 */
//	/*
//	 * protected static String getDeclaredVariableName(WebElement testObject,
//	 * Object testClass) { String varName = ""; java.lang.reflect.Field[] fields
//	 * = testClass.getClass() .getDeclaredFields(); for (int i = 0; i <
//	 * fields.length; i++) { try { if (fields[i] != null &&
//	 * fields[i].get(testClass) != null) { if
//	 * (fields[i].get(testClass).toString()
//	 * .equalsIgnoreCase(testObject.toString())) { varName =
//	 * fields[i].getName(); break; } } } catch (java.lang.IllegalAccessException
//	 * e) { e.printStackTrace(); } } return varName; }
//	 */
//
//	/**
//	 * Return a value from two dimensional array
//	 * 
//	 * @param table1
//	 * @param string
//	 * @param colIndex
//	 * @return
//	 */
//	/*
//	 * @SuppressWarnings("unused") private String seGetStringInArray(String[][]
//	 * table1, String string, int colIndex) { String reqval=""; try { for(int
//	 * i=0;i<table1.length;i++){ for(int j=0;j<table1[0].length;j++){
//	 * if(table1[i][j].toString().contains(string)){ reqval=
//	 * table1[i][colIndex].toString(); return reqval; } } } } catch (Exception
//	 * e) { e.printStackTrace(); return null; } return reqval; }
//	 */
	
	
	
//	// Locate the table
//    WebElement table = driver.findElement(By.cssSelector("table.questions-table"));
//
//    // Find all rows within the table body
//    List<WebElement> rows = table.findElements(By.tagName("tr"));
//
//    // To store all <td> elements
//    List<WebElement> allCells = new ArrayList<>();
//
//    // Iterate over each row to find all <td> elements
//    for (WebElement row : rows) {
//        List<WebElement> cells = row.findElements(By.tagName("td"));
//        allCells.addAll(cells);
//    }
//
//    // Randomly select a <td>
//    Random rand = new Random();
//    int randomCellIndex = rand.nextInt(allCells.size());
//    WebElement randomCell = allCells.get(randomCellIndex);
//
//    // Click or perform actions on the randomly selected <td>
//    randomCell.click();
//    System.out.println("Selected TD text: " + randomCell.getText());
	
	public void seSelectQuestionaire(WebElement testObject) {
	    try {
//	        seHighlightElement(testObject);

	        // Find all tr elements under the tbody
	        List<WebElement> rows = testObject.findElements(By.xpath(".//tbody/tr"));
	        System.out.println("Number of rows found: " + rows.size());

	        // Iterate through each row
	        for (WebElement row : rows) {
	            // Find all input elements within the row
	            List<WebElement> inputs = row.findElements(By.tagName("input"));

	            if (!inputs.isEmpty()) {
	                // Filter inputs to only include those with a value between 1 and 5
	                List<WebElement> validInputs = new ArrayList<>();
	                for (WebElement input : inputs) {
	                    String value = input.getAttribute("value");

	                    if (value != null && value.matches("[0-4]")) {
	                        validInputs.add(input);
	                    }
	                }

	                // If there are valid inputs, select a random one and click it
	                if (!validInputs.isEmpty()) {
	                    Random rand = new Random();
	                    int randomIndex = rand.nextInt(validInputs.size());
	                    WebElement randomInput = validInputs.get(randomIndex);

	                    // Scroll into view and then click
	                    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", randomInput);
	                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", randomInput);
	                    System.out.println("Clicked on input with value: " + randomInput.getAttribute("value"));
	                } else {
	                    System.out.println("No valid input found in this row.");
	                }
	            } else {
	                System.out.println("No input elements found in this row.");
	            }
	        }

	    } catch (Exception e) {
	        System.out.println("Exception caught during execution: " + e.getMessage());
	        handleException(e);
	    }
	}
	
	public void seClickbuttonRamdomlyinTable(WebElement testObject) {
		// Find all <td> elements that contain radio buttons
	try {	
		

		 // Find all tr elements under tbody
	        List<WebElement> trElements = testObject.findElements(By.xpath(".//tbody/tr"));
	     
	        // Random object to pick Yes or No
	        Random random = new Random();

	        for (WebElement tr : trElements) {
	            // Find the second td element (where the radio buttons are) within each tr
	            WebElement tdElement = tr.findElement(By.xpath(".//td[2]"));
	           
	            // Find the Yes and No labels within the td
	            WebElement yesLabel = tdElement.findElement(By.xpath(".//label[@for[starts-with(.,'optionYes')]]"));
	            WebElement noLabel = tdElement.findElement(By.xpath(".//label[@for[starts-with(.,'optionNo')]]"));

	            // Click Yes or No randomly
	            if (random.nextBoolean()) {
	            	seClickUsingJavaScript(false,yesLabel,"");
	                yesLabel.click();
	            } else {	            
	               
	                seClickUsingJavaScript(false,noLabel,"");
	            }
	    }
	 } catch (Exception e) {
	        System.out.println("Exception caught during execution: " + e.getMessage());
	        handleException(e);
	    }
	}

}