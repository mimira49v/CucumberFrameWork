package API;

public class PayLoadAddBook {
    public static String Addbook(String isbn, String aisle) {
        return "{\n" +
                "  \"name\": \"Learn Appium Automation with Java\",\n" +
                "  \"isbn\": \"" + isbn + "\",\n" +
                "  \"aisle\": \"" + aisle + "\",\n" +
                "  \"author\": \"John Doe\"\n" +
                "}";
    }
}
