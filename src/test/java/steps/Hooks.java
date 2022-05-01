package steps;


import io.cucumber.java.After;
import io.cucumber.java.Before;
import utils.CommonMethods;

public class Hooks extends CommonMethods {
    //  needs to be imported from cucumber not org.java
    @Before
    public void start(){
        openBrowserAndLunchApplication();
    }

    @After
    public void end(){
        tearDown();
    }
}
