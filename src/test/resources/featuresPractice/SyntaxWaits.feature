Feature: Explicit Wait Practice on Syntax Projects

  @SyntaxWaits @explicitWait
  Scenario: Wait for alert to appear after clicking button
    Given I open the Syntax Projects explicit wait page
    When I click on the "Click me to open alert!" wait button
    Then I verify that an alert appears
    And I accept the alert

  @SyntaxWaits @explicitWait @textChange
  Scenario: Wait for text to change after clicking button
    Given I open the Syntax Projects explicit wait page
    When I click on the "Click me to change text!" wait button
    Then I verify that the text changes from "Syntax" to a different value
    And I verify the text change message is displayed

  @SyntaxWaits @explicitWait
  Scenario: Wait for button to be displayed after clicking trigger button
    Given I open the Syntax Projects explicit wait page
    When I click on "Click me to Display button!" button
    Then I verify that a new button is displayed
    And I verify the displayed button is clickable

  @SyntaxWaits @explicitWait
  Scenario: Wait for disabled button to become enabled
    Given I open the Syntax Projects explicit wait page
    When I verify that the "Button" is initially disabled
    And I click on "Click me to Enable button!" button
    Then I verify that the "Button" becomes enabled
    And I verify the enabled button is clickable

  @SyntaxWaits @explicitWait
  Scenario: Wait for checkbox to be checked after clicking button
    Given I open the Syntax Projects explicit wait page
    When I verify that the checkbox is initially unchecked
    And I click on "Click me to checked the checkbox!" button
    Then I verify that the checkbox becomes checked

  @SyntaxWaits @explicitWait @smoke
  Scenario: Verify all explicit wait elements are present on page load
    Given I open the Syntax Projects explicit wait page
    Then I verify that "Open an alert" section is displayed
    And I verify that "Change Text" section is displayed
    And I verify that "Display Button" section is displayed
    And I verify that "Enable Button" section is displayed
    And I verify that "Checkbox Checked base on click" section is displayed

  @SyntaxWaits @explicitWait
  Scenario: Wait for multiple elements to appear sequentially
    Given I open the Syntax Projects explicit wait page
    When I click on "Click me to Display button!" button
    And I wait for the button to be displayed
    And I click on "Click me to Enable button!" button
    And I wait for the button to be enabled
    Then I verify that both buttons are visible and enabled