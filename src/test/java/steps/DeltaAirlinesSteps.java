package steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.CommonMethods;
import java.time.Duration;

public class DeltaAirlinesSteps extends CommonMethods {

    String departurePlaceholder;
    String destinationPlaceholder;
    String searchButtonText;
    
    private JavascriptExecutor getJSExecutorLocal() {
        return (JavascriptExecutor) getDriver();
    }
    
    private WebDriverWait getWaitLocal() {
        return new WebDriverWait(getDriver(), Duration.ofSeconds(10));
    }


    @Given("I open Delta Airlines homepage")
    public void i_open_delta_airlines_homepage() {

    }

    @When("I extract the placeholder text from the deprature field")
    public void i_extract_the_placeholder_text_from_the_deprature_field() {



        // Wait for the departure field to be present
        deltaPage.departureField = getWaitLocal().until(ExpectedConditions.presenceOfElementLocated
                                        (By.xpath("//span[@class='calenderDepartSpan']")));

        // Extract the text from the element
        String departText = deltaPage.departElement.getText().trim();

        System.out.println("Extracted Text: " + departText);

        // Extract the placeholder attribute
        departurePlaceholder = deltaPage.departureField.getText();
        System.out.println("Departure Field Placeholder: " + departurePlaceholder);

    }
    @When("I extract the placeholder text from the destination field")
    public void i_extract_the_placeholder_text_from_the_destination_field() throws InterruptedException {

        // WebElement departureField = wait.until(ExpectedConditions.visibilityOf(deltaPage.departElement));
        /*
         WebElement destinationField = wait.until(ExpectedConditions.presenceOfElementLocated
                                                (By.xpath("//span[@class='calenderReturnSpan calendar-placeholder']")));
         */


        destinationPlaceholder = deltaPage.destinationField.getText();
        System.out.println("Destination Field Placeholder: " + destinationPlaceholder);

    }
    @When("I extract the text from the search button")
    public void i_extract_the_text_from_the_search_button() {
        deltaPage.searchButton.getText();
        System.out.println("Search Button Text: " + searchButtonText);
    }
    @Then("I validate the extracted values")
    public void i_validate_the_extracted_values() {
        Assert.assertEquals(departurePlaceholder, "Depart", "Departure field text mismatch!");
        Assert.assertEquals(destinationPlaceholder, "Return", "Return field text mismatch!");
    }

    @When("I scroll down using the Action Class")
    public void iScrollDownUsingTheActionClass() {
        Actions actions = new Actions(getDriver());
        actions.scrollByAmount(0, 500).perform(); // scroll down 500 pixels
        System.out.println("Scrolled down using action class");
        
    }

    @And("I scroll to a specific element using JavaScript Executor")
    public void iScrollToASpecificElementUsingJavaScriptExecutor() {
        WebElement element = getDriver().findElement(By.xpath("//a[@class='search-text']"));
        getJSExecutorLocal().executeScript("arguments[0].scrollIntoView(true);", element);
        element.sendKeys("flights");

    }

    @And("I scroll to the bottom of the page using JavaScript")
    public void iScrollToTheBottomOfThePageUsingJavaScript() {
        WebDriverWait wait = new WebDriverWait(getDriver(), Duration.ofSeconds(10));
        
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));

        WebElement element = getDriver().findElement(By.xpath("//h3[contains(text(), 'Popular Topics')]"));
        getJSExecutorLocal().executeScript("arguments[0].scrollIntoView(true);", element);

        System.out.println("Scrolled to 'Popular Topics' section using JavaScript Executor");
    }

    @Then("I validate scrolling was successful")
    public void iValidateScrollingWasSuccessful() {
        
        getJSExecutorLocal().executeScript("window.scrollTo(0, document.body.scrollHeight)");
        System.out.println("==== Successfully scrolled to the bottom of the page. ====");
    }
}
