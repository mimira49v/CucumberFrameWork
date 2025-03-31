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
        driver.get("https://syntaxprojects.com/basic-select-dropdown-demo.php");
    }

    @When("I select {string} from the dropdown")
    public void i_select_from_the_dropdown(String option) {
        WebElement dropdownElement = driver.findElement(By.xpath("//select[@id='select-demo']"));
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

}
