Feature: ValidatePasswordExpiryWarning
Warn users of upcoming password expiration and prompt them to update their password.


  Scenario: ValidatePasswordExpiryWarning
    Given the account is active for "puseremail"
    Then the user logins in with valid useremail "puseremail" and password "ppassword" and clicks the login button
    Then the user should see an warning message "Your password will expire in" 
		