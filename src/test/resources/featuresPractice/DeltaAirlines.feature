Feature: Extract text and attribute values from Delta Airlines
  Background:
    Given I open Delta Airlines homepage

  @New222
  Scenario: Validate placeholders and button text on Delta Airlines homepage
    When I extract the placeholder text from the deprature field
    And I extract the placeholder text from the destination field
    And I extract the text from the search button
    Then I validate the extracted values

  Scenario: Scroll using Action Class and JavaScript Executor
    Given I open Delta Airlines homepage
    When I scroll down using the Action Class
    And I scroll to a specific element using JavaScript Executor
    And I scroll to the bottom of the page using JavaScript
    Then I validate scrolling was successful

  Scenario: