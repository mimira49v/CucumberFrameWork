package API;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import utils.RawToJson;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.APIConstants;

import java.util.*;

import static io.restassured.RestAssured.given;

/**
 * Practice Test Class for HackerRank Transactions API
 * This class demonstrates various API testing techniques using REST-assured
 */
public class TransactionsAPITest {

    private static final String BASE_URI = "https://jsonmock.hackerrank.com/api/transactions/search";

    @BeforeClass
    public void setup() {
        RestAssured.baseURI = BASE_URI;
    }

    /**
     * Test Case 1: Verify API Response Status Code
     * Practice: Basic API call and status code validation
     */
    @Test(priority = 1)
    public void testAPIResponseStatusCode() {
        var queryParam = new HashMap<String, String>();
        queryParam.put("txnType", "debit");
        queryParam.put("page", "1");

        Response response = given()
                .header(APIConstants.HEADER_CONTENT_TYPE, APIConstants.HEADER_CONTENT_TYPE_VALUE)
                .queryParams(queryParam)
                .when()
                .get();

        System.out.println(response.prettyPrint());

        int statusCode = response.getStatusCode();
        System.out.println("Status Code: " + statusCode);
        Assert.assertEquals(statusCode, 200, "Status code should be 200");
    }

    /**
     * Test Case 2: Validate Response Structure
     * Practice: Verify JSON response contains expected fields
     */
    @Test(priority = 2)
    public void testResponseStructure() {
        Response response = given()
                .header(APIConstants.HEADER_CONTENT_TYPE, APIConstants.HEADER_CONTENT_TYPE_VALUE)
                .queryParam("txnType", "debit")
                .queryParam("page", "1")
                .when()
                .get();

        JsonElement jsonElement = JsonParser.parseString(response.asString());
        JsonObject jsonObject = jsonElement.getAsJsonObject();

        // Verify root level fields exist
        Assert.assertTrue(jsonObject.has("page"), "Response should contain 'page' field");
        Assert.assertTrue(jsonObject.has("per_page"), "Response should contain 'per_page' field");
        Assert.assertTrue(jsonObject.has("total"), "Response should contain 'total' field");
        Assert.assertTrue(jsonObject.has("total_pages"), "Response should contain 'total_pages' field");
        Assert.assertTrue(jsonObject.has("data"), "Response should contain 'data' field");

        System.out.println("All expected fields are present in response");
    }

    /**
     * Test Case 3: Verify Data Array Size
     * Practice: Validate that data array is not empty and has expected size
     */
    @Test(priority = 3)
    public void testDataArraySize() {
        Response response = given()
                .header(APIConstants.HEADER_CONTENT_TYPE, APIConstants.HEADER_CONTENT_TYPE_VALUE)
                .queryParam("txnType", "debit")
                .queryParam("page", "1")
                .when()
                .get();

        JsonElement jsonElement = JsonParser.parseString(response.asString());
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray dataArray = jsonObject.get("data").getAsJsonArray();

        int dataSize = dataArray.size();
        System.out.println("Data array size: " + dataSize);

        Assert.assertTrue(dataSize > 0, "Data array should not be empty");
        Assert.assertTrue(dataSize <= 10, "Data array size should not exceed per_page limit");
    }

    /**
     * Test Case 4: Filter by Transaction Type - Debit
     * Practice: Verify all transactions in response are of type 'debit'
     */
    @Test(priority = 4)
    public void testDebitTransactionFilter() {
        Response response = given()
                .header(APIConstants.HEADER_CONTENT_TYPE, APIConstants.HEADER_CONTENT_TYPE_VALUE)
                .queryParam("txnType", "debit")
                .queryParam("page", "1")
                .when()
                .get();

        JsonElement jsonElement = JsonParser.parseString(response.asString());
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray dataArray = jsonObject.get("data").getAsJsonArray();

        // Verify all transactions are debit type
        for (int i = 0; i < dataArray.size(); i++) {
            JsonObject transaction = dataArray.get(i).getAsJsonObject();
            String txnType = transaction.get("txnType").getAsString();
            Assert.assertEquals(txnType, "debit", "Transaction type should be 'debit'");
        }

        System.out.println("All transactions verified as 'debit' type");
    }

    /**
     * Test Case 5: Filter by Transaction Type - Credit
     * Practice: Verify all transactions in response are of type 'credit'
     */
    @Test(priority = 5)
    public void testCreditTransactionFilter() {
        Response response = given()
                .header(APIConstants.HEADER_CONTENT_TYPE, APIConstants.HEADER_CONTENT_TYPE_VALUE)
                .queryParam("txnType", "credit")
                .queryParam("page", "1")
                .when()
                .get();

        JsonElement jsonElement = JsonParser.parseString(response.asString());
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray dataArray = jsonObject.get("data").getAsJsonArray();

        // Verify all transactions are credit type
        for (int i = 0; i < dataArray.size(); i++) {
            JsonObject transaction = dataArray.get(i).getAsJsonObject();
            String txnType = transaction.get("txnType").getAsString();
            Assert.assertEquals(txnType, "credit", "Transaction type should be 'credit'");
        }

        System.out.println("All transactions verified as 'credit' type");
    }

    /**
     * Test Case 6: Verify Pagination Works Correctly
     * Practice: Test different page numbers return different data
     */
    @Test(priority = 6)
    public void testPagination() {
        // Get page 1
        Response response1 = given()
                .header(APIConstants.HEADER_CONTENT_TYPE, APIConstants.HEADER_CONTENT_TYPE_VALUE)
                .queryParam("txnType", "debit")
                .queryParam("page", "1")
                .when()
                .get();

        // Get page 2
        Response response2 = given()
                .header(APIConstants.HEADER_CONTENT_TYPE, APIConstants.HEADER_CONTENT_TYPE_VALUE)
                .queryParam("txnType", "debit")
                .queryParam("page", "2")
                .when()
                .get();

        JsonObject page1Data = JsonParser.parseString(response1.asString()).getAsJsonObject();
        JsonObject page2Data = JsonParser.parseString(response2.asString()).getAsJsonObject();

        // Verify page numbers are different
        Assert.assertNotEquals(
                page1Data.get("page").getAsInt(),
                page2Data.get("page").getAsInt(),
                "Page numbers should be different"
        );

        System.out.println("Pagination working correctly");
    }

    /**
     * Test Case 7: Group Transactions by User ID (Main Practice Test)
     * Practice: Complex data processing - grouping transaction amounts by userId
     * This mimics the logic from APIAssignment.java
     */
    @Test(priority = 7)
    public void testGroupTransactionsByUserId() {
        Response response = given()
                .header(APIConstants.HEADER_CONTENT_TYPE, APIConstants.HEADER_CONTENT_TYPE_VALUE)
                .queryParam("txnType", "debit")
                .queryParam("page", "5")
                .when()
                .get();

        JsonElement jsonElement = JsonParser.parseString(response.asString());
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray dataArray = jsonObject.get("data").getAsJsonArray();

        // Group transactions by userId
        Map<Integer, List<Integer>> transactionsByUser = new LinkedHashMap<>();

        for (int i = 0; i < dataArray.size(); i++) {
            JsonObject transaction = dataArray.get(i).getAsJsonObject();
            int userId = transaction.get("userId").getAsInt();
            String amountStr = transaction.get("amount").getAsString().replaceAll("[$,]", "");
            Integer amount = (int) Double.parseDouble(amountStr);

            transactionsByUser.computeIfAbsent(userId, k -> new ArrayList<>()).add(amount);
        }

        // Display grouped data
        System.out.println("=== Transactions Grouped by User ID ===");
        for (Map.Entry<Integer, List<Integer>> entry : transactionsByUser.entrySet()) {
            System.out.println("User ID: " + entry.getKey());
            System.out.println("Transaction Amounts: " + entry.getValue());
            System.out.println("Total Transactions: " + entry.getValue().size());
            System.out.println("------------------------------");
        }

        // Assertions
        Assert.assertFalse(transactionsByUser.isEmpty(), "Should have grouped transactions");
        transactionsByUser.values().forEach(amounts ->
                Assert.assertTrue(amounts.size() > 0, "Each user should have at least one transaction")
        );
    }

    /**
     * Test Case 8: Calculate Total Debit Amount per User
     * Practice: Data aggregation and calculation
     */
    @Test(priority = 8)
    public void testCalculateTotalDebitPerUser() {
        Response response = given()
                .header(APIConstants.HEADER_CONTENT_TYPE, APIConstants.HEADER_CONTENT_TYPE_VALUE)
                .queryParam("txnType", "debit")
                .queryParam("page", "1")
                .when()
                .get();

        JsonElement jsonElement = JsonParser.parseString(response.asString());
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray dataArray = jsonObject.get("data").getAsJsonArray();

        Map<Integer, Double> totalDebitByUser = new HashMap<>();

        for (int i = 0; i < dataArray.size(); i++) {
            JsonObject transaction = dataArray.get(i).getAsJsonObject();
            int userId = transaction.get("userId").getAsInt();
            String amountStr = transaction.get("amount").getAsString().replaceAll("[$,]", "");
            double amount = Double.parseDouble(amountStr);

            totalDebitByUser.put(userId, totalDebitByUser.getOrDefault(userId, 0.0) + amount);
        }

        System.out.println("=== Total Debit Amount per User ===");
        totalDebitByUser.forEach((userId, total) ->
                System.out.println("User ID " + userId + ": $" + String.format("%.2f", total))
        );

        Assert.assertFalse(totalDebitByUser.isEmpty(), "Should have calculated totals");
    }

    /**
     * Test Case 9: Verify Transaction Object Structure
     * Practice: Validate nested JSON objects and fields
     */
    @Test(priority = 9)
    public void testTransactionObjectStructure() {
        Response response = given()
                .header(APIConstants.HEADER_CONTENT_TYPE, APIConstants.HEADER_CONTENT_TYPE_VALUE)
                .queryParam("txnType", "debit")
                .queryParam("page", "1")
                .when()
                .get();

        JsonElement jsonElement = JsonParser.parseString(response.asString());
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray dataArray = jsonObject.get("data").getAsJsonArray();

        // Verify first transaction has all required fields
        JsonObject firstTransaction = dataArray.get(0).getAsJsonObject();

        String[] requiredFields = {"id", "userId", "userName", "timestamp", "txnType", "amount", "location", "ip"};

        for (String field : requiredFields) {
            Assert.assertTrue(firstTransaction.has(field),
                    "Transaction should have '" + field + "' field");
        }

        // Verify location is a nested object with required fields
        JsonObject location = firstTransaction.get("location").getAsJsonObject();
        String[] locationFields = {"id", "address", "city", "zipCode"};

        for (String field : locationFields) {
            Assert.assertTrue(location.has(field),
                    "Location should have '" + field + "' field");
        }

        System.out.println("Transaction object structure validated successfully");
    }

    /**
     * Test Case 10: Verify Response Time
     * Practice: Performance testing - verify API responds within acceptable time
     */
    @Test(priority = 10)
    public void testResponseTime() {
        long startTime = System.currentTimeMillis();

        Response response = given()
                .header(APIConstants.HEADER_CONTENT_TYPE, APIConstants.HEADER_CONTENT_TYPE_VALUE)
                .queryParam("txnType", "debit")
                .queryParam("page", "1")
                .when()
                .get();

        long endTime = System.currentTimeMillis();
        long responseTime = endTime - startTime;

        System.out.println("API Response Time: " + responseTime + "ms");
        Assert.assertTrue(responseTime < 5000, "Response time should be less than 5 seconds");
    }

    /**
     * Test Case 11: Find User with Maximum Debit Transactions
     * Practice: Data analysis and comparison
     */
    @Test(priority = 11)
    public void testFindUserWithMaxTransactions() {
        Response response = given()
                .header(APIConstants.HEADER_CONTENT_TYPE, APIConstants.HEADER_CONTENT_TYPE_VALUE)
                .queryParam("txnType", "debit")
                .queryParam("page", "5")
                .when()
                .get();

        JsonElement jsonElement = JsonParser.parseString(response.asString());
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray dataArray = jsonObject.get("data").getAsJsonArray();

        Map<Integer, Integer> transactionCountByUser = new HashMap<>();

        for (int i = 0; i < dataArray.size(); i++) {
            JsonObject transaction = dataArray.get(i).getAsJsonObject();
            int userId = transaction.get("userId").getAsInt();
            transactionCountByUser.put(userId, transactionCountByUser.getOrDefault(userId, 0) + 1);
        }

        // Find user with max transactions
        int maxUserId = transactionCountByUser.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(-1);

        int maxCount = transactionCountByUser.get(maxUserId);

        System.out.println("User with most transactions: User ID " + maxUserId +
                " with " + maxCount + " transactions");

        Assert.assertTrue(maxUserId > 0, "Should find user with max transactions");
    }

    /**
     * Test Case 12: Validate Content-Type Header
     * Practice: HTTP header validation
     */
    @Test(priority = 12)
    public void testResponseContentType() {
        Response response = given()
                .header(APIConstants.HEADER_CONTENT_TYPE, APIConstants.HEADER_CONTENT_TYPE_VALUE)
                .queryParam("txnType", "debit")
                .queryParam("page", "1")
                .when()
                .get();

        String contentType = response.getHeader("Content-Type");
        System.out.println("Response Content-Type: " + contentType);

        Assert.assertTrue(contentType.contains("application/json"),
                "Response should be JSON format");
    }

     @Test(priority = 1)
     public void test() {

        long expecteTimeStamp = 1549536882071L;

         Response response = given()
                 .header(APIConstants.HEADER_CONTENT_TYPE, APIConstants.HEADER_CONTENT_TYPE_VALUE)
                 .when()
                 .get();

         JsonObject root = JsonParser.parseString(response.asString()).getAsJsonObject();
         try {
             long timeStamp = root.getAsJsonArray("data")
                     .get(0).getAsJsonObject()
                     .get("timestamp").getAsLong();
             System.out.println(timeStamp);
             Assert.assertEquals(timeStamp, expecteTimeStamp);
         } catch (Exception e) {
             throw new RuntimeException(e);
         }
         System.out.println("Time stamp test passed!");
     }

    /**
     * Test Case 13: Demonstrate PUT Request
     * Practice: Making a PUT request with JSON body
     * Note: This API is read-only, so PUT will likely return 404 or 405
     */
    @Test(priority = 13)
    public void testPutRequest() {
        // Create a JSON body for the PUT request
        JsonObject requestBody = new JsonObject();
        requestBody.addProperty("userId", 5);
        requestBody.addProperty("userName", "John Doe");
        requestBody.addProperty("txnType", "credit");
        requestBody.addProperty("amount", "$500.00");

        // Make PUT request to update a transaction (ID 1 in this example)
        Response response = given()
                .header(APIConstants.HEADER_CONTENT_TYPE, APIConstants.HEADER_CONTENT_TYPE_VALUE)
                .body(requestBody.toString())
                .when()
                .put("/1");  // PUT to /api/transactions/search/1

        System.out.println("PUT Request Status Code: " + response.getStatusCode());
        System.out.println("PUT Request Response:");
        System.out.println(response.prettyPrint());

        // Note: Since this is a mock read-only API, we expect it to fail
        // In a real API that supports PUT, you'd assert statusCode == 200 or 204
        System.out.println("Note: This API doesn't support PUT operations (read-only)");

    }
}