package API;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

public class MyRequest {
    public static RequestSpecification buildRequestSpecification() {
        RequestSpecBuilder builder = new RequestSpecBuilder();

        builder.setBaseUri("https://reqres.in/");
        builder.setBasePath("/api/users?page=2");
//        builder.addHeader("Content-Type", "application/json");
//        builder.addQueryParam("userId", 1);
//        builder.setBody("{\"title\":\"foo\",\"body\":\"bar\",\"userId\":1}");

        return builder.build();
    }
}
