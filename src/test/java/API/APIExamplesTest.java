package API;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.Test;
import utils.APIConstants;
import utils.APIPayLoadConstants;

import static io.restassured.RestAssured.*;
import static utils.RawToJson.rawToJson;

public class APIExamplesTest {

    String baseURI = RestAssured.baseURI = "http://hrm.syntaxtechs.net/syntaxapi/api";

//  create an Employee in SyntaxHRMS

    @Test
    public void createAnEmployee(){
        RequestSpecification request = given()
                                      .header(APIConstants.HEADER_CONTENT_TYPE, APIConstants.HEADER_CONTENT_TYPE_VALUE)
                                      .header(APIConstants.HEADER_AUTHORIZATION,JWT.JWT_GENERATED())
                                      .body(APIPayLoadConstants.API_Example());

        Response response = request.post("/createEmployee.php");

//      PRACTICE CODE

        String res = response.asPrettyString();
//      UNDERSTANDING GSON to decode json object
        // jsonElement -> json Object
        JsonPath js1 = rawToJson(res);
        System.out.println(res);
//      Extracting employee ID from JsonObject
        String employee_id = js1.getString("Employee.employee_id");
        System.out.println("EMPLOYE ID : " + employee_id);
//      =======================================================================
        System.out.println("=======================================================================");

        JsonElement jsonObject = new JsonParser().parse(response.asString());
        System.out.println(jsonObject);

        JsonObject json_data = jsonObject.getAsJsonObject();
        JsonElement key_message = json_data.get("Message");
        String message = key_message.getAsString();
        System.out.println("Stored in string : " + message);
        System.out.println(key_message);

        JsonElement Employee = json_data.get("Employee");
        System.out.println("JsonElement employees : "+Employee);

        JsonObject Employee_details = Employee.getAsJsonObject();
        JsonElement name;
        String empid = String.valueOf(Employee_details.get("emp_firstname"));
        System.out.println(empid);

        System.out.println(name=Employee_details.get("emp_firstname"));
        System.out.println(Employee_details.get("emp_firstname"));
        System.out.println(Employee_details.get("emp_middle_name"));
        System.out.println(Employee_details.get("emp_job_title"));

        Assert.assertEquals(Employee, Employee_details);
    }
}
