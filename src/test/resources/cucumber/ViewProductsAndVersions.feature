Feature: View Products and Versions

  Scenario: No product versions registered
    Given no product versions are registered
    When Trying to view all products versions
    Then no product versions should be displayed

  Scenario: Viewing all versions of all products
  Given there are product and versions registered
  When Trying to view all products versions
  Then all versions of all registered products should be displayed

