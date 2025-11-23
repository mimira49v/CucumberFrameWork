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


  @SyntaxRadioButtons @smoke
  Scenario: Select Male radio button and verify displayed value
    Given I open the Syntax Projects radio button demo page
    When I select "Male" radio button in the Radio Button Demo section
    And I click on "Get Checked value" button
    Then I verify that the displayed value contains "Male"

  @SyntaxRadioButtons @smoke
  Scenario: Select Female radio button and verify displayed value
    Given I open the Syntax Projects radio button demo page
    When I select "Female" radio button in the Radio Button Demo section
    And I click on "Get Checked value" button
    Then I verify that the displayed value contains "Female"

  @SyntaxRadioButtons @radioChange
  Scenario: Verify radio button selection can be changed from Male to Female
    Given I open the Syntax Projects radio button demo page
    When I select "Male" radio button in the Radio Button Demo section
    And I click on "Get Checked value" button
    Then I verify that the displayed value contains "Male"
    When I select "Female" radio button in the Radio Button Demo section
    And I click on "Get Checked value" button
    Then I verify that the displayed value contains "Female"
    And I verify that "Male" radio button is not selected

  @SyntaxRadioButtons @disabledRadioButtons
  Scenario: Verify disabled radio buttons cannot be selected
    Given I open the Syntax Projects radio button demo page
    When I attempt to select "Male" radio button and verify that it is not clickable
    And I verify that no radio button is selected in the Disabled Radio Button Demo section

  @SyntaxRadioButtons
  Scenario: Enable disabled radio buttons and verify functionality
    Given I open the Syntax Projects radio button demo page
    When I click on "Enable Buttons" button in the Disabled Radio Button Demo section
    Then I verify that the radio buttons are enabled
    When I select "Male" radio button in the Disabled Radio Button Demo section
    Then I verify that "Male" radio button is selected
    And I verify that "Female" radio button is not selected

  @SyntaxRadioButtons @smoke
  Scenario: Select options from Group Radio Buttons and verify displayed values
    Given I open the Syntax Projects radio button demo page
    When I select "Male" from the Sex group in Group Radio Buttons Demo
    And I select "15 to 50" from the Age Group in Group Radio Buttons Demo
    And I click on "Get values" button in Group Radio Buttons Demo
    Then I verify that the displayed values contain "Male"
    And I verify that the displayed values contain "15 to 50"

  @SyntaxRadioButtons
  Scenario: Verify only one option can be selected per group in Group Radio Buttons
    Given I open the Syntax Projects radio button demo page
    When I select "Male" from the Sex group in Group Radio Buttons Demo
    Then I verify that "Male" is selected in the Sex group
    When I select "Female" from the Sex group in Group Radio Buttons Demo
    Then I verify that "Female" is selected in the Sex group
    And I verify that "Male" is not selected in the Sex group

  @SyntaxRadioButtons
  Scenario: Select all age group options sequentially and verify behavior
    Given I open the Syntax Projects radio button demo page
    When I select "0 to 5" from the Age Group in Group Radio Buttons Demo
    And I click on "Get values" button in Group Radio Buttons Demo
    Then I verify that the displayed values contain "0 to 5"
    When I select "5 to 15" from the Age Group in Group Radio Buttons Demo
    And I click on "Get values" button in Group Radio Buttons Demo
    Then I verify that the displayed values contain "5 to 15"
    And I verify that the displayed values do not contain "0 to 5"
    When I select "15 to 50" from the Age Group in Group Radio Buttons Demo
    And I click on "Get values" button in Group Radio Buttons Demo
    Then I verify that the displayed values contain "15 to 50"
    And I verify that the displayed values do not contain "5 to 15"

  @SyntaxRadioButtons
  Scenario: Verify Get values button displays message when no selection is made
    Given I open the Syntax Projects radio button demo page
    When I click on "Get values" button in Group Radio Buttons Demo without selecting any option
    Then I verify that an appropriate message is displayed indicating no selection

