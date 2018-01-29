Feature: Getting list of Stages

  Background:
    Given there is a Tasks server
    Given I have 3 Stages on a Task

  Scenario: GET the list of Stages of a Task
    When I GET the list of stages with /tasks/numTask/stages endpoint
    Then the list of Stages has the good size