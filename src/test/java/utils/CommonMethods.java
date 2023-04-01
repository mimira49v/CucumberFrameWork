package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;       // added this line 28 it's not a part of the original framework
import steps.PageInitializers;
import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static java.awt.SystemColor.text;

public class CommonMethods extends PageInitializers {

    public static WebDriver driver;

    public void openBrowserAndLunchApplication() {
        ConfigReader.readProperties(Constants.CONFIGURATION_FILEPATH);
        switch (ConfigReader.getPropertyValue("browser")) {
            case "chrome":

                ChromeOptions ChromeOptions = new ChromeOptions();
                ChromeOptions.setHeadless(true); // prevents from opening chrome runs the tests without it
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver(ChromeOptions);
                break;
            case "firefox":
 /*
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        break;
                */
            default:
                throw new RuntimeException("Invalid browser name");
        }
        driver.get(ConfigReader.getPropertyValue("url"));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Constants.IMPLICIT_WAIT, TimeUnit.SECONDS);
        intializePageObjects();
    }

    public static void sendText(WebElement element, String textToSend) {
        element.clear();
        element.sendKeys(textToSend);
    }

    public static WebDriverWait getWait() {
        WebDriverWait wait = new WebDriverWait(driver, Constants.EXPLICIT_WAIT);
        return wait;
    }

    public static void Alert_handling(WebElement element) {
        element = driver.findElement(By.id("alert"));
        driver.switchTo().alert().dismiss();
    }

    public static void waitForClickability(WebElement element) {
        getWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void click(WebElement element) {
        waitForClickability(element);
        element.click();
    }

    public static JavascriptExecutor getJSExecutor() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        return js;
    }

    public static void jsClick(WebElement element) {
        getJSExecutor().executeScript("arguments[0].click;", element);
    }

    public static byte[] takeScreenshot(String fileName) {
        TakesScreenshot ts = (TakesScreenshot) driver;
        byte[] picBytes = ts.getScreenshotAs(OutputType.BYTES);
        File sourceFile = ts.getScreenshotAs(OutputType.FILE);
        try {
            FileUtils.copyFile(sourceFile, new File(Constants.SCREENSHOT_FILEPATH + fileName + " " + getTimeStamp("yyyy-MM-dd-HH-mm-ss") + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return picBytes;
    }

    public static String getTimeStamp(String pattern) {
        Date date = new Date();
//          to format the date according to our choice we want to implement in this function
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }
    public WebElement getElementBasedOnText (String text){
        return driver.findElement(By.xpath("//[contains(text(), '"+text+"']"));
    }

    public static void tearDown() {
        driver.quit();
    }

    public static void verifyErrorMsg(WebElement element) {
        System.out.println(element.isDisplayed());
    }

    public static void getErrorMsg(WebElement element) {
        System.out.println(element.getText());
    }

    public static void verifyLinkActive(String linkUrl) {

        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.get(linkUrl);

        List<WebElement> links = driver.findElements(By.tagName("a"));

        System.out.println("Total links are " + links.size());

        for (int i = 0; i < links.size(); i++) {

            WebElement ele = links.get(i);

            String url = ele.getAttribute("href");

            verifyLinkActive(url);

        }
        try {
            URL url = new URL(linkUrl);

            HttpURLConnection httpURLConnect = (HttpURLConnection) url.openConnection();

            httpURLConnect.setConnectTimeout(3000);

            httpURLConnect.connect();

            if (httpURLConnect.getResponseCode() == 200) {
                System.out.println(linkUrl + " - " + httpURLConnect.getResponseMessage());
            }
            if (httpURLConnect.getResponseCode() == HttpURLConnection.HTTP_NOT_FOUND) {
                System.out.println(linkUrl + " - " + httpURLConnect.getResponseMessage() + " - " + HttpURLConnection.HTTP_NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
