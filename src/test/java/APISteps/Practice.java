package APISteps;

import API.PayLoadAddBook;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Practice {

    WebDriver driver;

    public void setup() {
    driver.get("https://google.com");
            driver.manage().window().maximize();

    // Get Title of the page
    String title = driver.getTitle();
    System.out.println("Page Title : " + title);

    String currentUrl = driver.getCurrentUrl();
    System.out.println(currentUrl);



    driver.close();
    driver.quit();


    }

    @Test(dataProvider="BooksData")
    public void addBook(String isbn, String aisle){
        RestAssured.baseURI = "http://sssssssss"; //need base uri
        //response
        Response resp = given().header("Content-Type", "application/json").
                            body(PayLoadAddBook.Addbook(isbn, aisle)).
                                when().post("/Library/Addbook.php").
                                    then().assertThat().statusCode(200).extract().response();
        System.out.println(resp);
    }
    @DataProvider(name = "BooksData")
    public Object[][] getData(){
        return new Object[][]{{"dfjafd", "dalfdka"},{"adfdaf","dfafdaf","fdadfa","dfadfdaf"}};
    }
}

