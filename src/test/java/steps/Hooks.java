package steps;


import com.google.gson.JsonParser;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.DataProvider;
import utils.CommonMethods;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;

public class Hooks extends CommonMethods {
    //  needs to be imported from cucumber not org.java
    @Before
    public void start() {
        openBrowserAndLunchApplication();
    }

    @After   // Scenario class from cucumber holds the complete information of our execution
    public void end(Scenario scenario) {
        byte[] pic;
        if (scenario.isFailed()) {
            pic = takeScreenshot("failed/" + scenario.getName());
        } else {
            pic = takeScreenshot("passed/" + scenario.getName());
        }
//      it will attach the pic in the report
        scenario.attach(pic, "image/png", scenario.getName());
        tearDown();
    }

    @DataProvider(name = "db")
    public String [] readJson() throws IOException, ParseException {
        JsonParser jsonParser = new JsonParser();
        FileReader reader = new FileReader("testdata/responseJSON");
        Object obj = jsonParser.parse(reader);

        JSONObject userLoginsJsonObj = (JSONObject) obj;
        JSONArray userLoginArray = (JSONArray) userLoginsJsonObj.get("userlogins");

        String[] arr = new String[userLoginArray.length()];

        for (int i = 0; i < userLoginArray.length(); i++) {
            JSONObject users = (JSONObject) userLoginArray.get(i); // extracting the first element(JSONObject)
            String user = (String) users.get("username");
            String pwd = (String) users.get("password");

            arr[i] = user + " , " + pwd;
        }
        return arr;
    }
}
