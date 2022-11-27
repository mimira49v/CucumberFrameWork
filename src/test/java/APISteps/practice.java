package APISteps;

import com.google.gson.JsonParser;
import org.json.JSONArray;
import org.json.JSONObject;
import org.openqa.selenium.By;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.FileReader;
import java.io.IOException;

import static utils.CommonMethods.driver;

public class practice {

    @Test(dataProvider="dp")
    void login(String data){
        String[] users =data.split(",");
        driver.get("https://demo.nopcommerce.com/login");
        driver.findElement(By.id("Email")).sendKeys(users[0]);
        driver.findElement(By.id("Password")).sendKeys(users[1]);
        driver.findElement(By.xpath("//input[@class='button-1 login-button]")).click();
    }

    @DataProvider(name="dp")
    public String[][] readJson() throws IOException {
        JsonParser jsonParser = new JsonParser();
        FileReader reader = new FileReader("src/test/resources/testdata/responseJSON");
        Object obj = jsonParser.parse(reader);
        JSONObject userLoginsJsonObj = (JSONObject) obj;
        JSONArray userLoginsArray = (JSONArray) userLoginsJsonObj.get("userlogins");

        String arr[]=new String[userLoginsArray.length()];

        for(int i=0; i<userLoginsArray.length(); i++){
            JSONObject users = (JSONObject) userLoginsArray.get(i);
            String user = (String) users.get("username");
            String pwd = (String) users.get("password");

            arr[i]=user+" , "+pwd;
        }
        return new String[][]{arr};
    }
}
