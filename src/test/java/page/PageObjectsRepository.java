package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.CacheLookup;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;

import coreutility.SuperHelper;
/*
'Revision History
'#############################################################################
'@rev.On	@rev.No		@rev.By				  @rev.Comments
'										
'#############################################################################
*/

/**
 * Page: SamplePage<p>
 * Page Object Model Sample<p>
 * Please refer this page while creating other page object models
 * 
 * @author Usharani A
 *
 */
public class PageObjectsRepository extends SuperHelper {

	private static PageObjectsRepository thisIsTestObj;


	// So that there only one object accesses this class at any moment
	public synchronized static PageObjectsRepository get() {
		thisIsTestObj = PageFactory.initElements(driver, PageObjectsRepository.class);
		return thisIsTestObj;
	}
	


	// Recommended model for all objects
	@FindBy(how = How.NAME, using = "email")
	@CacheLookup
	public WebElement userName;
	
	@FindBy(how = How.NAME, using = "password")
	@CacheLookup
	public WebElement password;
	
	@FindBy(how = How.XPATH, using = "//button[@type='submit']")
	@CacheLookup
	public WebElement submit;
	

	@FindBy(how = How.XPATH, using = "(//button[@class='btn btn-primary'])[2]")
	@CacheLookup
	public WebElement Admin;
	
	@FindBy(how = How.XPATH, using = "//a[@href='/admin/users']")
	@CacheLookup
	public WebElement Users;
	
	@FindBy(how = How.XPATH, using = "//*[@class='table table-hover']")
	@CacheLookup
	public WebElement Usertable;
	
	
	
	@FindBy(how = How.XPATH, using = "//a[contains(@href, 'security')]")
	@CacheLookup
	public WebElement Security;
	
	@FindBy(how = How.ID, using = "pass_confirm")
	@CacheLookup
	public WebElement pass_confirm;
	
	@FindBy(how = How.XPATH, using = "//input[@type='submit']")
	@CacheLookup
	public WebElement submit1;
	
//	@FindBy(how = How.XPATH, using = "//a[text()='{email}']")
//	@CacheLookup
//	public useremail;
	
	@FindBy(how = How.XPATH, using = "//a[text()='{email}']")
	@CacheLookup
	public WebElement useremail;
	
	@FindBy(how = How.XPATH, using = "//*[@role='alert']")
	@CacheLookup
	public WebElement alert;
	
	
	@FindBy(how = How.XPATH, using = "//a[contains(@href, 'forgot-password')]")
	@CacheLookup
	public WebElement forgotpassword;
	
	@FindBy(how = How.NAME, using = "email")
	@CacheLookup
	public WebElement emailaddress;
	
	@FindBy(how = How.XPATH, using = "//*[@class='card-text' and text()='New Participant']")
	@CacheLookup
	public WebElement newparticipant;
	
	@FindBy(how = How.XPATH, using = "//input[@type='text'and @autocomplete='alias']")
	@CacheLookup
	public WebElement studyID;
	
	@FindBy(how = How.XPATH, using = "//input[@type='text'and @autocomplete='pcp_id']")
	@CacheLookup
	public WebElement primarycarephysician;
	
	@FindBy(how = How.NAME, using = "enrollment_date")
	@CacheLookup
	public WebElement enrollmentdate;
	
	@FindBy(how = How.XPATH, using = "//input[@type='submit'and @value='Save Participant']")
	@CacheLookup
	public WebElement saveparticipant;
	
	@FindBy(how = How.XPATH, using = "//*[@id='location_id_select']")
	@CacheLookup
	public WebElement location;
	
	@FindBy(how = How.XPATH, using = "//span[normalize-space(text())='New Participant Encounter']")
	@CacheLookup
	public WebElement newencounter;	
	
	@FindBy(how = How.XPATH, using = "//input[@id='encounter_datetime']")
	@CacheLookup
	public WebElement encounterDateTime;	
	
	
	@FindBy(how = How.XPATH, using = "//input[@id='encounter-status-select']")
	@CacheLookup
	public WebElement encounterStatus;	
	
	@FindBy(how = How.XPATH, using = "//input[@id='encounter_type_select']")
	@CacheLookup
	public WebElement encounterType;	

	@FindBy(how = How.XPATH, using = "//input[@id='exampleFormControlTextarea1']")
	@CacheLookup
	public WebElement encountercomment;	
	
	@FindBy(how = How.XPATH, using = "//input[@type='submit'and @value='Save Changes']")
	@CacheLookup
	public WebElement encountersavechanges;	
	
	
	
	

	
	
	
	
	
//  
//	//Other ways 
//	public By Identification_Number = By.xpath("//input[@id=\"ctl00_MainContent_maincontent_ctl01_txtIdentificationNum\"]"); 
//
//	// If property of an object is dynamic in nature
//	WebElement pwd;
//	public WebElement password(String strNameValue) {
//		pwd = driver.findElement(By.name(strNameValue));
//		return pwd;
//
//	}
//
//	// in case you want to use XPATH
//	@FindBy(how = How.XPATH, using = "//input[@type='submit']")
//	@CacheLookup
//	public WebElement submit;


	/**
	 * Logs into the application
	 * @param userProfile
	 * @author Santosh Bukkashetti (AF37512)
	 */
//	public void loginApplication(String userProfile) {
//
//		//getLoginInfo function provides the user id and password from the user profile
//		String[] userInfo = getLoginInfo(userProfile);
//
//		setUserName(userInfo[0]);
//
//		setPassword(userInfo[1]);
//
//		clickSubmit();
//	}
//
//	/**
//	 * Sets user name
//	 * @param strUserID User ID
//	 * @author Santosh Bukkashetti (AF37512)
//	 */
//	public void setUserName(String strUserID) {
//		seSetUserId(userName, strUserID, "User Name");
//	}
//
//	
//	/**
//	 * Sets password
//	 * @param strPasword encrypted password
//	 * @author Santosh Bukkashetti (AF37512)
//	 */
//	public void setPassword(String strPasword) {
//		seSetPassword(pwd, strPasword, "Enter Password");
//	}
//
//	
//	/**
//	 * Clicks on Submit button
//	 * @author Santosh Bukkashetti (AF37512)
//	 */
//	public void clickSubmit() {
//		seClick(submit, "Click Submit button");
//	}
//
}
