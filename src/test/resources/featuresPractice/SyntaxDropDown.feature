Feature: Automate dropdown selection on Syntax Projects

  @SyntaxDropDown
  Scenario: Select and validate a dropdown option
    Given I open the Syntax Projects dropdown page
    When I select "Tuesday" from the dropdown
    Then I verify that "Tuesday" is selected
    And I print all available options in the dropdown

