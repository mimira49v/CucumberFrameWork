package API;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;

import static io.restassured.RestAssured.given;

public class HardCodedExamples {
    String baseURI = RestAssured.baseURI = "http://hrm.syntaxtechs.net/syntaxapi/api"; // needs http://
    String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NTUyNDkyOTcsImlzcyI6ImxvY2FsaG9zdCIsImV4cCI6MTY1NTI5MjQ5NywidXNlcklkIjoiMzc3MiJ9.GdAsrTCs0GvyryXWBL3Mf0zfdPpbJGY6g3yp1OsMqB8";

    @Test
    public void createEmployee(){
        // NEED TO PREPARE THE REQUEST FIRST
        RequestSpecification request =
                given().header("Content-type","application/json").
                header("Authorization",token).body("{\n" +
                        "  \"emp_firstname\": \"mil222os\",\n" +
                        "  \"emp_lastname\": \"dae2222res\",\n" +
                        "  \"emp_middle_name\": \"MSS\",\n" +
                        "  \"emp_gender\": \"F\",\n" +
                        "  \"emp_birthday\": \"2009-06-11\",\n" +
                        "  \"emp_job_title\": \"Probation\",\n" +
                        "  \"emp_status\": \"QA\"\n" +
                        "}");
        // hit the end point to make a call we have to use when keyword
        Response response = request.when().post("/createEmployee.php");     // object request is from RequestSpecification
        response.prettyPrint();                                                // prints the repsonse
    }
}
