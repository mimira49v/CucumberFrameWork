Feature: Automate dropdown selection on Syntax Projects

  @SyntaxDropDown
  Scenario: Select and validate a dropdown option
    Given I open the Syntax Projects dropdown page
    When I select "Tuesday" from the dropdown
    Then I verify that "Tuesday" is selected
    And I print all available options in the dropdown

  @SyntaxInputForm
  Scenario: Fill and submit input form on Syntax Projects
    Given I open the Syntax Projects input form locator page
    When I enter "John" in the first name field
    And I enter "Doe" in the last name field
    And I enter "john.doe@example.com" in the email field
    And I click on the submit button
    Then I verify that the form has been submitted successfully