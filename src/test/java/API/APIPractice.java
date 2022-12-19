package API;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.APIPayLoadConstants;
import utils.RawToJson;

import static io.restassured.RestAssured.given;

public class APIPractice {
        @Test(dataProvider="BooksData")
        public void addBook(String isbn,String aisle){
            RestAssured.baseURI="http://216.10.245.166";
            Response resp=given().
                    header("Content-Type","application/json").
                    body(APIPayLoadConstants.jsonPractice(isbn,aisle)).
                    when().
                    post("/Library/Addbook.php").
                    then().assertThat().statusCode(200).
                    extract().response();
            String response = resp.toString();
            JsonPath js= RawToJson.rawToJson(response);
            String id=js.get("ID");
            System.out.println(id);
            //deleteBOok
        }
        @DataProvider(name="BooksData")
        public Object[][]  getData() {
            return new Object[][] {{"ojfwty","9363"},{"cwetee","4253"},{"okmfet","533"}};
        }
    }
