Feature: View Tickets of a version
  Scenario: there no are tickets created for a version
    Given there are no tickets created for version ID 1
    When Trying to view the tickets for version ID 1
    Then no tickets for version 1 will be displayed

  Scenario: there are tickets created for a version
    Given there are tickets created for version ID 1
    When Trying to view the tickets for version ID 1
    Then all tickets for version 1 will be displayed


