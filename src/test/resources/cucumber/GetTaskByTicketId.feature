Feature: View tasks associated ticket
  Scenario: There are tasks associated with ticket X
    Given There are tasks associated with ticket ID 1
    When Trying to view tasks associated with ticket ID 1
    Then all tasks associated with ticket ID 1 are displayed

  Scenario: There are no tasks associated with ticket X
    Given Ticket with ID 2
    When Trying to view tasks associated with ticket ID 2
    Then No Tasks are displayed

