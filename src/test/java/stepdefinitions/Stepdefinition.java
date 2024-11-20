package stepdefinitions;

import io.cucumber.java.en.Given;

import io.cucumber.java.en.When;
import io.qameta.allure.Allure;
import io.qameta.allure.Attachment;
import io.qameta.allure.Step;
import page.PageObjectsRepository;
import scripts.Cucumberseleniumgluescripts;
import utility.DatabaseUtils;
import utility.DatabaseUtils2;
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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
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

	@Then("enter the newpassword and click on Submit Password and the system should display an error message:")
	public void the_system_should_display_an_error_message(DataTable dataTable) throws Exception {
		try {
			List<List<String>> data = dataTable.asLists(String.class);
			for (int row = 1; row < data.size(); row++) { // Start from row 1 to skip header
				seSetUserId(PageObjectsRepository.get().password, data.get(row).get(0));
				seSetUserId(PageObjectsRepository.get().pass_confirm, data.get(row).get(0),
						"PasswordConfirmation" + row);
				seClick(PageObjectsRepository.get().submit1, "submit1");
				String expectedErrorMessage = data.get(row).get(1);
				seCompareText(PageObjectsRepository.get().alert, expectedErrorMessage, "Validate error");
				seCaptureScreenshot("Validation" + row,"");
			}

		} catch (Exception e) {
			hooks.handleexceptioncucumbersteps(e, "UpdatePassword");
			throw e;
		}

	}
	
	
	
	@Then("enter the newpassword and click on Update Password and the system should display an error message:")
	public void the_system_should_display_an_error_message_admin(DataTable dataTable) throws Exception {
		try {
			List<List<String>> data = dataTable.asLists(String.class);
			for (int row = 1; row < data.size(); row++) { // Start from row 1 to skip header
				seSetUserId(PageObjectsRepository.get().password, data.get(row).get(0));
				seSetUserId(PageObjectsRepository.get().pass_confirm, data.get(row).get(0),
						"PasswordConfirmation" + row);
				seClick(PageObjectsRepository.get().updatepassword, "UpdatePassword");
				String expectedErrorMessage = data.get(row).get(1);
				seCompareText(PageObjectsRepository.get().alert, expectedErrorMessage, "Validate error");
				seCaptureScreenshot("Validation" + row,"");
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
		
		try {
			createnewparticipant(StudyID, EnrollmentDate, PrimaryCarePhysician, Location);
			 
		} catch (Exception e) {
			hooks.handleexceptioncucumbersteps(e, "createnewparticiapntwithdetails");
			throw e;
		}
	}

//	And enter details to create New Encounter with EncounterStatus "<EncounterStatus>" and EncounterType "<EncounterType>"
	@And("enter details to create New Encounter with EncounterStatus {string} and EncounterType {string}")
	public void createencounterfromdetails(String EncounterStatus, String EncounterType) throws Exception {

		try {
			createnewencounter(EncounterStatus, EncounterType);
			

		} catch (Exception e) {
			hooks.handleexceptioncucumbersteps(e, "createnewencounterwithdetails");
			throw e;
		}
	}

	@Then("update Questionnaire for Promis")
	public void update_Questionnaire_Promis() throws Exception {
		try {
			promisQuestionnaire();

		} catch (Exception e) {
			hooks.handleexceptioncucumbersteps(e, "update_Questionnaire_Promis");
			throw e;
		}
	}
	
	@Then("update Questionnaire for Alcohol")
	public void update_Questionnaire_Alcohol() throws Exception {
		try {
			alcoholQuestionnaire();

		} catch (Exception e) {
			hooks.handleexceptioncucumbersteps(e, "update_Questionnaire_Promis");
			throw e;
		}
	}
	
	@Then("update Questionnaire for Drug")
	public void update_Questionnaire_Drug() throws Exception {
		try {
			drugQuestionnaire();

		} catch (Exception e) {
			hooks.handleexceptioncucumbersteps(e, "update_Questionnaire_Promis");
			throw e;
		}
	}
	
	@Then("update Questionnaire for PHQ")
	public void update_Questionnaire_PHQ() throws Exception {
		try {
			phqQuestionnaire();

		} catch (Exception e) {
			hooks.handleexceptioncucumbersteps(e, "update_Questionnaire_Promis");
			throw e;
		}
	}
	
	@Then("update Questionnaire for GAD")
	public void update_Questionnaire_GAD() throws Exception {
		try {
			gadQuestionnaire();

		} catch (Exception e) {
			hooks.handleexceptioncucumbersteps(e, "update_Questionnaire_Promis");
			throw e;
		}
	}
	
	@Then("update Questionnaire for Overdose with EnrollmentDate {string} ,OverdoseDate {string} , OverdoseTreatment {string} ,naloxone {string}")
	public void update_Questionnaire_Overdose(String pEnrollmentDate ,String pOverdoseDate ,String pOverdoseTreatment ,String pnaloxone) throws Exception {
		try {
			overDoseQuestionnaire(pEnrollmentDate,pOverdoseDate,pOverdoseTreatment,pnaloxone);

		} catch (Exception e) {
			hooks.handleexceptioncucumbersteps(e, "update_Questionnaire_Overdose");
			throw e;
		}
	}
	
	@Then("update Questionnaire for UDS with UDSDate {string} , UrineDrugTest {string} ,UDSResults {string}")
			public void update_Questionnaire_UDS(String pEnrollmentDate ,String pUrineDrugTest ,String pUDSResults) throws Exception {
		try {
			udsQuestionnaire(pEnrollmentDate,pUrineDrugTest,pUDSResults);

		} catch (Exception e) {
			hooks.handleexceptioncucumbersteps(e, "update_Questionnaire_UDS");
			throw e;
		}
	}


	@Then("update Questionnaire for RxMeds")
	public void update_Questionnaire_RxMeds() throws Exception {
		try {
			rxmedsQuestionnaire();

		} catch (Exception e) {
			hooks.handleexceptioncucumbersteps(e, "update_Questionnaire_RxMeds");
			throw e;
		}
	}
	
	@Then("update Questionnaire for Contigency Management using {string}")
	public void update_Questionnaire_ContigencyMgt(String pContingency) throws Exception {
		try {
			contigencyQuestionnaire(pContingency);

		} catch (Exception e) {
			hooks.handleexceptioncucumbersteps(e, "update_Questionnaire_ContigencyMgt");
			throw e;
		}
	}
	
	@When("the user clicks on the My Participant button")
	public void clickonMyParticiapantbutton() throws Exception {
		
		try {
			clickonMyParticiapant();
			 
		} catch (Exception e) {
			hooks.handleexceptioncucumbersteps(e, "clickonMyParticiapantbutton");
			throw e;
		}
	}
	
	
	@And("select the registry id {string}")
	public void selectsregistryid(String pregistryid) throws Exception {
		try {
			clickregistryid(pregistryid);
		} catch (Exception e) {
			hooks.handleexceptioncucumbersteps(e, "selectsregistryid");
			
			throw e;
		}
		
	}
		
		
	@Then ("if any encounter is inprogress ,a new encounter should not be created")
	public void testinprogressencounter() throws Exception {
		
		try {
			checkinprogressencounter();
		} catch (Exception e) {
			hooks.handleexceptioncucumbersteps(e, "testinprogressencounter");			
			throw e;
		}
	}
	
	
	@Then ("the account should be locked and  the user should see an {string} message")
	public void testaccountlockinactive(String errmsg) throws Exception {
		
		try {
			checkaccountlock( errmsg);
			
		} catch (Exception e) {
			hooks.handleexceptioncucumbersteps(e, "testaccountlockinactive");			
			throw e;
		}
	}
	
	
	@Given("the account is active for {string}")
	public void activateaccount(String useremail)throws Exception {
		try {
			 String username = (String) constdata.get(useremail);
			 DatabaseUtils2.dbActivateAccount(username);
		
		} catch (Exception e) {
			hooks.setException(e);
			hooks.captureAndAttachScreenshotToAllure("Login");
			throw e; 
		}
	}	

	
	
	@When("the user logins in with valid useremail {string} and invalid password {string} and clicks the login button")
	public void theUserEntersinValidUserEmailAndPasswordAndClicksTheLoginButton(String email, String password)
			throws Exception {
		try {
			logininvalidcredentials(true,email, password);
		} catch (Exception e) {
			hooks.setException(e);// Store the exception for the hook
			hooks.captureAndAttachScreenshotToAllure("Login");
			throw e; // Rethrow the exception to mark the step as failed
		}
	}	

	@Then("again the user logins in with valid useremail {string} and invalid password {string} and clicks the login button")
	public void theUseragainEntersinValidUserEmailAndPasswordAndClicksTheLoginButton(String email, String password)
			throws Exception {
		try {
			logininvalidcredentials(false,email, password);
		} catch (Exception e) {
			hooks.setException(e);// Store the exception for the hook
			hooks.captureAndAttachScreenshotToAllure("Login");
			throw e; // Rethrow the exception to mark the step as failed
		}
	}
	
	
	@Given("the account password expired for useremail {string}")
	public void setpasswordexpiryinDB(String useremail) {
		String username = (String) constdata.get(useremail);
        LocalDateTime currentDate = LocalDateTime.now();
        LocalDateTime pastDate = currentDate.minus(2, ChronoUnit.DAYS); // Example: subtracting 1 day
        Allure.step("The account password expired on " + pastDate );
        DatabaseUtils2.updateDBcolumn("password_expires_at",username,pastDate);

	}
	
	@Then("reset the account for useremail {string}")
	public void resettheaccount(String useremail) {
		String username = (String) constdata.get(useremail);
        DatabaseUtils2.updateDBforcereset("force_reset","0",useremail);

	}
	
	@When("account inactive for more than 30 days for useremail {string}")
	public void setlastuseddateinDB(String useremail) {		
		try {
			String username = (String) constdata.get(useremail);
            LocalDateTime currentDate = LocalDateTime.now();
            LocalDateTime pastDate = currentDate.minus(32, ChronoUnit.DAYS); // Example: subtracting 1 day
            Allure.step("The account was active on " + pastDate );
            DatabaseUtils2.updateDBcolumn("last_used_at",username,pastDate);
		} catch (Exception e) {
			hooks.setException(e);// Store the exception for the hook
			hooks.captureAndAttachScreenshotToAllure("Login");
			throw e; // Rethrow the exception to mark the step as failed
		}
	}
	

	@And("again the user clicks on the forgot password button and provided the {string} to reset")
	public void theUserClicksOnTheforgotButton3time(String useremail) throws Exception {
		try {
			navigatetologinscreen();
			clickForgotpassword(useremail);
		} catch (Exception e) {
			hooks.setException(e);// Store the exception for the hook
			hooks.captureAndAttachScreenshotToAllure("Login");
			throw e; // Rethrow the exception to mark the step as failed
		}
	}
	
	@Then ("the user should see an {string} message")
	public void userseesmessage(String errmsg) throws Exception {
		
		try {
			checkaccountlock( errmsg);
		} catch (Exception e) {
			hooks.handleexceptioncucumbersteps(e, "userseesmessage");			
			throw e;
		}
	}
	
	
	@Then ("the user should see an warning message {string}")
	public void userseeswarningmessage(String errmsg) throws Exception {
		
		try {
			checkwarningmsg( errmsg);
		} catch (Exception e) {
			hooks.handleexceptioncucumbersteps(e, "userseeswarningmessage");			
			throw e;
		}
	} 
	@Then ("admin should see an warning message {string} and {string}")
	public void adminseesmessage(String errmsg1 ,String errmsg2) throws Exception {
		
		try {
			adminnotification( errmsg1,errmsg2);
		} catch (Exception e) {
			hooks.handleexceptioncucumbersteps(e, "testaccountlockinactive");			
			throw e;
		}
	}


	@Then("login as admin with {string} and {string}")
	public void loginasadmin(String email, String password)
			throws Exception {
		try {
			logintoApplication(email, password);
		} catch (Exception e) {
			hooks.setException(e);// Store the exception for the hook
			hooks.captureAndAttachScreenshotToAllure("Login");
			throw e; // Rethrow the exception to mark the step as failed
		}
	}
	
	@And("the admin unlock the account")
	public void accountunlock() throws Exception {
		try {
			adminaccountunlock();
		} catch (Exception e) {
			hooks.setException(e);// Store the exception for the hook
			hooks.captureAndAttachScreenshotToAllure("Login");
			throw e; // Rethrow the exception to mark the step as failed
		}
	}
	
	
	
	

//	/final class
}
