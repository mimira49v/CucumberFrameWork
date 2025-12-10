package Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.CommonMethods;

public class DropDownPage extends CommonMethods {

    // Dropdown Demo Page Elements
    @FindBy(xpath = "//select[@id='select-demo']")
    public WebElement selectDemoDropdown;

    // Input Form Locator Page Elements
    @FindBy(xpath = "//input[@placeholder='First Name']")
    public WebElement firstNameField;

    @FindBy(xpath = "//input[@placeholder='Last Name']")
    public WebElement lastNameField;

    @FindBy(xpath = "//input[@placeholder='E-Mail Address']")
    public WebElement emailField;

    @FindBy(xpath = "//button[contains(text(), 'Submit')]")
    public WebElement submitButton;

    @FindBy(xpath = "//p[contains(text(), 'Form has been submit successfully')]")
    public WebElement successMessage;

    // Radio Button Demo Page Elements
    @FindBy(xpath = "//input[@type='radio' and @value='Male' and @name='optradio']")
    public WebElement maleRadioButton;

    @FindBy(xpath = "//input[@type='radio' and @value='Female' and @name='optradio']")
    public WebElement femaleRadioButton;

    @FindBy(xpath = "//button[contains(text(), 'Get Checked value')]")
    public WebElement getCheckedValueButton;

    @FindBy(xpath = "//p[@class='radiobutton']")
    public WebElement displayedRadioValue;

    // Disabled Radio Button Demo Section Elements
    @FindBy(xpath = "//input[@type='radio' and @value='gender-male' and @name='gender-identity']")
    public WebElement disabledMaleRadioButton;

    @FindBy(xpath = "//input[@type='radio' and @value='gender-female' and @name='gender-identity']")
    public WebElement disabledFemaleRadioButton;

    @FindBy(xpath = "//button[contains(text(), 'Enable Buttons')]")
    public WebElement enableButtonsButton;

    // Group Radio Buttons Demo Elements
    @FindBy(xpath = "//input[@type='radio' and @value='Male' and @name='gender']")
    public WebElement groupMaleRadioButton;

    @FindBy(xpath = "//input[@type='radio' and @value='Female' and @name='gender']")
    public WebElement groupFemaleRadioButton;

    @FindBy(xpath = "//input[@type='radio' and @value='0 - 5' and @name='ageGroup']")
    public WebElement ageGroup0To5;

    @FindBy(xpath = "//input[@type='radio' and @value='5 - 15' and @name='ageGroup']")
    public WebElement ageGroup5To15;

    @FindBy(xpath = "//input[@type='radio' and @value='15 - 50' and @name='ageGroup']")
    public WebElement ageGroup15To50;

    @FindBy(xpath = "//button[contains(text(), 'Get values')]")
    public WebElement getValuesButton;

    public DropDownPage() {
        PageFactory.initElements(getDriver(), this);
    }
}
