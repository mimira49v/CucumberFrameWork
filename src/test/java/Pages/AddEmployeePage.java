package Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.CommonMethods;

public class AddEmployeePage extends CommonMethods {

    @FindBy(id="firstName")
    public WebElement firstNameField;

    @FindBy(id="middleName")
    public WebElement middleNameField;

    @FindBy(id="lastName")
    public WebElement lastNameField;

    @FindBy(id="btnSave")
    public WebElement saveButton;

    @FindBy(xpath = "//div[@id='profile-pic']//child::h1")
    public WebElement emplVerify;

    public AddEmployeePage(){
        PageFactory.initElements(driver, this);
    }
}