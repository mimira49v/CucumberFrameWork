package API;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class HardCodedExamples {

    String baseURI = RestAssured.baseURI = "http://hrm.syntaxtechs.net/syntaxapi/api";
    String token = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE2Njk1OTEwNjksImlzcyI6ImxvY2FsaG9zdCIsImV4cCI6MTY2OTYzNDI2OSwidXNlcklkIjoiMzc3MiJ9.x8CzD_vIuVQKv0xLTaRXJBTQTJUqg-lAUThdLu5sxMw";
    static String employee_id;

    @Test(priority = 1)
    public void createEmployee() {
        RequestSpecification request = given().log().all()
                .header("Content-type", "application/json")
                .header("Authorization", token)
                .body("{\n" +
                        "  \"emp_firstname\": \"mil222os\",\n" +
                        "  \"emp_lastname\": \"dae2222res\",\n" +
                        "  \"emp_middle_name\": \"MSS\",\n" +
                        "  \"emp_gender\": \"F\",\n" +
                        "  \"emp_birthday\": \"2009-06-11\",\n" +
                        "  \"emp_job_title\": \"Probation\",\n" +
                        "  \"emp_status\": \"QA\"\n" +
                        "}");

        Response response = request.when().post("/createEmployee.php");
        response.prettyPrint();

        response.then().assertThat().statusCode(201).body("Message", equalTo("Employee Created"));
        response.then().assertThat().body("Employee.emp_firstname", equalTo("mil222os"));

        employee_id = response.jsonPath().getString("Employee.employee_id");
        System.out.println("Created employee ID: " + employee_id);
    }

    @Test(priority = 2, dependsOnMethods = "createEmployee")
    public void getCreatedEmployee() {
        RequestSpecification request = given()
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .queryParam("employee_id", employee_id);

        Response response = request.when().get("/getOneEmployee.php");
        response.prettyPrint();

        response.then().assertThat().statusCode(200);

        String tempId = response.jsonPath().getString("employee.employee_id");
        Assert.assertEquals(tempId, employee_id);
    }

    @Test(priority = 3, dependsOnMethods = "createEmployee")
    public void updateEmployee() {
        RequestSpecification request = given()
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .body("{\n" +
                        "        \"employee_id\": \"" + employee_id + "\",\n" +
                        "        \"emp_firstname\": \"edward\",\n" +
                        "        \"emp_lastname\": \"sisi\",\n" +
                        "        \"emp_middle_name\": \"MS1\", \n" +
                        "        \"emp_gender\": \"M\",       \n" +
                        "        \"emp_birthday\": \"1995-06-12\", \n" +
                        "        \"emp_status\": \"confirmed\",       \n" +
                        "        \"emp_job_title\": \"Manager\"        \n" +
                        "    }");

        Response response = request.when().put("/updateEmployee.php");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
    }

    @Test(priority = 4, dependsOnMethods = "updateEmployee")
    public void getUpdatedEmployee() {
        RequestSpecification request = given()
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .queryParam("employee_id", employee_id);

        Response response = request.when().get("/getOneEmployee.php");
        response.prettyPrint();
        response.then().assertThat().statusCode(200);
    }

    @Test(priority = 5)
    public void getAllEmployees() {
        RequestSpecification request = given()
                .header("Authorization", token)
                .header("Content-Type", "application/json");

        Response response = request.when().get("/getAllEmployees.php");

        String allEmployees = response.prettyPrint();
        JsonPath js = new JsonPath(allEmployees);

        int count = js.getInt("Employees.size()");
        System.out.println("Total Employees: " + count);

        for (int i = 0; i < count; i++) {
            String empID = js.getString("Employees[" + i + "].employee_id");
            System.out.println(empID);
        }
    }
}
