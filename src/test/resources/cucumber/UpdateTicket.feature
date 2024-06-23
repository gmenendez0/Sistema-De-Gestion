Feature: Update Ticket

  Scenario: Successful ticket status change
    Given Ticket with status "Nuevo"
    When Trying to change the status to "Cerrado"
    Then Ticket status is "Cerrado"

  Scenario:Successful ticket description change
    Given Ticket with description "hello"
    When Trying to change the description  to "World"
    Then the ticket description is "World"

  Scenario: Invalid ticket status change
    Given Ticket with status "Nuevo"
    When Trying to change the status to "X"
    Then Error invalid Argument is displayed