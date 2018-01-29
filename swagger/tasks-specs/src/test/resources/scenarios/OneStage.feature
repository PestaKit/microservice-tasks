Feature: Creation of stages

  Background:
    Given there is a Tasks server
    Given I have a task
    Given I have 3 Stages payload

  Scenario: create a Stage
    When I POST a correct stage to the /tasks/numTask/stages endpoint
    Then I receive a 201 status code

  Scenario: create a Stage with an already given name
    When I POST a correct stage but with already used name to the /tasks/numTask/stages endpoint
    Then I receive a 409 status code

  Scenario: create two Stages at same position
    When I POST two Stages at the same position
    Then the position of first Stage has been switched forward

  Scenario: GET an existing Stage
    When I GET an existing Stage at /tasks/numTask/stages/position endpoint
    Then I receive a 200 status code

  Scenario: GET an existing Stage at non-existent Task
    When I GET an non-existent Stage at existing Task /tasks/numTask/stages/position endpoint
    Then I receive a 404 status code

  Scenario: GET a non-existent Stage at existing Task
    When I GET an existing Stage at a non-existent Task /tasks/numTask/stages/position endpoint
    Then I receive a 404 status code

  Scenario: DELETE an existing Stage
    When I DELETE an existing Stage /tasks/numTask/stages/position endpoint
    Then I receive a 201 status code

  Scenario: DELETE a non-existing Stage
    When I DELETE a non-existent Stage /tasks/numTask/stages/position endpoint
    Then I receive a 404 status code