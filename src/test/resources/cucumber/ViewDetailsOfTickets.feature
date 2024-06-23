Feature: View Products and Versions
  Scenario: Viewing details of non-existing ticket
    Given Ticket with ID 1
    When Trying to view details of ticket ID 2
    Then The user is informed that ticket 2 does not exist

  Scenario: Viewing details of existing ticket
    Given Ticket with ID 1
    When Trying to view details of ticket ID 1
    Then The details of ticket ID 1 should be displayed

