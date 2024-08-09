Feature: User login Authetication
 In order to access my account As a registered user I want to log in to the application
 
 @TestCaseKey=CC-T21 
Scenario Outline: ValidateLoginScreenAuthenticationInvalidCredential

Given the user navigates to the login page
When the user clicks on the forgot password button and provided the "<useremail>" to reset
And query the database to get the token and navigate to the page to reset the password
Then enter the newpassword and click on Update Password and the system should display an error message:
     | newpassword |   errormessage                                            |
     | Test123     |   Password must be more than 10 characters long.          |
     |Test123art5  |   Password must contain at least one number and one symbol.|
      

 Examples:
      | useremail                                | password      | 
      | cocareproject2024@gmail.com              | Eclipse$2024  | 
   



