Feature: Create New Encounter
  Login to the Application and create new Encounters

  @TestCaseKey=CC-T21
  Scenario Outline: CreateNewEncounter
    Given the user logins in with valid useremail "<useremail>" and password "<password>" and clicks the login button
    When the user clicks on the New Participant button , create New Participant with StudyID "<StudyID>",EnrollmentDate "<EnrollmentDate>",PrimaryCarePhysician "<PrimaryCarePhysician>",Location "<Location>" and Save Participant
    And enter details to create New Encounter with EncounterStatus "<EncounterStatus>" and EncounterType "<EncounterType>"
    

    Examples: 
      | useremail                            | password     |StudyID        | EnrollmentDate        | PrimaryCarePhysician   | Location    |EncounterStatus   | EncounterType |
      | cocareproject2024+ncmtest1@gmail.com          | Eclipse$2024 |  123          |  08/08/2024           |   abc         | W           | In Progress      | Phone         |
