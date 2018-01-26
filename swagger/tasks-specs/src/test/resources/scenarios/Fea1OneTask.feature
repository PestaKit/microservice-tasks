Feature: Creation of tasks

  Background:
    Given there is a Tasks server

  Scenario: create a task
    Given I have a task payload
    When I POST it to the /tasks endpoint
    Then I receive a 201 status code
    And I GET the endpoint /tasks/1 and  verify if it is not empty and that is name is 'task one'
    And I DELETE the endpoint /tasks/1 and verify if it is deleted