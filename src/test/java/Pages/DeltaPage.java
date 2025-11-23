package Pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.CommonMethods;

public class DeltaPage extends CommonMethods {

    @FindBy(xpath="(//span[@class='calenderDepartSpan'])[1]")
    public WebElement departElement;

    @FindBy(xpath = "//span[@class='calenderDepartSpan']")
    public WebElement departureField;

    @FindBy(xpath = "//span[@class='calenderReturnSpan calendar-placeholder']")
    public WebElement destinationField;

    @FindBy(xpath = "//h3[normalize-space()='Popular Topics:']")
    public WebElement  popular_topics;

    @FindBy(xpath = "//button[@id='btn-book-submit']")
    public WebElement searchButton;

    public DeltaPage(){
        PageFactory.initElements(getDriver(), this);
    }
}
