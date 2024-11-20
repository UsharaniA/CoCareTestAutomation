Feature: AccountLockedDuetoInactivity
User account will be locked due to inactivity for more than 30 days


  Scenario Outline: AccountLockedDuetoInactivity
   
    Given account inactive for more than 30 days for useremail "puseremail"
    Then the user logins in with valid useremail "puseremail" and password "ppassword" and clicks the login button
    Then the account should be locked and  the user should see an "Your account is locked due to inactivity for more than 30 days. Please contact support." message
    Then login as admin with "padminuseremail" and "padminpassword"
	  When the user clicks on the Admin button and goes to user page and click on User button
    And select the useremail "puseremail"
    And click on the Security Tab
    Then admin should see an warning message "<Warning1>" and "<Warning2>"
    And the admin unlock the account

Examples: 
  |  Warning1                                                     | Warning2         |
  |    This account is locked. Password change is disabled.       |This account is locked.| 

 


