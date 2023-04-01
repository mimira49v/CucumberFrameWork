package utils;


import io.cucumber.java.en.Given;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;



public class test extends CommonMethods {
    RequestSpecification request;
    Response response;
    public static String employee_id;

    @Given("a request is prepared to create an employee")
    public void prep_request() {

    }
}
