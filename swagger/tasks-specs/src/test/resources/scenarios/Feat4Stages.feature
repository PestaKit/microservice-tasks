Feature: getting Stages

  Background:
    Given there is a Tasks server

  Scenario: get list of stages
    Given I have 3 Stages on a Task
    When I GET the list of stages with /tasks/1/stages endpoint
    Then I receive a 200 status code for getting Stages
    And the list has the good size