Feature: Creation of tasks

  Background:
    Given there is a Tasks server
    Given I have a Task
    Given I have a Task payload
    Given I have a Stage Payload

  Scenario: create a Task
    When I POST a correct Task payload to the /tasks endpoint
    Then I receive a 201 status code

  Scenario: create a Task with an already given name
    When I POST a correct Task payload but with already used name to the /tasks endpoint
    Then I receive a 409 status code

  Scenario: GET an existing Task
    When I GET an existing Task with  /tasks/numTask endpoint
    Then I receive a 200 status code

  Scenario: GET a non-existent Task
    When I GET an non-existent Task with  /tasks/numTask endpoint
    Then I receive a 404 status code

  Scenario: DELETE an existing Task
    When I DELETE an existent Task in /tasks/numTask endpoint
    Then I receive a 201 status code

  Scenario: DELETE a non-existent Task
    When I DELETE a non-existent Task in /tasks/numTask endpoint
    Then I receive a 404 status code

  Scenario: GET a deleted Task
    When I GET a deleted Task with  /tasks/numTask endpoint
    Then I receive a 404 status code
