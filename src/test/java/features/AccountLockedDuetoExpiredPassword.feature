Feature: AccountLockedDuetoExpiredPassword
A user is required to reset their password before the password expiry else the account will be locked and the admin need to unlock the account.

  Scenario Outline: AccountLockedDuetoExpiredPassword
    Given the account password expired for useremail "puseremail"
    Then the user logins in with valid useremail "puseremail" and password "ppassword" and clicks the login button
     Then the account should be locked and  the user should see an "Your password has expired." message
		Then login as admin with "padminuseremail" and "padminpassword"
	  When the user clicks on the Admin button and goes to user page and click on User button
    And select the useremail "puseremail"
    And click on the Security Tab
    Then admin should see an warning message "<Warning1>" and "<Warning2>"
    Then reset the account for useremail "puseremail"
 Examples:
 |  Warning1                                                 | Warning2|
 |Password change is mandatory as per the system requirements.| Unlocking Not Available: The account is active, but a password reset is required.|
