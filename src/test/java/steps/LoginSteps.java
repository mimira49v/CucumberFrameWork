package steps;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import utils.CommonMethods;


public class LoginSteps extends CommonMethods {
//  we can comment out "LoginPage login = new LoginPage();" cuz PageInitializers extends CommonMethods!!!

    @Then("admin user is successfully logged in")
    public void admin_user_is_successfully_logged_in() {
        System.out.println("test passed");
        tearDown();
    }

    @When("user enters valid ess username and password")
    public void user_enters_valid_ess_username_and_password() {
//      LoginPage login = new LoginPage();

//      WebElement usernamefield = driver.findElement(By.id("txtUsername"));
//      usernamefield.sendKeys(ConfigReader.getPropertyValue("username"));

        sendText(login.usernamebox, "tts12345");

//      WebElement passwordField = driver.findElement(By.name("txtPassword"));

        sendText(login.passwordbox, "Hum@nhrm123");
    }

    @Then("ess user is successfully logged in")
    public void ess_user_is_successfully_logged_in() {
        //leaving it for validation
        tearDown();
    }

    @When("user enters invalid username and password")
    public void user_enters_invalid_username_and_password() {
//      LoginPage login = new LoginPage();

//      WebElement usernamefield = driver.findElement(By.id("txtUsername"));
//      usernamefield.sendKeys(ConfigReader.getPropertyValue("username"));

        sendText(login.usernamebox, "tts12345");

//      WebElement passwordField = driver.findElement(By.name("txtPassword"));

        sendText(login.passwordbox, "Hum@nhrm");
    }

    @Then("user sees error message on the screen")
    public void user_sees_error_message_on_the_screen() {
//      homework - verify error message for this
        getErrorMsg(login.errorMsg);
//      tearDown();
    }
}