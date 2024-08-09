package stepdefinitions;

import io.cucumber.java.en.Given;

import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import page.PageObjectsRepository;
import scripts.Cucumberseleniumgluescripts;
import utility.DatabaseUtils;
import utility.EnvHelper;
import utility.ExcelDataReader;
import utility.FileUtility;
import utility.ZephyrScaleAPI;
import utility.allurereportmanager;
import io.cucumber.java.en.Then;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.AfterClass;
import org.junit.Assert;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import coreutility.SuperHelper;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;
import io.cucumber.java.en.And;

public class Stepdefinition extends Cucumberseleniumgluescripts {
	WebDriver driver;
	static String baseURL = EnvHelper.getValue("url");
	String testCaseId = "CC-T21";
	public Scenario scenario;
//	public Path testreportpath; 
	private static Set<String> currentScenarioTags;
	private static int scenarioCount = 0;
	private static int completedScenarios = 0;

	// No-arg constructor for PicoContainer
	public Stepdefinition() {
		// Empty constructor
	}

	public Stepdefinition(Scenario scenario) {
		this.scenario = scenario;
		hooks.setScenario(scenario);
	}

//    public static void afterAllScenarios() throws IOException {
//        // Create a test case in Zephyr Scale after all scenarios
//		System.out.println("After All scenarios");
////      ZephyrScaleAPI.createTestCase();
//      ZephyrScaleAPI.attachFileToExecution();
//      
//
//        
//	}

//
//	@Step("Executing step: {0}")
//	public void executeStep(String stepDescription) {
//		// Log the step description in Allure
//		Allure.step(stepDescription);
//	}
//
//	@Given("the user navigates to the login page with Username and Password")
////	@Step("User navigates to the login page")
//	public void user_navigates_to_login_page() throws Exception {
//		try {
//		logintoApplication();
//		}
//	 catch (Exception e) {
////		 hooks.attachExceptionToScenario(scenario,e,"Login");
//		 allurereportmanager.captureAndAttachScreenshot("screenshotname");
////        throw e;
//    }
//
//	}

	@Given("the user navigates to the login page")
	public void the_user_navigates_to_the_login_page() {
		try {
			navigatetologinscreen();
		} catch (Exception e) {
//			hooks.attachExceptionToScenario(scenario,e,"Login");
//            throw e;
			allurereportmanager.captureAndAttachScreenshot("screenshotname");
		}

	}

	@Given("the user logins in with valid useremail {string} and password {string} and clicks the login button")
	public void theUserEntersValidUserEmailAndPasswordAndClicksTheLoginButton(String email, String password)
			throws Exception {

		try {
			logintoApplication(email, password);
			hooks.captureAndAttachScreenshotToAllure("Login");
		} catch (Exception e) {
			hooks.setException(e);// Store the exception for the hook
			hooks.captureAndAttachScreenshotToAllure("Login");
			throw e; // Rethrow the exception to mark the step as failed
		}
	}

	@When("the user clicks on the forgot password button and provided the {string} to reset")
	public void theUserClicksOnTheforgotButton(String useremail) throws Exception {
		try {
			clickForgotpassword(useremail);
		} catch (Exception e) {
//			hooks.attachExceptionToScenario(scenario,e,"ForgotPassword");
			hooks.captureAndAttachScreenshotToAllure("ForgotPassword");
//            throw e;
//			hooks.captureAndAttachScreenshotToAllure("screenshotname");
		}
	}

	@When("the user clicks on the Admin button and goes to user page and click on User button")
	public void theUserClicksOnTheAdminButton() throws Exception {
		try {
			clickAdmin_User();
		} catch (Exception e) {
			hooks.setException(e);
			hooks.captureAndAttachScreenshotToAllure("AdminButton");
			throw e;
		}
	}

	@And("select the useremail {string}")
	public void selectsTheUserEmail(String userEmail) throws Exception {
		try {
			clickAdmin_User(userEmail);
		} catch (Exception e) {
			hooks.setException(e);
			hooks.captureAndAttachScreenshotToAllure("UserEmail");
			throw e;
		}

	}

	@And("click on the Security Tab")
	public void clicksOnTheSecurityTab() throws Exception {
		try {
			clickSecurity();
		} catch (Exception e) {
			hooks.handleexceptioncucumbersteps(e, "Security");
//			hooks.setException(e);// Store the exception for the hook
//			hooks.captureAndAttachScreenshotToAllure("Security");
//			 Assert.fail("Failed to log in: NoSuchElementException - " + e.getMessage());
//            throw e;	// Rethrow the exception to mark the step as failed		
		}
	}

	@And("query the database to get the token and navigate to the page to reset the password")
	public void queryDBtoresetpassword() {
		String token = DatabaseUtils.querymysql();
		String resetURL = EnvHelper.getValue("reseturl");
		navigatetoscreen(resetURL + token);

	}

	@Then("the system should display an error message {string}")
	public void theSystemShouldDisplayAnErrorMessage(String expectedErrorMessage) throws InterruptedException {
		seCompareText(PageObjectsRepository.get().alert, expectedErrorMessage, "Validate error");

	}

	@Then("enter the newpassword and click on Update Password and the system should display an error message:")
	public void the_system_should_display_an_error_message(DataTable dataTable) throws Exception {
		try {
			List<List<String>> data = dataTable.asLists(String.class);
			for (int row = 1; row < data.size(); row++) { // Start from row 1 to skip header
				seSetUserId(PageObjectsRepository.get().password, data.get(row).get(0));
				seSetUserId(PageObjectsRepository.get().pass_confirm, data.get(row).get(0),
						"PasswordConfirmation" + row);
				seClick(PageObjectsRepository.get().submit1, "UpdatePassword");
				String expectedErrorMessage = data.get(row).get(1);
				seCompareText(PageObjectsRepository.get().alert, expectedErrorMessage, "Validate error");
				seCaptureScreenshot("Error" + row);
			}

		} catch (Exception e) {
			hooks.handleexceptioncucumbersteps(e, "UpdatePassword");
			throw e;
		}

	}

//	When   the user clicks on the New Participant button , create New Participant with StudyID "<StudyID>",EnrollmentDate "<EnrollmentDate>",PrimaryCarePhysician "<PrimaryCarePhysician>",Location "<Location>"  and Save Participant
	@When("the user clicks on the New Participant button , create New Participant with StudyID {string},EnrollmentDate {string},PrimaryCarePhysician {string},Location {string} and Save Participant")
	public void createnewparticiapntwithdetails(String StudyID, String EnrollmentDate, String PrimaryCarePhysician,
			String Location) throws Exception {
		if (StudyID.equals("")) {
			StudyID="Automation123"	;
			
		}
		if (EnrollmentDate.equals("")) {
			LocalDate today = LocalDate.now(); // Get today's date	  
	        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
	        EnrollmentDate = today.format(formatter);		
		}
		
		if (PrimaryCarePhysician.equals("")) {
			PrimaryCarePhysician="AutomationPhysician"	;	
		}
		try {

		createnewparticipant(StudyID, EnrollmentDate, PrimaryCarePhysician, Location);
	
		} catch (Exception e) {
			hooks.handleexceptioncucumbersteps(e, "createnewparticiapntwithdetails");
			throw e;
		}
		}
	
	
//	And enter details to create New Encounter with EncounterStatus "<EncounterStatus>" and EncounterType "<EncounterType>"
	@And("enter details to create New Encounter with EncounterStatus {string} and EncounterType {string}")
	public void createencounterfromdetails(String EncounterStatus, String EncounterType ) throws Exception {
	
		try {
			createnewencounter(EncounterStatus, EncounterType);
		
		
	} catch (Exception e) {
		hooks.handleexceptioncucumbersteps(e, "createnewencounterwithdetails");
		throw e;
	}
	}
}
