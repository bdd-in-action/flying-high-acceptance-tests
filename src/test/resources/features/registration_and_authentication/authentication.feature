Feature: Authentication

  Registered Frequent Flyer members can access their account using their email and password

  @FHFF-1 @WIP
  Scenario: Trevor successfully logs on to the Frequent Flyer app
    Given Trevor has registered as a Frequent Flyer member
    Then he should be able to log on to the Frequent Flyer application
