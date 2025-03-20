package utils;

import java.util.HashMap;
import java.util.Map;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;


public class test {
    private static io.restassured.RestAssured RestAssured;


//   CREATING NESTED JSON USING HASHMAP

    public static void main(String[] args) {
        RestAssured.baseURI = "http://your-api-base-url.com";
        Map<String, Object> map = new HashMap<>();
        map.put("firstName", "John");
        map.put("lastName", "Doe");

        // if you have a nested json you can create another map that will be for that object exp:
        HashMap <String, Object> map2 = new HashMap<>();
        map.put("lat", 12);
        map.put("long", 34);
        map.put("location", map2);

        given().
                contentType(JSON).
                body(map).
        when().
                post("/somewhere").
        then().
                statusCode(200);
    }
}

