Feature: Getting list of tasks

  Background:
    Given there is a Tasks server

  Scenario: get list of tasks
    Given I have 3 tasks payload
    When I GET the list of Tasks
    Then I receive a 200 status code for getting list of Tasks
    And the list contains the good tasks