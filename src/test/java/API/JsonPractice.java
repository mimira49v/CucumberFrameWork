package API;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Test;
import utils.APIConstants;
import utils.APIPayLoadConstants;
import static io.restassured.RestAssured.given;
import static utils.RawToJson.rawToJson;

public class JsonPractice {
    String baseURI = RestAssured.baseURI = "http://hrm.syntaxtechs.net/syntaxapi/api";
    @Test
    public void createAnEmployee() {
        RequestSpecification request = given()
                                        .header(APIConstants.HEADER_CONTENT_TYPE, APIConstants.HEADER_CONTENT_TYPE_VALUE)
                                        .header(APIConstants.HEADER_AUTHORIZATION, JWT.JWT_GENERATED())
                                        .body(APIPayLoadConstants.API_Example());

        Response response = request.post("/createEmployee.php");

        String res = response.asPrettyString();
        System.out.println(res);

        /*

        We takes raw API response object (res) and passes it to the rawToJson method.
        The rawToJson method converts the raw response body into a JsonPath object.
        The JsonPath object (js1) allows us to query specific data fields from
        the JSON response in a structured and easy way.

         */
        JsonPath js1 = rawToJson(res);
        String employee_id = js1.getString("Employee.employee_id");
        System.out.println("EMPLOYE ID : " + employee_id);


//      response goes to Json OBJECT FIRST!!!
        JsonObject jsonObject = (JsonObject) new JsonParser().parse(res.toString());
        JsonObject json_data = jsonObject.getAsJsonObject();
        JsonElement Employee = json_data.get("Employee");
        JsonObject Employee_details = Employee.getAsJsonObject();
        String empid = String.valueOf(Employee_details.get("emp_firstname"));
        System.out.println(empid);





//        Looking for Json Element from Json Object
//        JsonElement key_msg = jsonObject.get("Message");
//        Storing that element in the string
//        String msg = key_msg.getAsString();
//        System.out.println(msg);
//        JsonElement employee = jsonObject.get("Employee");
//
//        System.out.println(employee);
//        String x = "balance";
//        String emp = employee.toString();
//        System.out.println(emp);
        
    }
}
