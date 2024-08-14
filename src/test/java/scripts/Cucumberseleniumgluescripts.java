package scripts;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;

import constants.EnvConstants;
import utility.DateTimeUtils;

import coreutility.SuperHelper;
import io.qameta.allure.Allure;
import page.PageObjectsRepository;
import utility.EnvHelper;
import utility.ExcelDataReader;
import utility.FileUtility;

public class Cucumberseleniumgluescripts extends SuperHelper {

	WebDriver driver;
//	public Map<String, Object> constdata;
	// public Path screenshotPath;

	static String baseURL = EnvHelper.getValue("url");

	public void loadData() throws IOException {
		constdata = ExcelDataReader.getConstData(); // Initialize constdata
		EnvConstants.screenshotPath = FileUtility.createResultFolder(TEST_CASE_ID);
		System.out.println(screenshotPath);
	}

	public void navigatetologinscreen() {
		seOpenBrowser(EnvConstants.chromeDriver, baseURL, "Launching the application");
//        seCloseBrowser();
	}

	public void navigatetoscreen(String baseURL) {
		seOpenBrowser(EnvConstants.chromeDriver, baseURL, "Launching the application");
//       seCloseBrowser();
	}

	public void logintoApplication(String pusername, String ppassword) throws Exception {
		try {

			String username = (String) constdata.get(pusername);
			String password = (String) constdata.get(ppassword);
//			addParametersToAllure("logintoApplication", pusername);
			seOpenBrowser(EnvConstants.chromeDriver, baseURL, "Launching the application");
			seSetUserId(PageObjectsRepository.get().userName, username);
			seSetUserId(PageObjectsRepository.get().password, password);
			seClick(PageObjectsRepository.get().submit, "LoginButton");
			seCaptureScreenshot("LoginScreen", "LoginScreen");
			

		} catch (Exception e) {
			// Log the error and rethrow to ensure the scenario fails
			throw new Exception("Error : " + e.getMessage());
		}
	}

	public void clickForgotpassword(String useremail) throws Exception {
		seClick(PageObjectsRepository.get().forgotpassword, "forgotpassword");
		seSetUserId(PageObjectsRepository.get().emailaddress, useremail, "UserEmail");
		seClick(PageObjectsRepository.get().submit, "submit");

	}

	public void clickAdmin_User() throws Exception {
		seClick(true, PageObjectsRepository.get().Admin, "AdminButton");
		seWaitForPageLoad();
		seClick(true, PageObjectsRepository.get().Users, "UserButton");

	}

	public void clickAdmin_User(String email_text) throws Exception {		
		String xpath_expr = "//a[text()='" + email_text + "']";
		sefindelementClick(xpath_expr);
	}

	public void clickSecurity() throws Exception {
		seClick(true, PageObjectsRepository.get().Security, "Security");
	}

	public void createnewparticipant(String pStudyID, String pEnrollmentDate, String pPrimaryCarePhysician,
		String pLocation) throws Exception {

		String StudyID = (String) constdata.get(pStudyID);
		String EnrollmentDate = (String) constdata.get(pEnrollmentDate);
		String PrimaryCarePhysician = (String) constdata.get(pPrimaryCarePhysician);
		String Location = (String) constdata.get(pLocation);

		if (StudyID.equals("")) {
			StudyID = "Automation123";

		}
		if (EnrollmentDate.equals("")) {
			LocalDate today = LocalDate.now(); // Get today's date
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
			EnrollmentDate = today.format(formatter);
		}

		if (PrimaryCarePhysician.equals("")) {
			PrimaryCarePhysician = "AutomationPhysician";
		}

		addParametersToAllure("createnewparticipant", StudyID, EnrollmentDate, PrimaryCarePhysician, Location);

		seClick(true, PageObjectsRepository.get().newparticipant, "newparticipant");
		seSetText(PageObjectsRepository.get().studyID, StudyID);
		seSetText(PageObjectsRepository.get().enrollmentdate, EnrollmentDate);
		seSetText(PageObjectsRepository.get().primarycarephysician, PrimaryCarePhysician);
		boolean chkstatus = seSelectText(PageObjectsRepository.get().location, Location, "");

		if (!chkstatus) {
			Allure.step(
					"WARNING:Location element provided as test input cannot be found hence selectng the 1st element");
			SelectFirstDropdownOption(PageObjectsRepository.get().location);
			seCaptureScreenshot("newparticipant",
					"Location specicifed as test input cannot be found hence selectng the 1st element");
		} else {

			seCaptureScreenshot("newparticipant", "Entered Inputs:StudyID=" + StudyID + ",EnrollmentDate="
					+ EnrollmentDate + ",PrimaryCarePhysician=" + PrimaryCarePhysician + ",Location=" + Location);
		}
		seClick(PageObjectsRepository.get().saveparticipant, "saveparticipant");
	}

	public void createnewencounter(String pencounterStatus, String pencounterType) throws Exception {

		try {
		
			String encounterStatus = (String) constdata.get(pencounterStatus);
			String encounterType = (String) constdata.get(pencounterType);
			addParametersToAllure("createnewencounter", encounterStatus, encounterType);

			System.out.println("encounterStatus:" + encounterStatus);
			seClick(true, PageObjectsRepository.get().newencounter, "newencounter");
			seWaitForPageLoad();
			seHighlightElement(PageObjectsRepository.get().encounterStatus);
//		seWaitForWebElement(1000, ExpectedConditions.elementToBeClickable(PageObjectsRepository.get().encounterStatus));
			seSelectText(PageObjectsRepository.get().encounterStatus, encounterStatus, "");
			seSelectText(PageObjectsRepository.get().encounterType, encounterType, "");
			seSetText(PageObjectsRepository.get().encountercomment, "Encounter Created by Automation Script");
			seCaptureScreenshot("newencounter", "");

			seClick(PageObjectsRepository.get().encountersavechanges, "encountersavechanges");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void promisQuestionnaire() {
		seClick(true, PageObjectsRepository.get().promis2, "promis2");
		seSelectQuestionaire(PageObjectsRepository.get().questiontablepromis);
		seCaptureScreenshot("promis2", "");

	}

	public void alcoholQuestionnaire() {
		seClick(true, PageObjectsRepository.get().alcohol, "alcohol");
		seSelectQuestionaire(PageObjectsRepository.get().questiontablealcohol);
		seCaptureScreenshot("alcohol", "");

	}

	public void drugQuestionnaire() {
		seClick(true, PageObjectsRepository.get().drug, "drug");
		seSelectQuestionaire(PageObjectsRepository.get().questiontabledrug);
		seCaptureScreenshot("drug", "");

	}

	public void phqQuestionnaire() {
		seClick(true, PageObjectsRepository.get().phq, "phq");
		seSelectQuestionaire(PageObjectsRepository.get().questiontablephq);
		seCaptureScreenshot("phq", "");

	}

	public void gadQuestionnaire() {

		seClickUsingJavaScript(true, PageObjectsRepository.get().Gad7, "Gad7");
		seSelectQuestionaire(PageObjectsRepository.get().questiontablegad7);
		seCaptureScreenshot("Gad7", "");

	}

	public void overDoseQuestionnaire(String pEnrollmentDate, String pOverdoseDate, String pOverdoseTreatment,
			String pnaloxone) {

		try {
			System.out.println("OverdoseTreatment:" + pOverdoseDate);
			System.out.println("EnrollmentDate:" + pEnrollmentDate);
			String EnrollmentDate = (String) constdata.get(pEnrollmentDate);

			String OverdoseDate = (String) constdata.get(pOverdoseDate);
			String OverdoseTreatment = ((String) constdata.get(pOverdoseTreatment));
			String naloxone = ((String) constdata.get(pnaloxone));
			SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-YYYY"); // Adjust the format to match your date
																				// strings
			System.out.println("OverdoseTreatment:" + OverdoseDate);
			System.out.println("EnrollmentDate:" + EnrollmentDate);
			addParametersToAllure("overDoseQuestionnaire", EnrollmentDate, OverdoseDate, OverdoseTreatment, naloxone);
			seClickUsingJavaScript(false, PageObjectsRepository.get().Overdose, "Overdose");

			if (OverdoseTreatment.equals("Yes")) {

				seHighlightElement(PageObjectsRepository.get().overdoseYes);
				seClick(false, PageObjectsRepository.get().overdoseYes, "");

				seSelectText(PageObjectsRepository.get().overdosenaloxone, naloxone, "");
				Date enrollment = dateFormat.parse(EnrollmentDate);
				Date overdose = dateFormat.parse(OverdoseDate);

				if (overdose.compareTo(enrollment) <= 0) {
					// OverdoseDate is less than or equal to EnrollmentDate
					seSetText(PageObjectsRepository.get().overdoseDate, OverdoseDate);
				} else {
					// OverdoseDate is greater than EnrollmentDate
					seSetText(PageObjectsRepository.get().overdoseDate, EnrollmentDate);
				}
				seClick(false, PageObjectsRepository.get().overdosesave, "");
				seCaptureScreenshot("OverDose", "");
			}

		} catch (ParseException e) {
			// Handle the exception if the date parsing fails
			e.printStackTrace();
		} catch (Exception e) {
			// Handle the exception if the date parsing fails
			e.printStackTrace();
		}

	}

	public void udsQuestionnaire(String pEnrollmentDate, String pUrineDrugTest, String pUDSResults) {

		try {
			seWaitForPageLoad();
			System.out.println("EnrollmentDate:" + pEnrollmentDate);

			String EnrollmentDate = (String) constdata.get(pEnrollmentDate);

			String UrineDrugTest = (String) constdata.get(pUrineDrugTest);
			String UDSResults = ((String) constdata.get(pUDSResults));
			addParametersToAllure("udsQuestionnaire", EnrollmentDate, UrineDrugTest, UDSResults);
			seWaitForWebElement(30000,
					ExpectedConditions.visibilityOf(PageObjectsRepository.get().UDS));
			seClickUsingJavaScript(false, PageObjectsRepository.get().UDS, "UDS");
			if (UrineDrugTest.equals("Yes")) {
				seWaitForWebElement(30000, ExpectedConditions.visibilityOf(PageObjectsRepository.get().UDSYes));
				seClickUsingJavaScript(false, PageObjectsRepository.get().UDSYes, "UDSYes");

//			WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
//			wait.until(ExpectedConditions.visibilityOfElementLocated(PageObjectsRepository.get().UDSResult);
				seWaitForWebElement(30000, ExpectedConditions.visibilityOf(PageObjectsRepository.get().UDSResult));
				if (PageObjectsRepository.get().UDSResult.isDisplayed()) {
					System.out.println("clicking select");
					seWaitForWebElement(30000,
							ExpectedConditions.visibilityOf(PageObjectsRepository.get().UDSResult));
					seHighlightElement(PageObjectsRepository.get().UDSResult);
					// seClick(false ,PageObjectsRepository.get().UDSYes,"UDSYes");
					seSelectText(PageObjectsRepository.get().UDSResult, UDSResults, "");
					seWaitForWebElement(30000,
							ExpectedConditions.textToBePresentInElement(PageObjectsRepository.get().UDSResult, UDSResults));
					
					
					
					seWaitForWebElement(30000,
							ExpectedConditions.elementToBeClickable(PageObjectsRepository.get().udssave));
					System.out.println("clicked select");
				}

			}
			seWaitForWebElement(20000, ExpectedConditions.visibilityOf(PageObjectsRepository.get().udssave));
			seClick(false, PageObjectsRepository.get().udssave, "");
			seCaptureScreenshot("UDS", "");
		} catch (Exception e) {
			// Handle the exception if the date parsing fails
			e.printStackTrace();
		}

	}

	public void addParametersToAllure(String methodName, String... parameterValues) throws Exception {
		try {
			  Class<?>[] parameterTypes = new Class[parameterValues.length];
		        for (int i = 0; i < parameterValues.length; i++) {
		            parameterTypes[i] = String.class; // Assuming all parameters are of type String
		        }
			Method method = this.getClass().getMethod(methodName, parameterTypes);
			Parameter[] parameters = method.getParameters();
			// Check for length mismatch
	        if (parameters.length != parameterValues.length) {
	            System.out.println("Mismatch between parameter count and provided values.");
	            return; // Early return to avoid IndexOutOfBoundsException
	        }
			// const currentTest = Allure.getCurrentTest();
			for (int i = 0; i < parameters.length; i++) {
				String parameterName = parameters[i].getName();
				String parameterValue = parameterValues[i];

				if (parameterValue != null) {
					System.out.println("parameterValue"+ parameterValue);
//	        	 Allure.step("WARNING:Location element provided as test input cannot be found hence selectng the 1st element");
					Allure.step(parameterName + ":" + parameterValue);
//					Allure.parameter(parameterName, parameterValue);
				}
			}
		} catch (NoSuchMethodException e) {
	        System.out.println("No such method: " + e.getMessage());
	    } catch (Exception e) {
	        System.out.println("An error occurred while adding parameters to Allure: " + e.getMessage());
	    }
	}

	public void rxmedsQuestionnaire() {
		try {

			seWaitForPageLoad();
			seWaitForWebElement(20000, ExpectedConditions.visibilityOf(PageObjectsRepository.get().rxmeds));
			seClickUsingJavaScript(false, PageObjectsRepository.get().rxmeds, "rxmeds");
			seClickbuttonRamdomlyinTable(PageObjectsRepository.get().questiontablerxmed);
//			seClick(false, PageObjectsRepository.get().rxmedssave, "");
			seClickUsingJavaScript(false, PageObjectsRepository.get().rxmedssave,"");
			seCaptureScreenshot("RXMeds", "");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public void contigencyQuestionnaire(String pContingency) {
		try {
			System.out.println("EnrollmentDate:" + pContingency);
			String contigencycheck = (String) constdata.get(pContingency);
			seHighlightElement(PageObjectsRepository.get().contigency);
			seClickUsingJavaScript(false, PageObjectsRepository.get().contigency,"");
			
			addParametersToAllure("contigencyQuestionnaire", contigencycheck);
			System.out.println("contigencycheck:" + contigencycheck);
			if (contigencycheck.equals("Yes")) {
				seWaitForPageLoad();
				seWaitForWebElement(30000,
						ExpectedConditions.visibilityOf(PageObjectsRepository.get().contigencyenroll));
				seClickUsingJavaScript(false, PageObjectsRepository.get().contigencyenroll, "contigency");
				seWaitForPageLoad();
				
				
				seWaitForWebElement(30000,
				ExpectedConditions.visibilityOf(PageObjectsRepository.get().contigencyalertyes));
				seClick(false, PageObjectsRepository.get().contigencyalertyes, "");
				seWaitForPageLoad();
				seWaitForWebElement(30000,
						ExpectedConditions.visibilityOf(PageObjectsRepository.get().contigencyresponsivetable));
				
				seHighlightElement(PageObjectsRepository.get().contigencyresponsivetable);
				seCaptureScreenshot("Contigencymangement", "");
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}
