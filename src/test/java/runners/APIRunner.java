package runners;



import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(

        features = "src/test/resources/features/",

        glue = "APISteps",

        dryRun = false,

        monochrome = true,

        //tags = "@dynamic",

        plugin = {"pretty",
                   "json:target/cucumber/report.json",
                   "html:target/cucumber/report.html",
                   "junit:target/cucumber/report.xml"
        }
)
public class APIRunner extends AbstractTestNGCucumberTests {
}
