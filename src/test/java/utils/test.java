package utils;

import io.restassured.path.json.JsonPath;
public class test extends CommonMethods {
    public static void main(String[] args) {
        JsonPath js = new JsonPath(APIPayLoadConstants.jsonPractice());
        int count = js.getInt("courses.size()");
//      System.out.println(count);
        int totalAmount = js.getInt("dashboard.purchaseAmount");
//      System.out.println(totalAmount);
        String title = js.get("courses[0].title");
        System.out.println(title);
        System.out.println("======================");

        for (int i = 0; i < count; i++) {
            String courseTtitles = js.get("courses[" + i + "].title");
            System.out.println(courseTtitles + " " );
            System.out.println(js.get("courses[" + i + "].price").toString());
        }
        for (int i = 0; i < count; i++) {
            String courseTtitles = js.get("courses[" + i + "].title");

            if(courseTtitles.equalsIgnoreCase("Selenium Python")) {

                String copies = js.get("courses[" + i + "].copies").toString();

                System.out.println("How many copies did we sold :  " + copies);
                break;
            }
        }

        System.out.println("__________________________________");

        int sum = 0;
        for (int i = 0; i < count; i++) {

            int price = js.getInt("courses[" + i + "].price");
            int copies = js.getInt("courses[" + i + "].copies");
            int amount = price * copies;
            String courseTtitles = js.get("courses[" + i + "].title");
            System.out.println(courseTtitles + " HAS SOLD : " + amount + "$");
            sum = sum + amount;
        }
        System.out.println("__________________________________");
        System.out.println("TOTAL : " + sum + "$");
    }
}