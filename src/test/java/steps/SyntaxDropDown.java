package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import utils.CommonMethods;

import java.util.List;

public class SyntaxDropDown extends CommonMethods {

    Select dropdown;

    @Given("I open the Syntax Projects dropdown page")
    public void i_open_the_syntax_projects_dropdown_page() {
        getDriver().get("https://syntaxprojects.com/basic-select-dropdown-demo.php");
    }

    @When("I select {string} from the dropdown")
    public void i_select_from_the_dropdown(String option) {
        dropdown = new Select(dropDownPage.selectDemoDropdown);
        dropdown.selectByVisibleText(option);
    }

    @Then("I verify that {string} is selected")
    public void i_verify_that_is_selected(String expectedOption) {
        String selectedOption = dropdown.getFirstSelectedOption().getText();
        Assert.assertEquals(selectedOption, expectedOption, "Dropdown selection mismatch!");
    }

    @And("I print all available options in the dropdown")
    public void i_print_all_available_options_in_the_dropdown() {
        List<WebElement> options = dropdown.getOptions();
        // Available options retrieved - can be used for validation if needed
        options.size();
    }

    @Given("I open the Syntax Projects input form locator page")
    public void i_open_the_syntax_projects_input_form_locator_page() {
        getDriver().get("https://syntaxprojects.com/input-form-locator.php");
    }

    @When("I enter {string} in the first name field")
    public void i_enter_in_the_first_name_field(String firstName) {
        waitForVisibility(dropDownPage.firstNameField);
        sendText(dropDownPage.firstNameField, firstName);
    }

    @When("I enter {string} in the last name field")
    public void i_enter_in_the_last_name_field(String lastName){
        waitForVisibility(dropDownPage.lastNameField);
        sendText(dropDownPage.lastNameField, lastName);
    }

    @When("I enter {string} in the email field")
    public void i_enter_in_the_email_field(String email){
        waitForVisibility(dropDownPage.emailField);
        sendText(dropDownPage.emailField, email);
    }

    @When("I click on the submit button")
    public void i_click_on_the_submit_button(){
        click(dropDownPage.submitButton);
    }

    @Then("I verify that the form has been submitted successfully")
    public void i_verify_that_the_form_has_been_submitted_successfully(){
        waitForVisibility(dropDownPage.successMessage);
        Assert.assertTrue(dropDownPage.successMessage.isDisplayed(), "Success message not displayed");
    }

    @Given("I open the Syntax Projects radio button demo page")
    public void i_open_the_syntax_projects_radio_button_demo_page() {
        getDriver().get("https://syntaxprojects.com/basic-radiobutton-demo.php");
    }
    @When("I select {string} radio button in the Radio Button Demo section")
    public void i_select_radio_button_in_the_radio_button_demo_section(String radioButton) {
        WebElement radioButtonElement = radioButton.equalsIgnoreCase("Male") 
            ? dropDownPage.maleRadioButton 
            : dropDownPage.femaleRadioButton;
        click(radioButtonElement);
    }
    @When("I click on {string} button")
    public void i_click_on_button_in_the_radio_button_demo_section(String button) {
        if (button.contains("Get Checked value")) {
            click(dropDownPage.getCheckedValueButton);
        } else if (button.contains("Get values")) {
            click(dropDownPage.getValuesButton);
        } else if (button.contains("Enable Buttons")) {
            click(dropDownPage.enableButtonsButton);
        }
    }

    @Then("I verify that the displayed value contains {string}")
    public void i_verify_that_the_displayed_value_contains(String expectedValue) {
        waitForVisibility(dropDownPage.displayedRadioValue);
        String actualValue = dropDownPage.displayedRadioValue.getText();
        Assert.assertTrue(actualValue.contains(expectedValue), 
            "Displayed value does not contain '" + expectedValue + "'. Actual value: " + actualValue);
    }

    @And("I verify that {string} radio button is not selected")
    public void i_verify_that_radio_button_is_not_selected(String option) {
        WebElement radioButton = option.equalsIgnoreCase("Male") 
            ? dropDownPage.maleRadioButton 
            : dropDownPage.femaleRadioButton;
        Assert.assertFalse(radioButton.isSelected(), 
            "Radio button '" + option + "' should not be selected but it is selected");
    }

    @When("I attempt to select {string} radio button and verify that it is not clickable")
    public void i_attempt_to_select_radio_button_in_the_disabled_radio_button_demo_section(String option) {
        WebElement radioButton = option.equalsIgnoreCase("Male") 
            ? dropDownPage.disabledMaleRadioButton 
            : dropDownPage.disabledFemaleRadioButton;
        Assert.assertFalse(radioButton.isEnabled(), 
            "Radio button '" + option + "' should not be enabled but it is enabled");
    }

    @Then("I verify that no radio button is selected in the Disabled Radio Button Demo section")
    public void i_verify_that_no_radio_button_is_selected_in_the_disabled_radio_button_demo_section() {
        Assert.assertFalse(dropDownPage.disabledMaleRadioButton.isSelected(), "Male radio button should not be selected in Disabled section");
        Assert.assertFalse(dropDownPage.disabledFemaleRadioButton.isSelected(), "Female radio button should not be selected in Disabled section");
    }
    
}
