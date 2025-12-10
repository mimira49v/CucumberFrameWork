Feature: iframe Practice on Syntax Projects

@SyntaxIFrame @iframe 
Scenario: Complete form submission workflow with iframe interaction
    Given I open the Syntax Projects iframe page
    When I enter "Test Automation Best Practices" in the topic field
    And I switch to the inner frame
    And I check the "Inner Frame Check box" checkbox
    And I switch back to the dfault content
    And I select "Dog" from the animals dropdown
    Then I verify that the topic filed contains "Test Automation Best Practices"
    And I verify that the animals dropdown displays "Baby Cat"
    And I verify that the checkbox in the inner frame is checked