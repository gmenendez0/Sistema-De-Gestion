Feature: Create Ticket

  Scenario: Successfully create a ticket with tittle X
    Given No exist Ticket with title "hello"
    When Trying to create a ticket with title "hello"
    Then Ticket tittle should be title "hello"

  Scenario: Create a ticket with tittle X and already exist a ticket with that tittle
    Given Already exist a ticket with tittle "hello"
    When Trying to create a ticket with title "hello"
    Then Error a ticket with the same name already exists

  Scenario: Create a ticket with no description
    Given No exist Ticket with title "x"
    When Trying to create a ticket with no description
    Then Error invalid Argument is displayed

  Scenario: Create a ticket with no Client
    Given No exist Ticket with title "x"
    When Trying to create a ticket with no client
    Then Error invalid Argument is displayed
