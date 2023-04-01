package API;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.APIConstants;
import utils.APIPayLoadConstants;
import utils.RawToJson;

import static io.restassured.RestAssured.given;
import static utils.RawToJson.rawToJson;

public class APIPractice {

        @Test(dataProvider="BooksData")
        public void addBook(String isbn,String aisle){
            RestAssured.baseURI = "http://216.10.245.166";
            Response resp = given().header("Content-Type","application/json").
 //                           body(payload.Addbook("adsfs","6464")).
                            when().post("/Library/Addbook.php").
                            then().assertThat().statusCode(200).extract().response();
        }
        @DataProvider(name="BooksData")
        public Object[][]  getData() {
            return new Object[][] {{"ojfwty","9363"},{"cwetee","4253"},{"okmfet","533"}};
        }
    }
