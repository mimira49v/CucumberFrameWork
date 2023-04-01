package API;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;


@FixMethodOrder(MethodSorters.NAME_ASCENDING) // executes methods alphabetically
public class HardCodedExamples {
    String baseURI = RestAssured.baseURI = "http://hrm.syntaxtechs.net/syntaxapi/api"; // needs http://
    String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2Njk1OTEwNjksImlzcyI6ImxvY2FsaG9zdCIsImV4cCI6MTY2OTYzNDI2OSwidXNlcklkIjoiMzc3MiJ9.x8CzD_vIuVQKv0xLTaRXJBTQTJUqg-lAUThdLu5sxMw";
    static String employee_id;

    @Test
    public void AcreateEmployee(){
        // NEED TO PREPARE THE REQUEST FIRST
        RequestSpecification request =
                given().log().all().header("Content-type","application/json").
                header("Authorization",token).body("{\n" +
                        "  \"emp_firstname\": \"mil222os\",\n" +
                        "  \"emp_lastname\": \"dae2222res\",\n" +
                        "  \"emp_middle_name\": \"MSS\",\n" +
                        "  \"emp_gender\": \"F\",\n" +
                        "  \"emp_birthday\": \"2009-06-11\",\n" +
                        "  \"emp_job_title\": \"Probation\",\n" +
                        "  \"emp_status\": \"QA\"\n" +
                        "}");
//      hit the end point to make a call we have to use when keyword
        Response response = request.when().post("/createEmployee.php");     // object request is from RequestSpecification
        response.prettyPrint();                                                // prints the repsonse
        response.then().assertThat().statusCode(201).body("Message", equalTo("Employee Created"));
        //Hamcrest matchers                            equalTO import Matchers.equalTo;
        response.then().assertThat().body("Message",equalTo("Employee Created"));
        response.then().assertThat().body("Employee.emp_firstname", equalTo("mil222os"));
//      using jsonPath(), to specify the key in the body so that it returns the value against it.
        employee_id = response.jsonPath().getString("Employee.employee_id");
        System.out.println(employee_id);

//                    if you are not looking for the key in the right place it will give you null values :
//                    if you only pass on line 34 :  emp_firstname
//             Error :
//                    Expected: mil222os
//                              Actual: null
//                    needs to be Employee.emp_firstname to pass the test
    }
    @Test
    public void BgetCreatedEmployee(){
        RequestSpecification preparedRequest = given().header("Content-Type","application/json").
                                        header("Authorization",token).queryParam("employee_id", employee_id);

        Response response = preparedRequest.when().get("/getOneEmployee.php");
        response.prettyPrint();

        response.then().assertThat().statusCode(200);

        String tempId = response.jsonPath().getString("employee.employee_id");
            System.out.println(tempId);
                Assert.assertEquals(tempId, employee_id);
    }
    @Test
    public void cupdateEmployee(){
        RequestSpecification preparedRequest = given().header("Content-Type", "application/json").
                header("Authorization", token).
                body("{\n" +
                        "        \"employee_id\": \""+ employee_id + "\",\n" +
                        "        \"emp_firstname\": \"edward\",\n" +
                        "        \"emp_lastname\": \"sisi\",\n" +
                        "        \"emp_middle_name\": \"MS1\", \n" +
                        "        \"emp_gender\": \"M\",       \n" +
                        "        \"emp_birthday\": \"1995-06-12\", \n" +
                        "        \"emp_status\": \"confirmed\",       \n" +
                        "        \"emp_job_title\": \"Manager\"        \n" +
                        "    }");

        Response response =preparedRequest.when().put("/updateEmployee.php");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
    }
    @Test
    public void DgetUpdatedEmployee(){
        RequestSpecification request = given().header("Content-Type","application/json").
                header("Authorization",token).queryParam("employee_id",employee_id);
        Response response = request.when().get("/getOneEmployee.php");
        response.then().assertThat().statusCode(200);
        response.prettyPrint();
    }

    @Test
    public void eGetAllEmployees(){
        RequestSpecification request = given().header("Authorization",token)
                .header("Content-Type","application/json");

        Response response = request.when().get("/getAllEmployees.php");

        //it returns string of response
        String allEmployees = response.prettyPrint();

        //jsonPath() vs jsonPath
        //jsonPath is a class that contains method for converting the values into json object
        //jsonPath() is a method belongs to jsonPath class

        //creating object of jsonPath class
        JsonPath js = new JsonPath(allEmployees);

        //retrieving the total number of employees
        int count = js.getInt("Employees.size()");
        System.out.println(count);

        //to print only employee id of all the employees
        for (int i=0; i<count; i++){
            String empID =  js.getString("Employees["+ i + "].employee_id");
            System.out.println(empID);
        }
    }
}
