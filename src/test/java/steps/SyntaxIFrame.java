package steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import org.testng.Assert;
import utils.CommonMethods;

public class SyntaxIFrame extends CommonMethods{

    @Given("I open the Syntax Projects iframe page")
    public void i_open_the_syntax_projects_iframe_page() {
        getDriver().get("https://syntaxprojects.com/handle-iframe.php");
    }

    @When("I enter {string} in the topic field")
    public void i_enter_string_in_the_topic_field(String text_to_send) {
        switchToFrameByNameOrID("textfield-iframe");
        sendText(iframePage.topicField, text_to_send);
    }

    @And("I switch to the inner frame")
    public void i_switch_to_the_inner_frame() {
        switchToFrameByNameOrID("checkbox-iframe");
    }

    @And("I check the {string} checkbox")
    public void i_check_the_checkbox(String checkbox) {
        // Click the inner frame checkbox as specified by the parameter
        // The parameter 'checkbox' indicates which checkbox to click (in this case "Inner Frame Check box")
        iframePage.innerFrameCheckBox.click();
        Assert.assertTrue(iframePage.innerFrameCheckBox.isSelected(),
                         "Checkbox '" + checkbox + "' was not selected after clicking");
    }

    @And("I switch back to the dfault content")
    public void i_switch_back_to_the_dfault_content() {
        switchToDefaultContent();
    }
}
