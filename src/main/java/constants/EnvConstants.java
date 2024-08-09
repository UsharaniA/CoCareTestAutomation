
package constants;

import java.nio.file.Path;

/**
 * @author usharani A
 * 
 * This class works as a Bridge between env.properties files and the EnvHelper class providing values from env.properties 
 *
 */
public class EnvConstants {

	public static String logsPath = "logs.path";
	
	public static String reportsPath = "reports.path";
	public static Path screenshotPath;	
	public static String inputDataPath = "input.runorder.excel.file";	
	public static  String inputRunOrderSheet = "input.runorder.worksheet";		
	public static String driversFolderPath = "drivers.folder.path";	
	public static String chromeDriver = "chrome.driver";	
	public static String headlessDriver = "headless.driver";
	public static  String TEST_CASE_ID = "";
	
	// Log Status Values
		public static final int FAIL = 0;
		public static final int PASS = 1;
		public static final int FATAL = 2;
		public static final int ERROR = 3;

		
//Jira values		
public static String jiraurl= "jira.url";
public static String jirausername="jira.username";
public static String	jiratoken="jira.password";
public static String	jiraprojectid ="zephyr.projectId";
public static String	jiratestcycle ="zephyr.testcycle";
public static  String jiratestkey = "";


public static String	testcycleid ="zephyr.cycleId";
	

}
