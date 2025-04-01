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

        tags = "@dynamic",

        plugin = {"pretty", "html:target/report/cucumber.html", "json:target/report/cucumber.json", "junit:target/report/cucumber.xml"}
)
public class APIRunner {
}
