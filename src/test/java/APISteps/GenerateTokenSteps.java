package APISteps;

import io.cucumber.java.en.Given;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import utils.APIConstants;
import static io.restassured.RestAssured.given;

public class GenerateTokenSteps {
//  Don't need it cuz we are passing APIConstants.GENERATE_TOKEN_URI on line 25.
//  String baseURI = RestAssured.baseURI = "http://hrm.syntaxtechs.net/syntaxapi/api";
    public static String token;

    @Given("a JWT is generated")
    public void a_jwt_is_generated() {
        RequestSpecification request = given().header(APIConstants.HEADER_CONTENT_TYPE, APIConstants.HEADER_CONTENT_TYPE_VALUE).
                body("{\n" +
                        "    \"username\" : \"admin\",\n" +
                        "    \"password\": \"password123\"   \n" +
        "}");


        Response response = request.when().post(APIConstants.GENERATE_TOKEN_URI);

        // making sure everything is good to go before extracting the token
        Assert.assertEquals(response.getStatusCode(), 200, "Unexpected status code!");
        Assert.assertTrue(response.jsonPath().getString("token") != null, "Token is null!");

        // in case of wrong status code or token is null
        if (response.getStatusCode() != 200 || response.jsonPath().getString("token") == null) {
            throw new RuntimeException("Failed to generate token: " + response.getBody().asString());
        }

        token = "Bearer " + response.jsonPath().getString("token");
        System.out.println(token);
    }
}
//  decrypting the token  //
