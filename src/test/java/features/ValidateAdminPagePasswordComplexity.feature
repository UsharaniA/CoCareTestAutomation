Feature: ValidateAdminPagePasswordComplexity
  Access Cocare Application and validate the Password creation rules.

  @TestCaseKey=CC-T21
  Scenario: ValidateAdminPagePasswordComplexity
    Given the user logins in with valid useremail "padminuseremail" and password "padminpassword" and clicks the login button
    When the user clicks on the Admin button and goes to user page and click on User button
    And select the useremail "puseremail"
    And click on the Security Tab
    Then enter the newpassword and click on Update Password and the system should display an error message:
     | newpassword         |   errormessage                                            |
     | Test123             |   Password must be more than 10 characters long.          |
     | Test123art5         |   Password must contain at least one number, one symbol, and one uppercase letter.|
     | test123art5         |   Password must contain at least one number, one symbol, and one uppercase letter.| 
     | Admin@123art5       |   Password must not contain any part of the user ID.|
     | Test@123qwertyuiop  |   Password must not contain common keyboard patterns.|
     | Test@1234567890     |   Password must not contain sequential patterns.|
     


