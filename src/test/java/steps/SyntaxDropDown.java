package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
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
        WebElement dropdownElement = getDriver().findElement(By.xpath("//select[@id='select-demo']"));
        dropdown = new Select(dropdownElement);
        dropdown.selectByVisibleText(option);
        System.out.println("Selected option: " + option);
    }

    @Then("I verify that {string} is selected")
    public void i_verify_that_is_selected(String expectedOption) {
        String selectedOption = dropdown.getFirstSelectedOption().getText();
        Assert.assertEquals(selectedOption, expectedOption, "Dropdown selection mismatch!");
        System.out.println("Verified selected option: " + selectedOption);
    }

    @And("I print all available options in the dropdown")
    public void i_print_all_available_options_in_the_dropdown() {
        List<WebElement> options = dropdown.getOptions();
        System.out.println("Available options in the dropdown:");
        for (WebElement option : options) {
            System.out.println(option.getText());
        }
    }

    @Given("I open the Syntax Projects input form locator page")
    public void i_open_the_syntax_projects_input_form_locator_page() {
        getDriver().get("https://syntaxprojects.com/input-form-locator.php");
    }

    @When("I enter {string} in the first name field")
    public void i_enter_in_the_first_name_field(String firstName) {
        waitForPresence(By.xpath("//input[@placeholder='First Name']"));
        WebElement firstNameField = getDriver().findElement(By.xpath("//input[@placeholder='First Name']"));
        sendText(firstNameField, firstName);
    }

    @When("I enter {string} in the last name field")
    public void i_enter_in_the_last_name_field(String lastName){
        waitForPresence(By.xpath("//input[@placeholder='Last Name']"));
        WebElement lastNameField = getDriver().findElement(By.xpath("//input[@placeholder='Last Name']"));
        sendText(lastNameField, lastName);
    }

    @When("I enter {string} in the email field")
    public void i_enter_in_the_email_field(String email){
        waitForPresence(By.xpath("//input[@placeholder='E-Mail Address']"));
        WebElement emailField = getDriver().findElement(By.xpath("//input[@placeholder='E-Mail Address']"));
        sendText(emailField, email);
    }

    @When("I click on the submit button")
    public void i_click_on_the_submit_button(){
        WebElement submitButton = getDriver().findElement(By.xpath("//button[contains(text(), 'Submit')]"));
        click(submitButton);
    }

    @Then("I verify that the form has been submitted successfully")
    public void i_verify_that_the_form_has_been_submitted_successfully(){
        waitForPresence(By.xpath("//p[contains(text(), 'Form has been submit successfully')]"));
        WebElement successMessage = getDriver().findElement(By.xpath("//p[contains(text(), 'Form has been submit successfully')]"));
        Assert.assertTrue(successMessage.isDisplayed(), "Success message not displayed");
    }
}
