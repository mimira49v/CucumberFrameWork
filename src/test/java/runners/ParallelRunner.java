package runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;

@CucumberOptions(
        features = "src/test/resources/featuresPractice",
        glue = "steps",
        tags = "@New222", // run scenarios with any of these tags
        plugin = {
                "pretty",
                "html:target/cucumber/report.html",
                "json:target/cucumber/report.json",
                "rerun:target/failed.txt"
        },
        monochrome = true
)
public class ParallelRunner extends AbstractTestNGCucumberTests {

}

