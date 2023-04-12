package API;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;
import utils.APIConstants;
import java.io.IOException;
import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertTrue;
import static utils.RawToJson.rawToJson;

public class regresin {

    private static final long RESPONSE_TIME_TRESHHOLD = 1000; // in milliseconds
    private static String id_exp = "4";
    String baseURI = RestAssured.baseURI = "https://reqres.in/";

    private static final String ENDPOINT = "/api/register";

    @Test
    public void listResourse(){

        String URL = "https://reqres.in/#support-heading";

        RequestSpecification request = given()
                .header(APIConstants.HEADER_CONTENT_TYPE, APIConstants.HEADER_CONTENT_TYPE_VALUE);
        Response response = request.get("/api/unknown");

        long timeTaken = response.time();

        String res = response.asPrettyString();
//        System.out.println(res);
        JsonPath url_jsonPath = rawToJson(res);
        String url_isPresent = url_jsonPath.getString("support.url");
        Assert.assertEquals("URL is not the same!", url_isPresent,URL);
        System.out.println("URL key is present! URL : " + url_isPresent);
        System.out.println("Time taken: " + timeTaken + "ms");
        assertTrue("Response time is less than " + RESPONSE_TIME_TRESHHOLD + " ms",
                response.time() <= RESPONSE_TIME_TRESHHOLD);

        System.out.println("TEST PASSED!");
    }

    @Test
    public void unsuccesfulRegister() {
        RequestSpecification request = given()
                .header(APIConstants.HEADER_CONTENT_TYPE, APIConstants.HEADER_CONTENT_TYPE_VALUE)
                .body("{\"email\": \"sydney@fife\"}");

        Response response = request.post("/api/register");
        String res = response.asPrettyString();
        System.out.println(res);

        JsonPath js1 = rawToJson(res);
        String error = js1.getString("error");
        System.out.println("Error key is present : " + error);
    }

    @Test
    public void postReq(){

        RequestSpecification request = given().
                                            header(APIConstants.HEADER_CONTENT_TYPE, APIConstants.HEADER_CONTENT_TYPE_VALUE).
                                            header(APIConstants.HEADER_AUTHORIZATION,JWT.JWT_GENERATED()).
                                       body("{\n" +
                                               "    \"email\": \"eve.holt@reqres.in\",\n" +
                                               "    \"password\": \"pistol\"\n" +
                                               "}");


            Response response = request.post("/api/users?page=2");
            String responseCreateEmployee = response.asPrettyString();
            System.out.println(responseCreateEmployee);

            JsonPath js1 = rawToJson(responseCreateEmployee);
            String id = js1.get("id");
            System.out.println("Id key is present! : " + id);
    }

    @Test
    public void createAnEmployee() throws IOException {

        String job_expected = "leader";
        long RESPONSE_TIME_THRESHOLD = 500; // in milliseconds

        RequestSpecification request = given()
                .header(APIConstants.HEADER_CONTENT_TYPE, APIConstants.HEADER_CONTENT_TYPE_VALUE)
                .header(APIConstants.HEADER_AUTHORIZATION,JWT.JWT_GENERATED())
                .body("{\n" +
                        "    \"name\": \"morpheus\",\n" +
                        "    \"job\": \"leader\"\n" +
                        "}");
        Response response = request.post("/api/users");
        long timeTaken = response.time();
        System.out.println("Time taken: " + timeTaken + "ms");
        String res = response.asPrettyString();
        JsonPath js1 = rawToJson(res);
        System.out.println(res);
        String job_actual = js1.getString("job");
        System.out.println(job_actual);
        Assert.assertEquals(job_actual, job_expected);

        assertTrue("Response time is less than " + RESPONSE_TIME_THRESHOLD + " ms",
                response.time() <= RESPONSE_TIME_THRESHOLD);
    }

    @Test
    public void regSucc() {

        RequestSpecification request = given().
                                            header(APIConstants.HEADER_CONTENT_TYPE, APIConstants.HEADER_CONTENT_TYPE_VALUE).
                                            header(APIConstants.HEADER_AUTHORIZATION, JWT.JWT_GENERATED()).
                                        body("{\n" +
                                                 "    \"email\": \"eve.holt@reqres.in\",\n" +
                                                 "    \"password\": \"pistol\"\n" +
                                                 "}");

        Response response = request.post("/api/register");

        String res = response.asPrettyString();

        JsonPath js = rawToJson(res);
        String id_act = js.getString("id");

        System.out.println("Actual id is: " + id_act);
        Assert.assertEquals(id_exp, id_act);

        System.out.println(res);

        long timeTaken = response.time();
        System.out.println("Time taken: " + timeTaken + "ms");
        assertTrue("Response time is less than " + RESPONSE_TIME_TRESHHOLD + " ms",
                response.time() <= RESPONSE_TIME_TRESHHOLD);

        System.out.println("TEST PASSED!");
    }

    @Test
    public void getUsers() {
        String data = "data";
        RequestSpecification requestSpec = MyRequest.buildRequestSpecification();
        Response response = given()
                                .spec(requestSpec).
                            when()
                                .get();

        int statusCode = response.getStatusCode();
        String responseBody = response.getBody().asString();

        System.out.println("Status code: " + statusCode);
        System.out.println("Response body: " + responseBody);

        Assert.assertEquals(statusCode, 200);

        JSONObject jobj = new JSONObject(responseBody);
        JSONArray dataArr = jobj.getJSONArray(data);

        JSONObject firstObj = dataArr.getJSONObject(0);
        int id = firstObj.getInt("id");
        String email_1 = firstObj.getString("first_name");
        System.out.println(id);
        System.out.println(email_1);
        System.out.println(dataArr.toString());

        JSONObject secondObj = dataArr.getJSONObject(1);
        String lastName = secondObj.getString("last_name");
        System.out.println("Last name of the first index : " + lastName);

        Assert.assertTrue(jobj.has(data));

        JSONObject jobj2 = new JSONObject(responseBody);
        JSONArray dataArr2 = jobj2.getJSONArray(data);

        for (int i = 0; i < dataArr2.length(); i++) {
            JSONObject obj = dataArr2.getJSONObject(i);
            Assert.assertTrue(obj.has("id"));
            Assert.assertTrue(obj.has("email"));
            Assert.assertTrue(obj.has("first_name"));
            Assert.assertTrue(obj.has("last_name"));
            Assert.assertTrue(obj.has("avatar"));

            int id_2 = obj.getInt("id");
            String email = obj.getString("email");
            String firstName_2 = obj.getString("first_name");
            String lastName_2 = obj.getString("last_name");
            String avatar = obj.getString("avatar");

            System.out.println("ID: " + id_2);
            System.out.println("Email: " + email);
            System.out.println("First Name: " + firstName_2);
            System.out.println("Last Name: " + lastName_2);
            System.out.println("Avatar: " + avatar);
        }
    }
}
