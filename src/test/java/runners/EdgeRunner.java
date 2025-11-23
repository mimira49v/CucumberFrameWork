package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/featuresPractice/",
        glue = "steps",
        plugin = {
                "pretty",
                "html:target/edge-report.html",
                "json:target/edge-report.json"
        }
)
public class EdgeRunner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        System.setProperty("browser", "edge");
        return super.scenarios();
    }
}
