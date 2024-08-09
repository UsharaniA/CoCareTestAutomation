1. Download and Install Java
•	Download JDK:
o	Go to the Oracle JDK download page or AdoptOpenJDK.
o	Run the downloaded installer and follow the on-screen instructions.
o	Note the installation directory (usually C:\Program Files\Java\jdk-<version>).
•	Set the System Path
o	Right-click on This PC or My Computer and select Properties.
o	Click on Advanced system settings on the left side.
o	Click on the Environment Variables button.
•	Set JAVA_HOME Variable:
o	In the Environment Variables window, click on New under the System variables section.
o	Set the variable name to JAVA_HOME.
o	Set the variable value to the path where JDK is installed (e.g., C:\Program Files\Java\jdk-<version>).
•	Update PATH Variable:
o	In the System variables section, find the Path variable and select it, then click Edit.
o	In the Edit Environment Variable window, click New and add the following:
o	%JAVA_HOME%\bin
o	Click OK to save your changes.
•	Verify the Installation:
o	Open a new Command Prompt window (search for cmd).
o	Type the following command and press Enter:
java -version
o	You should see the installed version of Java. If you see an error, ensure that you have followed the steps correctly.

2.	Install Eclipse 
•	Visit the Eclipse Downloads page.
•	Choose the Eclipse IDE for Java Developers (or another package based on your needs).
•	Click on the download link for the latest version and run the installer.
3.	Clone the Code from Git Repository - https://github.com/UsharaniA/CoCareTestAutomation.git
4.	Project Build – Using Maven
o	Right-click on the project in the Project Explorer.
o	Select Run As > Maven build...
o	In the Goals field, enter clean install and click Run.
	clean: Cleans the target directory.
	install: Compiles the code, runs tests, and installs the package into the local repository.
o	Maven will download the necessary dependencies, compile the code, run tests, and package the project.

5.	Set the Test Case in the Runner file.
 
![image](https://github.com/user-attachments/assets/1748c209-aaef-4439-99d8-26a89f456aeb)

![image](https://github.com/user-attachments/assets/4d752022-dd5d-49b3-bb7e-461325c6f0a7)


 


src/test/java/features/*.feature – Will Execute all the Feature files under src/test/java/features folder
6.	Find the Screenshots under your Project Directory 

![image](https://github.com/user-attachments/assets/9e541dfe-3f0c-4933-93c2-b0d2ad6cd1ac)

 
7.	Create HTML Reports
Goto to terminal from  the project root folder from Eclipse (Ctrl+Alt+T) and enter allure generate –clean

 ![image](https://github.com/user-attachments/assets/2f6c0ddf-d9e1-420a-9b3a-1d8b94e046a2)


Once the Report is created , host the report in local browser with command allure serve

![image](https://github.com/user-attachments/assets/d4a9fd31-fc23-401b-b3e8-dc5abf23e20d)


 
