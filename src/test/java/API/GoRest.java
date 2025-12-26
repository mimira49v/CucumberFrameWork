package API;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

/**
 * GoRest API Test Class
 * Demonstrates POST request with authentication
 * API Documentation: https://gorest.co.in/
 */
public class GoRest {

    private static final String BASE_URI = "https://gorest.co.in/public/v2";
    private static final String BEARER_TOKEN = "5eac0aafb93831de97b2c47adc15c02a6e4545cef8eb8464fc93414a320f834d";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URI;
    }

    /**
     * Test Case: Create a new user using POST request
     * Practice: POST request with Bearer token authentication and JSON body
     */
    @Test
    public void testCreateUserWithPostRequest() {
        // Create unique email to avoid duplicates
        String uniqueEmail = "testuser" + System.currentTimeMillis() + "@example.com";

        // Build request body using JsonObject
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("name", "John Doe");
        requestBody.addProperty("email", uniqueEmail);
        requestBody.addProperty("gender", "male");
        requestBody.addProperty("status", "active");

        System.out.println("=== POST Request to Create User ===");
        System.out.println("Request Body: " + requestBody.toString());

        // Make POST request with authentication
        Response response = given()
                .header("Authorization", "Bearer " + BEARER_TOKEN)
                .header("Content-Type", "application/json")
                .body(requestBody.toString())
                .when()
                .post("/users");

        // Print response details
        System.out.println("\nStatus Code: " + response.getStatusCode());
        System.out.println("Response Body:");
        System.out.println(response.prettyPrint());

        // Assertions
        Assert.assertEquals(response.getStatusCode(), 201, "Status code should be 201 (Created)");

        // Parse response and validate created user
        JsonObject responseJson = JsonParser.parseString(response.asString()).getAsJsonObject();

        Assert.assertTrue(responseJson.has("id"), "Response should contain user ID");
        Assert.assertEquals(responseJson.get("name").getAsString(), "John Doe",
                "Name should match request");
        Assert.assertEquals(responseJson.get("email").getAsString(), uniqueEmail,
                "Email should match request");
        Assert.assertEquals(responseJson.get("gender").getAsString(), "male",
                "Gender should match request");
        Assert.assertEquals(responseJson.get("status").getAsString(), "active",
                "Status should match request");

        System.out.println("\nUser created successfully with ID: " +
                responseJson.get("id").getAsString());
    }
}
