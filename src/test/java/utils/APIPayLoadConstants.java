package utils;

import org.json.JSONObject;

public class APIPayLoadConstants {
    public static String createEmployeePayLoad() {
        String createEmployee = "{\n" +
                "  \"emp_firstname\": \"Aymat\",\n" +
                "  \"emp_lastname\": \"tata\",\n" +
                "  \"emp_middle_name\": \"MS\",\n" +
                "  \"emp_gender\": \"M\",\n" +
                "  \"emp_birthday\": \"1988-02-28\",\n" +
                "  \"emp_status\": \"Employee\",\n" +
                "  \"emp_job_title\": \"QA\"\n" +
                "}";

        return createEmployee;
    }

//  passing the body from json object
    public static String createEmployeePayloadViaJson(){

    JSONObject obj = new JSONObject();
        obj.put("emp_firstname", "Aymat");
        obj.put("emp_lastname", "tata");
        obj.put("emp_middle_name", "MS");
        obj.put("emp_gender", "M");
        obj.put("emp_birthday", "1988-02-28");
        obj.put("emp_status", "Employee");
        obj.put("emp_job_title", "QA");

        return obj.toString();
    }

    public static String  createEmployeeDynamic(String firstName, String lastName, String middleName,
                                                        String gender, String dob, String status,String jobTitle){

        JSONObject obj = new JSONObject();
        obj.put("emp_firstname", firstName);
        obj.put("emp_lastname", lastName);
        obj.put("emp_middle_name", middleName);
        obj.put("emp_gender", gender);
        obj.put("emp_birthday", dob);
        obj.put("emp_status", status);
        obj.put("emp_job_title", jobTitle);

        return obj.toString(); // we are using to string cuz we declared it in the method header!
    }
    public static String jsonPractice(String isbn, String aisle) {
        return "{\n" +
                "\n" +
                "\t\"dashboard\": {\n" +
                "\n" +
                "\t\t\"purchaseAmount\": 910,\n" +
                "\n" +
                "\t\t\"website\": \"rahulshettyacademy.com\"\n" +
                "\n" +
                "\t},\n" +
                "\n" +
                "\t\"courses\": [\n" +
                "\n" +
                "\t\t{\n" +
                "\n" +
                "\t\t\t\"title\": \"Selenium Python\",\n" +
                "\n" +
                "\t\t\t\"price\": 50,\n" +
                "\n" +
                "\t\t\t\"copies\": 6\n" +
                "\n" +
                "\t\t},\n" +
                "\n" +
                "\t\t{\n" +
                "\n" +
                "\t\t\t\"title\": \"Cypress\",\n" +
                "\n" +
                "\t\t\t\"price\": 40,\n" +
                "\n" +
                "\t\t\t\"copies\": 4\n" +
                "\t\t},\n" +
                "\t\t{\n" +
                "\t\t\t\"title\": \"RPA\",\n" +
                "\n" +
                "\t\t\t\"price\": \"45\",\n" +
                "\n" +
                "\t\t\t\"copies\": \"5\"\n" +
                "\t\t}\n" +
                "\t]\n" +
                "}";

    }

    public static String API_Example(){
        return "{\n" +
                "  \"emp_firstname\": \"mi32sdran22\",\n" +
                "  \"emp_lastname\": \"dafafadf22222\",\n" +
                "  \"emp_middle_name\": \"MSS\",\n" +
                "  \"emp_gender\": \"M\",\n" +
                "  \"emp_birthday\": \"2009-06-11\",\n" +
                "  \"emp_job_title\": \"EMPLOYED\",\n" +
                "  \"emp_status\": \"QA\"\n" +
                "}";
    }}

/*

{
    "dashboard": 910,
    "website": "rahulshettyacademy.com
    },
    "courses":{
        "title": "Selenium Python"
        "price": 50,
        "copies": 6
        }.
        {
        "title": "Cypress"
        "price": 40,
        "copies": 4
        },
        {
        "title": "RPA"
        "price": 45,
        "copies": 10
        }
    }
    }



 */
