package API;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.APIConstants;

import static io.restassured.RestAssured.given;

public class JWT {
    public static String JWT = "";

    public static String JWT_GENERATED () {
        RequestSpecification request =
                given().
                header(APIConstants.HEADER_CONTENT_TYPE, APIConstants.HEADER_CONTENT_TYPE_VALUE).
                body("{\n" +
                        "    \"email\" : \"batchtwelve@test.com\",\n" +
                        "    \"password\": \"Test@123\"   \n" +
                        "}");
        Response response = request.when().post(APIConstants.GENERATE_TOKEN_URI);
         JWT = "Bearer " + response.jsonPath().getString("token");

        return  JWT;
    }
}
