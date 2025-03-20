package steps;
import io.cucumber.java.*;
import utils.CommonMethods;


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
}
