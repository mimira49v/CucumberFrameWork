package API;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;
import pojo.AddPlace;
import pojo.Location;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class serializeTest {

    public static String status;
    public static String id;

    public static void main(String[] args) {

        AddPlace p = new AddPlace();
        p.setAccuracy(50);
        p.setAddress("29, side layout, cohen 09");
        p.setLanguage("French-IN");
        p.setPhone_number("(+91) 983 893 3937");
        p.setWebsite("htttps://rahulshettyacademy.com");
        p.setName("Frontline house");

        List<String> myList = new ArrayList<String>();
        myList.add("shoe park");
        myList.add("shop");
        p.setTypes(myList);

        Location l = new Location();
        l.setLat(-38.383484);
        l.setLng(33.427362);

        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com").
                addQueryParam("key", "qaclick123").
                setContentType(ContentType.JSON).build();

        p.setLocation(l);

        RestAssured.baseURI = "https://rahulshettyacademy.com";

        Response res = given().spec(req).
                       body(p).
                       when().post("/maps/api/place/add/json").
                       then().assertThat().statusCode(200).extract().response();

        System.out.println(res.asString());

        status = "OK";
        id = "6b686ac85d7e9e5237cff36e95edde2c";

        JsonElement jsonObject = new JsonParser().parse(res.asString());
        JsonObject json_data = jsonObject.getAsJsonObject();
        JsonElement st = json_data.get("status");
        String actual_status = st.getAsString();

        Assert.assertEquals("TEST FAILED",actual_status,status);
    }
}
