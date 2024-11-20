Feature: ValidatePasswordResetAttempt 
Validate the enforcement of Password Reset Attempt Limits.


  Scenario Outline: ValidatePasswordResetAttempt
Given the user navigates to the login page
When the user clicks on the forgot password button and provided the "puseremail" to reset
And again the user clicks on the forgot password button and provided the "puseremail" to reset
And again the user clicks on the forgot password button and provided the "puseremail" to reset
And again the user clicks on the forgot password button and provided the "puseremail" to reset
Then the user should see an "Too many password reset attempts.Please wait until tomorrow to try again." message
Then login as admin with "padminuseremail" and "padminpassword"
When the user clicks on the Admin button and goes to user page and click on User button
And select the useremail "puseremail"
And click on the Security Tab
Then admin should see an warning message "<Warning1>" and "<Warning2>"
And the admin unlock the account

    Examples: 
  |  Warning1                                                     | Warning2         |
  |    This account is locked. Password change is disabled.       |This account is locked.| 


