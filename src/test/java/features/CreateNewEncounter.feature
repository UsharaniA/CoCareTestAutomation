Feature: Create New Encounter
  Login to the Application and create new Encounters

  @TestCaseKey=CC-T21
  Scenario: CreateNewEncounter
  Given the user logins in with valid useremail "puseremail" and password "ppassword" and clicks the login button
    When the user clicks on the New Participant button , create New Participant with StudyID "pStudyID",EnrollmentDate "pEnrollmentDate",PrimaryCarePhysician "pPrimaryCarePhysician",Location "pLocation" and Save Participant
    And enter details to create New Encounter with EncounterStatus "pEncounterStatus" and EncounterType "pEncounterType"
    Then update Questionnaire for Promis
    Then update Questionnaire for Alcohol
    Then update Questionnaire for Drug
    Then update Questionnaire for PHQ
    Then update Questionnaire for GAD
    Then update Questionnaire for Overdose with EnrollmentDate "pEnrollmentDate" ,OverdoseDate "pOverdoseDate" , OverdoseTreatment "pOverdoseTreatment" ,naloxone "pnaloxone"
    Then update Questionnaire for RxMeds
    Then update Questionnaire for Contigency Management using "pContingency"
    Then update Questionnaire for UDS with UDSDate "pEnrollmentDate" , UrineDrugTest "pUrineDrugTest" ,UDSResults "pUDSResults"
    
    