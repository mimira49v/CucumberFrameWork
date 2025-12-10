package Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.CommonMethods;

public class SyntaxWaitPage extends CommonMethods {
    @FindBy(xpath = "//button[@id='show_alert']")
    public WebElement openAlertButton;

    @FindBy(xpath = "//button[@id='changetext_button']")
    public WebElement changeTextButton;

    @FindBy(xpath = "//h2[@id='headingtext']")
    public WebElement textElement;

    // Reuses headingtext element to verify text change is displayed
    @FindBy(xpath = "//h2[@id='headingtext']")
    public WebElement textChangeMessage;

    public SyntaxWaitPage() {
        PageFactory.initElements(getDriver(), this);
    }
}
