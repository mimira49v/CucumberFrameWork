Feature: Adding the employees in HRMS Aplication
  @regression
  Scenario: Adding one employee from feature file
    # Given user is navigated to HRMS application
    When user enters valid admin credentials
    And user clicks on login button
    Then admin user is successfully logged in
    When user clicks on PIM option
    And user clicks on add employee option
    And user enters firstname middlename and lastname

    Scenario: Adding one employee using cucumber feature file
      When user enters valid admin credentials
      And user clicks on login button
      Then admin user is successfully logged in
      When user clicks on PIM option
      And user clicks on add employee option
      And user enters "zuhoor" "Mujeen" and "Silvia"
      And user clicks on save button
      Then employee added successfully

    @123
    Scenario Outline: Adding multiple employees
      And user enters "<firstName>" "<middleName>" and "<lastName>"
      And user clicks on save button
      Then employee added successfully
      Examples:
      |firstName |middleName|lastName|
      |asel      |MS        |tolga   |
      |Yazgul    |MS        |kishan  |
      |tarik     |MS        |mujeeb  |
      |nassir    |MS        |volkan  |

  @test @datatable
 Scenario: Add employee using data table
    When user provides multiple employee data and verify they are added
    |firstName|middleName|lastName|
    |asel      |MS        |tolga   |
    |Yazgul    |MS        |kishan  |
    |tarik     |MS        |mujeeb  |
    |nassir    |MS        |volkan  |

