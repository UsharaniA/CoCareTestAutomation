package scripts;

import java.io.IOException;
import java.nio.file.Path;
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
	public Map<String, Object> constdata;
	//public Path screenshotPath; 
	
	static String baseURL = EnvHelper.getValue("url");

    public void loadData() throws IOException {
        constdata = ExcelDataReader.getTestData(); // Initialize constdata
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
    
	public  void logintoApplication(String username, String password) throws Exception  {
		try {
		
//	     String username = (String) constdata.get("pUsername");
//	     String password = (String) constdata.get("pPassword");

	     seOpenBrowser(EnvConstants.chromeDriver, baseURL, "Launching the application");
	     seSetUserId(PageObjectsRepository.get().userName,username);
	     seSetUserId(PageObjectsRepository.get().password,password);
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
		
	
	public  void createnewparticipant(String StudyID,String EnrollmentDate,String PrimaryCarePhysician,String Location) throws Exception {
		seClick(true,PageObjectsRepository.get().newparticipant,"newparticipant");
		seSetText(PageObjectsRepository.get().studyID,StudyID);
		seSetText(PageObjectsRepository.get().enrollmentdate,EnrollmentDate);
		seSetText(PageObjectsRepository.get().primarycarephysician,PrimaryCarePhysician);
		boolean chkstatus=seSelectText(PageObjectsRepository.get().location, Location,"");
		
		  System.out.println("chkstatus:" + chkstatus);
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
	
	
	public  void createnewencounter(String encounterStatus,String encounterType) throws Exception {
		seClick(true,PageObjectsRepository.get().newencounter,"newencounter");
		seSetText(PageObjectsRepository.get().encounterStatus,encounterStatus);
		seSetText(PageObjectsRepository.get().encounterType,encounterType);
		
		  seCaptureScreenshot("newencounter","");	

		seClick(PageObjectsRepository.get().encountersavechanges, "encountersavechanges");
	}
		
		
	  
	
}
