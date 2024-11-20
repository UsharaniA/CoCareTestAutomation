Feature: AccountLockedDuetoFailedattempts
This user account is locked due to multiple failed login attempts (3 consecutive failures).

  Scenario Outline: AccountLockedDuetoFailedattempts
  	Given the account is active for "puseremail"
    When the user logins in with valid useremail "puseremail" and invalid password "<password>" and clicks the login button
    Then again the user logins in with valid useremail "puseremail" and invalid password "<password>" and clicks the login button
    Then again the user logins in with valid useremail "puseremail" and invalid password "<password>" and clicks the login button
    Then the account should be locked and  the user should see an "Your account is locked due to too many failed login attempts." message
		Then login as admin with "padminuseremail" and "padminpassword"
	  When the user clicks on the Admin button and goes to user page and click on User button
    And select the useremail "puseremail"
    And click on the Security Tab
    Then admin should see an warning message "<Warning1>" and "<Warning2>"
		And the admin unlock the account

    Examples: 
    | password      |  Warning1                                                  | Warning2         |
    | 1234          | This account is locked. Password change is disabled.       |This account is locked.| 

 