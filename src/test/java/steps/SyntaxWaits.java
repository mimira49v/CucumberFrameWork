package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

import Pages.SyntaxWaitPage;
import org.testng.annotations.AfterMethod;
import utils.CommonMethods;
import org.openqa.selenium.Alert;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class SyntaxWaits extends CommonMethods {

    String url = "https://syntaxprojects.com/synchronization-explicit-wait.php";

    @Given("I open the Syntax Projects explicit wait page")
    public void i_open_the_syntax_projects_explicit_wait_page() {
        getDriver().get(url);
    }

    @When("I click on the {string} wait button")
    public void i_click_on_wait_button(String buttonText) {
        switch (buttonText) {
            case "Click me to open alert!":
                click(syntaxWaitPage.openAlertButton);
                break;
            case "Click me to change text!":
                click(syntaxWaitPage.changeTextButton);
                break;
            default:
                throw new IllegalArgumentException("Unknown button: " + buttonText);
        }
    }

    @Then("I verify that an alert appears")
    public void i_verify_that_an_alert_appears() {
        
        getWait().until(ExpectedConditions.alertIsPresent());
        Alert alert = getDriver().switchTo().alert();
        String alertText = alert.getText();

        Assert.assertNotNull(alertText, "Alert text should not be null");
        Assert.assertFalse(alertText.isEmpty(), "Alert text should not be empty");
    }

    @And("I accept the alert")
    public void i_accept_the_alert() {
        Alert alert = getDriver().switchTo().alert();
        alert.accept();
    }

    @Then("I verify that the text changes from {string} to a different value")
    public void i_verify_that_the_text_changes_from_syntax_to_a_different_value(String syntax){

        // Explicit wait for the text to change from "Syntax"
        getWait().until(ExpectedConditions.not(
                ExpectedConditions.textToBe(
                        By.xpath("//h2[@id='headingtext']"),
                        "Syntax"
                )
        ));

        // Get the new text after change
        String newText = syntaxWaitPage.textElement.getText();

        // Verify the text is no longer "Syntax"
        Assert.assertNotEquals(newText, "Syntax", "Text should have changed from 'Syntax'");
        Assert.assertFalse(newText.isEmpty(), "New text should not be empty");
    }

    @And("I verify the text change message is displayed")
    public void i_verify_the_text_change_message_is_displayed() {
        getWait().until(ExpectedConditions.visibilityOf(syntaxWaitPage.textChangeMessage));

    }
}