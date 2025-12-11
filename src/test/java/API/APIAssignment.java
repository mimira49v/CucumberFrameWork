package API;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import static io.restassured.RestAssured.given;

public class APIAssignment {
    public static void main(String[] args) {
        RestAssured.baseURI = "https://jsonmock.hackerrank.com/api/transactions/search";
        RequestSpecification preparedRequest = given()
            .header("Content-Type", "application/json")
            .queryParam("txnType", "debit")
            .queryParam("page", "5");
        Response response = preparedRequest.when().get();
        JsonElement jelement = new JsonParser().parse(response.asString());
        System.out.println(jelement);
        JsonObject data = jelement.getAsJsonObject();
        JsonElement alldata = data.get("data");
        System.out.println(alldata);
        JsonArray arrayofdata = alldata.getAsJsonArray();
        int sizeofData = arrayofdata.size();
        System.out.println("Size of data : " + sizeofData);
        Map<Integer, List<Integer>> listList = new LinkedHashMap();
        for (int i = 0; i < sizeofData; i++) {
            JsonElement userInfo = arrayofdata.get(i);
            JsonObject usersInfo = userInfo.getAsJsonObject();
            int userID = usersInfo.get("userId").getAsInt();
//          System.out.print(userID);
            String trans = usersInfo.get("amount").getAsString().replaceAll("[,$]", "");
            Integer transcationAmount = (int) Double.parseDouble(trans);
//          System.out.println(transcationAmount);
            String transactionType = usersInfo.get("txnType").getAsString();
            if (transactionType.equals("debit")) {
                List<Integer> inner = listList.get(userID);
                if (inner != null) {
                    inner.add(transcationAmount);
                    listList.put(userID, inner);
                } else {
                    List<Integer> arr = new ArrayList<>();
                    arr.add(transcationAmount);
                    listList.put(userID, arr);
                }
            }
        }
        for (Map.Entry<Integer, List<Integer>> entry : listList.entrySet()) {
            System.out.println("User ID: " + entry.getKey());
            System.out.println("Transaction Amounts: " + entry.getValue());
            System.out.println("------------------------------");
        }
    }
}
