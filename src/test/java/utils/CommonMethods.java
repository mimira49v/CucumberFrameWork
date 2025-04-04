package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import steps.PageInitializers;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class CommonMethods extends PageInitializers {
    public static WebDriver driver;

    public static WebDriver openBrowserAndLunchApplication() {
        ConfigReader.readProperties(Constants.CONFIGURATION_FILEPATH);
        String browser = ConfigReader.getPropertyValue("browser").toLowerCase();
        boolean isRemote = Boolean.parseBoolean(ConfigReader.getPropertyValue("isRemote"));

        if (isRemote) {
            driver = startRemoteDriver(browser);
        } else {
            switch (browser) {
                case "chrome":
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver();
                    break;
                case "firefox":
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                    break;
                case "headless-chrome":
                    WebDriverManager.chromedriver().setup();
                    ChromeOptions options = new ChromeOptions();
                    options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1080");
                    driver = new ChromeDriver(options);
                    break;
                default:
                    throw new RuntimeException("Invalid browser name: " + browser);
            }
        }

        driver.get(ConfigReader.getPropertyValue("delta_url"));
        driver.manage().timeouts().implicitlyWait(Constants.IMPLICIT_WAIT, TimeUnit.SECONDS);
        intializePageObjects();

        return driver;
    }

    public static WebDriver startRemoteDriver(String browserType) {
        String remoteUrl = ConfigReader.getPropertyValue("hubURL");
        MutableCapabilities capabilities;

        if (browserType.equalsIgnoreCase("chrome")) {
            ChromeOptions options = new ChromeOptions();
            options.setCapability("browserName", "chrome");
            capabilities = options;
            System.out.println("### Remote Test Execution on Chrome");
        } else if (browserType.equalsIgnoreCase("firefox")) {
            FirefoxOptions options = new FirefoxOptions();
            options.setCapability("browserName", "firefox");
            capabilities = options;
            System.out.println("### Remote Test Execution on Firefox");
        } else {
            throw new RuntimeException("Unsupported remote browser: " + browserType);
        }

        try {
            return new RemoteWebDriver(new URL(remoteUrl), capabilities);
        } catch (MalformedURLException e) {
            System.out.println("Malformed hub URL: " + remoteUrl);
            throw new RuntimeException(e);
        }
    }

    public static void sendText(WebElement element, String textToSend) {
        element.clear();
        element.sendKeys(textToSend);
    }

    public static WebDriverWait getWait() {
        return new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public static void waitForClickability(WebElement element) {
        getWait().until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void click(WebElement element) {
        waitForClickability(element);
        element.click();
    }

    public static JavascriptExecutor getJSExecutor() {
        return (JavascriptExecutor) driver;
    }

    public static void jsClick(WebElement element) {
        getJSExecutor().executeScript("arguments[0].click();", element);
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
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    public static void verifyErrorMsg(WebElement element) {
        System.out.println(element.isDisplayed());
    }

    public static void getErrorMsg(WebElement element) {
        System.out.println(element.getText());
    }

    public static void waitForVisibility(WebElement element) {
        getWait().until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForInvisibility(WebElement element) {
        getWait().until(ExpectedConditions.invisibilityOf(element));
    }

    public static void waitForPresence(By locator) {
        getWait().until(ExpectedConditions.presenceOfElementLocated(locator));
    }

    public static void selectByVisibleText(WebElement dropdown, String text) {
        new Select(dropdown).selectByVisibleText(text);
    }

    public static void selectByIndex(WebElement dropdown, int index) {
        new Select(dropdown).selectByIndex(index);
    }

    public static void selectByValue(WebElement dropdown, String value) {
        new Select(dropdown).selectByValue(value);
    }

    public void switchToFrameByNameOrID(String nameOrId) {
        try {
            driver.switchTo().frame(nameOrId);
            System.out.println("Switched to frame with name or ID: " + nameOrId);
        } catch (Exception e) {
            System.out.println("Unable to switch to frame with name or ID: " + nameOrId);
            e.printStackTrace();
        }
    }

    public void switchToFrameByElement(WebElement frameElement) {
        try {
            driver.switchTo().frame(frameElement);
            System.out.println("Switched to frame using WebElement.");
        } catch (Exception e) {
            System.out.println("Unable to switch to frame using WebElement.");
            e.printStackTrace();
        }
    }

    public void switchToDefaultContent() {
        try {
            driver.switchTo().defaultContent();
            System.out.println("Switched back to the default content.");
        } catch (Exception e) {
            System.out.println("Unable to switch back to the default content.");
            e.printStackTrace();
        }
    }
}
