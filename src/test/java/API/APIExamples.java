package API;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Test;
import static io.restassured.RestAssured.*;

public class APIExamples {
//set the base uri
    String baseURI = RestAssured.baseURI = "http://hrm.syntaxtechs.net/syntaxapi/api";
    String Token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2NjkzMTYwNjIsImlzcyI6ImxvY2FsaG9zdCIsImV4cCI6MTY2OTM1OTI2MiwidXNlcklkIjoiMzc3MiJ9.op2RS6aEJBh3M65hwZ_xd0A1N1dyR9EGmWRHWEo0-bw";

//create an Employee in SyntaxHRMS
    @Test
    public void createAnEmployee(){
        RequestSpecification request=given().header("Content-Type", "application/json").header("Authorization",Token).body("{\n" +
                "  \"emp_firstname\": \"mi32sdran22\",\n" +
                "  \"emp_lastname\": \"dafafadf22222\",\n" +
                "  \"emp_middle_name\": \"MSS\",\n" +
                "  \"emp_gender\": \"M\",\n" +
                "  \"emp_birthday\": \"2009-06-11\",\n" +
                "  \"emp_job_title\": \"EMPLOYED\",\n" +
                "  \"emp_status\": \"QA\"\n" +
                "}");
        Response response = request.post("/createEmployee.php");
//  UNDERSTANDING GSON to decode json object
        // jsonElement -> json Object
        JsonElement json_element = new JsonParser().parse(response.asString());
        JsonObject json_data = json_element.getAsJsonObject();
        JsonElement key_message = json_data.get("Message");
        String message = key_message.getAsString();
        System.out.println("Stored in string : " + message);
        System.out.println(key_message);
        JsonElement Employee = json_data.get("Employee");
        System.out.println(Employee);
        JsonObject Employee_details = Employee.getAsJsonObject();
        JsonElement name;
        System.out.println(name=Employee_details.get("emp_firstname"));
        System.out.println(Employee_details.get("emp_firstname"));
        System.out.println(Employee_details.get("emp_middle_name"));
        System.out.println(Employee_details.get("emp_birthday"));

        Assert.assertEquals(Employee, Employee_details);
    }
}
