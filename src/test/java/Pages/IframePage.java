package Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.CommonMethods;

public class IframePage extends CommonMethods{

    @FindBy(xpath = "//input[@name='Topic']")
    public WebElement topicField;

    @FindBy(xpath = "//input[@type='checkbox']")
    public WebElement innerFrameCheckBox;

    @FindBy(xpath = "//input[@name='Topic']")
    public WebElement checkboxIframe;

    @FindBy(xpath = "//input[@type='checkbox']/following-sibling::text()")
    public WebElement innerFrameCheckBoxLabel;
    
    public IframePage() {
        PageFactory.initElements(getDriver(), this);
    
    }
}
