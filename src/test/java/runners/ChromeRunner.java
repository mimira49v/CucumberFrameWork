package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;



@CucumberOptions(
        features = "src/test/resources/featuresPractice/",
        glue = "steps",
        plugin = {
                "pretty",
                "html:target/chrome-report.html",
                "json:target/chrome-report.json"
        }
)
public class ChromeRunner extends AbstractTestNGCucumberTests {

    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }

    @Parameters({"browser"})
    @BeforeClass
    public void setUp(String browser) {
        System.setProperty("browser", browser);
        System.out.println(">>> Running with browser: " + browser); // Optional debug
    }
}
