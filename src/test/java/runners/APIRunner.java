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

       // old plugin had to replace cuz of jenkins
       // plugin = {"pretty", "html:target/report/cucumber.html", "json:target/report/cucumber.json", "junit:target/report/cucumber.xml"}
       plugin = {
                "pretty",
                "html:target/cucumber/cucumber.html",       // or wherever you want
                "json:target/cucumber/report.json",          // 🔥 change this path!
                "junit:target/cucumber/cucumber.xml"
        }
)
public class APIRunner {
}
