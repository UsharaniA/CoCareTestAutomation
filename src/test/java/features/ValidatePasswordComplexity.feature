Feature: ValidatePasswordComplexity 
1.	Password must contain at least one number, one symbol, and one uppercase letter,one lower case letter.
2.	Password Should more than 10 Characters long.
3.	Non-Derivative Passwords
4.	No Spatial Patterns
5.	No Sequential Patterns
 
 @TestCaseKey=CC-T21 
Scenario: ValidatePasswordComplexity

Given the user navigates to the login page
When the user clicks on the forgot password button and provided the "puseremail" to reset
And query the database to get the token and navigate to the page to reset the password
Then enter the newpassword and click on Submit Password and the system should display an error message:
  | newpassword         |   errormessage                                            |
     | Test123             |   Password must be more than 10 characters long.          |
     | Test123art5         |   Password must contain at least one number, one symbol, and one uppercase letter.|
     | test123art5         |   Password must contain at least one number, one symbol, and one uppercase letter.| 
     | Julie@123art5       |   Password must not contain any part of the user ID.|
     | Test@123qwertyuiop  |   Password must not contain common keyboard patterns.|
     | Test@1234567890     |   Password must not contain sequential patterns.|
     
     

   



