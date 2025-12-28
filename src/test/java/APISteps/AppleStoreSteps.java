package APISteps;

import io.cucumber.java.PendingException;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import utils.APIPayLoadConstants;
import utils.TestDataReader;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class AppleStoreSteps {

    // Shared state - accessible across all step methods
    public static String baseUrl;
    public static String bearerToken;
    protected static RequestSpecification requestSpec;

    // Instance variables for current request/response
    private RequestSpecification request;
    private Response response;

    public static String customerId;
    public String email;
    private Map<String, String> currentCustomerData;

    // Background Step 1: Set base URL
    @Given("the Apple Store API is available at {string}")
    public void the_apple_store_api_is_available_at(String url) {
        baseUrl = url;
        RestAssured.baseURI = url;
        System.out.println("Apple Store API URL set to: " + url);
    }

    // Background Step 2: Prepare API credentials
    @And("I have valid Apple Store API credentials")
    public void i_have_valid_apple_store_api_credentials() {
        // Token from GoRest (https://gorest.co.in/)
        String rawToken = "5eac0aafb93831de97b2c47adc15c02a6e4545cef8eb8464fc93414a320f834d";
        bearerToken = "Bearer " + rawToken;

        requestSpec = new RequestSpecBuilder()
                .setBaseUri(baseUrl)
                .addHeader("Authorization" , bearerToken)
                .setContentType(ContentType.JSON)
                .build();

        System.out.println("Apple Store API credentials loaded");

        // Optional: Verify token is valid by making a test call
        Response testResponse = given()
            .spec(requestSpec)
            .when()
            .get(baseUrl + "/users?page=1&per_page=1");

        if (testResponse.getStatusCode() == 401) {
            throw new RuntimeException("Invalid API token! Please update the token from https://gorest.co.in/");
        }

        System.out.println("Token validated successfully (Status: " + testResponse.getStatusCode() + ")");
    }

    @Given("I want to create a new Apple customer account")
    public void i_want_to_create_a_new_apple_customer_account() {
        currentCustomerData = TestDataReader.getDataFromJson(
                "appleCustomers.json",
                "customers",
                "id",
                "default"
        );
        String uniqueEmail = TestDataReader.generateUniqueEmail(
                currentCustomerData.get("name"), "apple.com");

        currentCustomerData.put("email", uniqueEmail);

        System.out.println("Preparing customer creation request for: " + currentCustomerData.get("name"));
        System.out.println("Unique email generated: " + uniqueEmail);

        String payload = APIPayLoadConstants.createAppleCustomerPayload(currentCustomerData);

        request = given()
                .headers(
                        "Authorization", bearerToken,
                        "Content-Type", "application/json"
                )
                .body(payload);

        JSONObject prettyPayload = new JSONObject(payload);

        System.out.println("Request payload: " + prettyPayload.toString(2));
    }

    @When("I submit a POST request to {string} with customer data from {string}")
    public void i_submit_post_request_with_customer_data_from(String endpoint, String customerKey) {
        if(!customerKey.equals("default")) {
            currentCustomerData = TestDataReader.getDataFromJson(
                    "appleCustomers.json" ,
                    "customers" ,
                    "id",
                    customerKey
            );
            String uniqueEmail = TestDataReader.generateUniqueEmail(
                    currentCustomerData.get("name") , "apple.com"
            );
            currentCustomerData.put("email" , uniqueEmail);
        }
        String payload = APIPayLoadConstants.createAppleCustomerPayload(currentCustomerData);
        request = given()
                .spec(requestSpec)
                .body(payload);

        // response = request.when().post(baseUrl + endpoint); no need for base url since using reqSpec
        response = request.when().post(endpoint);
        System.out.println("POST request sent to: " + baseUrl + endpoint);
        System.out.println("Response status: " + response.getStatusCode());
        response.prettyPrint();
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be_201(int statusCode) {
        Integer actualStatus = response == null ? null : response.getStatusCode();
        Assert.assertEquals(actualStatus.intValue(), statusCode, "Expected status code " + statusCode + " when status is present");
    }

    @And("the response should contain a unique customer ID")
    public void the_response_should_contain_a_unique_customer_ID() {
        customerId = response == null ? null : response.jsonPath().getString("id");
        Assert.assertNotNull(customerId, "Customer ID is null");
        Assert.assertFalse(customerId.trim().isEmpty(), "Customer ID is empty");
        Assert.assertNotEquals(customerId, "8311673", "Customer ID should be unique");

        System.out.println(customerId);
    }

    @And("the customer email should match the test data")
    public void the_customer_email_should_match_the_test_data() {
        email = response.jsonPath().getString("email");
        String testEmail = currentCustomerData.get("email");

        Assert.assertEquals(email, testEmail, "Emails don't match");

        System.out.println("Customer email matches the test data");
    }

    @And("the account status should be {string}")
    public void theAccountStatusShouldBe(String accountStatus) {
        String actualStatus =  response.jsonPath().getString("status");
        Assert.assertEquals(accountStatus, actualStatus);
    }
}

