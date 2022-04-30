package Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.CommonMethods;

public class LoginPage extends CommonMethods {
//  Object repository
    @FindBy(id="txtUsername")
    public WebElement usernamebox;

    @FindBy(id="txtPassword")
    public WebElement passwordbox;

    @FindBy(id="btnLogin")
    public WebElement loginbtn;

//          defining page factory concept from selenium
    public LoginPage(){
//         it will initialize all the locator in this peticular class
        PageFactory.initElements(driver, this);
    }

    @FindBy(id = "spanMessage")
    public WebElement errorMsg;
}
