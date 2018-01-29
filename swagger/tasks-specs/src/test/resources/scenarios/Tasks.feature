Feature: Getting list of Tasks

  Background:
    Given there is a Tasks server
    Given I have 3 tasks

  Scenario: GET the list of tasks
    When I GET the list of Tasks
    Then the list of tasks has the good size
    And the list of tasks has the good values