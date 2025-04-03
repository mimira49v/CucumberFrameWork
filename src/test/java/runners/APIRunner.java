package runners;


import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
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
public class APIRunner {
}
