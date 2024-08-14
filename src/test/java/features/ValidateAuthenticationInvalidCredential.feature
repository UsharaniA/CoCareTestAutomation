Feature: User login Authetication
  Access Cocare Application and validate the Password creation rules.

  @TestCaseKey=CC-T21
  Scenario Outline: ValidateAuthenticationInvalidCredential
   Given the user logins in with valid useremail "puseremail" and password "ppassword" and clicks the login button
    When the user clicks on the Admin button and goes to user page and click on User button
    And select the useremail "<useremail>"
    And click on the Security Tab
    Then enter the newpassword and click on Update Password and the system should display an error message:
      | newpassword | errormessage                                              |
      | Test123     | Password must be more than 10 characters long.            |
      | Test123art5 | Password must contain at least one number and one symbol. |

    Examples: 
      | useremail                            | 
      | cocareproject2024@gmail.com | 
