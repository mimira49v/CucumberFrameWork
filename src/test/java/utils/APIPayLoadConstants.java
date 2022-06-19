package utils;

public class APIPayLoadConstants {
    public static String createEmployeePayLoad(){
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
}
