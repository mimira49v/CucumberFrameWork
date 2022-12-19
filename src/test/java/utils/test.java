package utils;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.annotations.Test;

import java.io.File;


import static io.restassured.RestAssured.given;

public class test extends CommonMethods {
    @Test
    public void uploadFile() {
        File testUploadFile = new File("C:\\Users\\mileu\\IdeaProjects\\CucumberFrameWork\\src\\test\\resources\\sssssss.JPG");
        RestAssured.baseURI = "hrm.syntaxtechs.net/syntaxapi/api";

        Response response = given().multiPart("image", testUploadFile).when().
                                post("/createEmployee.php");

    }
}