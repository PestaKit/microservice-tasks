Feature: Creation of Stages

  Background:
    Given there is a Tasks server

  Scenario: create a stage
    Given I have a task payload and a stage payload
    When I POST the stage to the /tasks/2/stages endpoint
    Then I receive a 201 status code for stage posting
    And I GET the endpoint /tasks/2/stages/1 and verify if it is not empty and that his name is 'stage one'
    And I POST a new Stage at /tasks/2/stages/1 and verify if the shift goes well
    And I DELETE the endpoint /tasks/2/stages/1 and verify if it is deleted
