package API;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

public class codingAssignment {

    public static WebDriver webDriver;
    @Before
    public void openBrowser(){
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();
        webDriver.get("https://codility-frontend-prod.s3.amazonaws.com/media/task_static/qa_login_page/9a83bda125cd7398f9f482a3d6d45ea4/static/attachments/reference_page.html");
    }
    @Test
    public void firstTest(){
        WebElement email = webDriver.findElement(By.id("email-input"));
        WebElement password = webDriver.findElement(By.id("password-input"));
        Assert.assertTrue(email.isDisplayed());
        Assert.assertTrue(password.isDisplayed());
    }
    @Test
    public void secondTest(){
        webDriver.findElement(By.id("email-input")).sendKeys("login@codility.com");
        webDriver.findElement(By.id("password-input")).sendKeys("password");
        webDriver.findElement(By.id("login-button")).click();
        String successMSG = webDriver.findElement(By.xpath("//div[@class='message success']")).getText();
        Assert.assertEquals(successMSG,"Welcome to Codility");
    }
    @Test
    public void thirdTest(){
        webDriver.findElement(By.id("email-input")).sendKeys("unknown@codility.com");
        webDriver.findElement(By.id("password-input")).sendKeys("password");
        webDriver.findElement(By.id("login-button")).click();
        String UnauthorizedMSG = webDriver.findElement(By.xpath("//div[@class='message error']")).getText();
        Assert.assertEquals(UnauthorizedMSG,"You shall not pass! Arr!");
    }
    @Test
    public void fourthTest(){
        webDriver.findElement(By.id("email-input")).sendKeys("unknowncodility.com");
        webDriver.findElement(By.id("password-input")).sendKeys("password");
        webDriver.findElement(By.id("login-button")).click();
        String UnauthorizedMSG = webDriver.findElement(By.xpath("//div[@class='validation error']")).getText();
        Assert.assertEquals(UnauthorizedMSG,"Enter a valid email");
    }
    @Test
    public void fifthTest(){
        webDriver.findElement(By.id("email-input")).sendKeys("unknown@codility.com");
        webDriver.findElement(By.id("password-input")).sendKeys("");
        webDriver.findElement(By.id("login-button")).click();
        String UnauthorizedMSG = webDriver.findElement(By.xpath("//div[@class='validation error']")).getText();
        Assert.assertEquals(UnauthorizedMSG,"Password is required");
    }
    @Test
    public void sixthTest(){
        webDriver.findElement(By.id("email-input")).sendKeys("");
        webDriver.findElement(By.id("password-input")).sendKeys("password");
        webDriver.findElement(By.id("login-button")).click();
        String UnauthorizedMSG = webDriver.findElement(By.xpath("//div[@class='validation error']")).getText();
        Assert.assertEquals(UnauthorizedMSG,"Email is required");
    }
    @After
    public void close(){
        webDriver.quit();
    }
}
