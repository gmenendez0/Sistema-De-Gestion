Feature: Create Ticket

  Scenario: Successfully create a ticket with tittle X
    Given No exist Ticket with title "hello"
    When Trying to create a ticket with title "hello"
    Then Ticket tittle should be title "hello"

  Scenario: Create a ticket with no Version
    Given No exist Ticket with title "x"
    When Trying to create a ticket with no version
    Then Error invalid Argument is displayed

  Scenario: Create a ticket with no Client
    Given No exist Ticket with title "x"
    When Trying to create a ticket with no client
    Then Error invalid Argument is displayed
