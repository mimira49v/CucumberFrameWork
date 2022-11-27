package utils;

import io.restassured.path.json.JsonPath;

public class RawToJson {

    public static JsonPath rawToJson(String response){
        JsonPath js1 = new JsonPath(response);
        return js1;
    }
}
