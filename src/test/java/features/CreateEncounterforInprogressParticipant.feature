Feature: CreateEncounterforInprogressParticipant
Log in to the application and attempt to create an encounter for a participant who already has open encounters.


  Scenario: CreateEncounterforInprogressParticipant
  Given the user logins in with valid useremail "puseremail" and password "ppassword" and clicks the login button
    When the user clicks on the My Participant button
    And select the registry id "pregistryid"
    Then if any encounter is inprogress ,a new encounter should not be created
    