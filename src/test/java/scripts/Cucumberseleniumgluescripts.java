package scripts;

import java.io.IOException;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import org.openqa.selenium.WebDriver;

import constants.EnvConstants;
import utility.DateTimeUtils;


import coreutility.SuperHelper;
import io.qameta.allure.Allure;
import page.PageObjectsRepository;
import utility.EnvHelper;
import utility.ExcelDataReader;
import utility.FileUtility;

public class Cucumberseleniumgluescripts extends SuperHelper{
	

	
	WebDriver driver;
//	public Map<String, Object> constdata;
	//public Path screenshotPath; 
	
	static String baseURL = EnvHelper.getValue("url");

    public void loadData() throws IOException {
        constdata = ExcelDataReader.getConstData(); // Initialize constdata
        System.out.println(constdata);
        EnvConstants.screenshotPath=FileUtility.createResultFolder(TEST_CASE_ID);
        System.out.println(screenshotPath);
    }
	
	public  void navigatetologinscreen() {
	     seOpenBrowser(EnvConstants.chromeDriver, baseURL, "Launching the application");	     
//        seCloseBrowser();
		}
	
	public  void navigatetoscreen(String baseURL) {
	     seOpenBrowser(EnvConstants.chromeDriver, baseURL, "Launching the application");	     
//       seCloseBrowser();
		}
    
	public  void logintoApplication(String pusername, String ppassword) throws Exception  {
		try {
			
	      pusername = (String) constdata.get(pusername);
	      ppassword =  (String) constdata.get(ppassword);
	      System.out.println(pusername);
	     seOpenBrowser(EnvConstants.chromeDriver, baseURL, "Launching the application");
	     seSetUserId(PageObjectsRepository.get().userName,pusername);
	     seSetUserId(PageObjectsRepository.get().password,ppassword);
	     seClick(PageObjectsRepository.get().submit,"LoginButton");
	     seCaptureScreenshot("LoginScreen","LoginScreen");	     
//         seCloseBrowser();
	     
	 } catch (Exception e) {
	        // Log the error and rethrow to ensure the scenario fails
	        throw new Exception("Error : " + e.getMessage());
	    }
		}
	
	public  void clickForgotpassword(String useremail) throws Exception {
		seClick(PageObjectsRepository.get().forgotpassword,"forgotpassword");
		seSetUserId(PageObjectsRepository.get().emailaddress,useremail,"UserEmail");
		seClick(PageObjectsRepository.get().submit,"submit");
		
	}
	
	
	
	public  void clickAdmin_User() throws Exception {
	     seClick(true,PageObjectsRepository.get().Admin,"AdminButton");
	     seWaitForPageLoad();
	     seClick(true,PageObjectsRepository.get().Users,"UserButton");	   

		}
	
	public  void clickAdmin_User(String email_text) throws Exception {
//		seClicktabledata(LoginPage.get().Usertable,username);		
		String xpath_expr = "//a[text()='"+email_text+"']";
		sefindelementClick(xpath_expr);
//		element = driver.find_element_by_xpath(xpath_expr)
//		testObject.isDisplayed()) {
//			testObject.click();
		}
	
	public  void clickSecurity() throws Exception {
		seClick(true,PageObjectsRepository.get().Security,"Security");
		}
		
	
	public  void createnewparticipant(String pStudyID,String pEnrollmentDate,String pPrimaryCarePhysician,String pLocation) throws Exception {


		
		String StudyID =  (String) constdata.get(pStudyID);
		String EnrollmentDate =  (String) constdata.get(pEnrollmentDate);
		String PrimaryCarePhysician =  (String) constdata.get(pPrimaryCarePhysician);
		String Location =  (String) constdata.get(pLocation);
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
		
		seClick(true,PageObjectsRepository.get().newparticipant,"newparticipant");
		seSetText(PageObjectsRepository.get().studyID,StudyID);
		seSetText(PageObjectsRepository.get().enrollmentdate,EnrollmentDate);
		seSetText(PageObjectsRepository.get().primarycarephysician,PrimaryCarePhysician);
		boolean chkstatus=seSelectText(PageObjectsRepository.get().location, Location,"");
		
		
		if (!chkstatus) {
			 Allure.step("WARNING:Location element provided as test input cannot be found hence selectng the 1st element");
			SelectFirstDropdownOption(PageObjectsRepository.get().location);
			seCaptureScreenshot("newparticipant","Location specicifed as test input cannot be found hence selectng the 1st element");	
		}
		else {
		
		  seCaptureScreenshot("newparticipant","");	
		}
		seClick(PageObjectsRepository.get().saveparticipant, "saveparticipant");
	}
	
	
	public  void createnewencounter(String pencounterStatus,String pencounterType) throws Exception {
		  System.out.println("encounterStatus:" + pencounterStatus);
		String encounterStatus =  (String) constdata.get(pencounterStatus);
		String encounterType =  (String) constdata.get(pencounterType);

		  System.out.println("encounterStatus:" + encounterStatus);
		seClick(true,PageObjectsRepository.get().newencounter,"newencounter");
		seWaitForPageLoad();
		seHighlightElement(PageObjectsRepository.get().encounterStatus);
//		seWaitForWebElement(1000, ExpectedConditions.elementToBeClickable(PageObjectsRepository.get().encounterStatus));
		seSelectText(PageObjectsRepository.get().encounterStatus,encounterStatus,"");
		seSelectText(PageObjectsRepository.get().encounterType,encounterType,"");
		seSetText(PageObjectsRepository.get().encountercomment,"Encounter Created by Automation Script");
		  seCaptureScreenshot("newencounter","");	

		seClick(PageObjectsRepository.get().encountersavechanges, "encountersavechanges");
	}
	
	public void promisQuestionnaire() {
		seClick(true,PageObjectsRepository.get().promis2,"promis2");
		seSelectQuestionaire(PageObjectsRepository.get().questiontablepromis);
		seCaptureScreenshot("promis2","");
		
	}
	
	public void alcoholQuestionnaire() {
		seClick(true,PageObjectsRepository.get().alcohol,"alcohol");
		seSelectQuestionaire(PageObjectsRepository.get().questiontablealcohol);
		seCaptureScreenshot("alcohol","");
		
	}
	
	public void drugQuestionnaire() {
		seClick(true,PageObjectsRepository.get().drug,"drug");
		seSelectQuestionaire(PageObjectsRepository.get().questiontabledrug);
		seCaptureScreenshot("drug","");
		
	}
	
	public void phqQuestionnaire() {
		seClick(true,PageObjectsRepository.get().phq,"phq");
		seSelectQuestionaire(PageObjectsRepository.get().questiontablephq);
		seCaptureScreenshot("phq","");
		
	}
	
	public void gadQuestionnaire() {
		seHighlightElement(PageObjectsRepository.get().Gad7);		
		seClickUsingJavaScript(true,PageObjectsRepository.get().Gad7,"Gad7");
		seSelectQuestionaire(PageObjectsRepository.get().questiontablegad7);
		seCaptureScreenshot("Gad7","");
		
	}
		
	  
	
}
