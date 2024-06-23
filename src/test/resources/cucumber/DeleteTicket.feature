Feature: Delete Ticket

  Scenario: Successful deletion of a ticket by ID
    Given Ticket with ID 1
    When Trying to delete ticket with ID 1
    Then Ticked deleted with ID 1

  Scenario: Failure deletion of a ticket by ID
    Given Ticket with ID 1
    When Trying to delete ticket with ID 123
    Then Ticked not deleted with ID 1
