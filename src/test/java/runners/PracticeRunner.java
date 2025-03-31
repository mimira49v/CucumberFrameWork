package runners;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;


    @RunWith(Cucumber.class)
    @CucumberOptions(
            features = "src/test/resources/featuresPractice/",
            glue = "steps",
            dryRun = true,
            monochrome = true,
            tags = "@SyntaxIframe",
            plugin = {
                    "html:target/cucumber/report.html",
                    "pretty",
                    "json:target/cucumber/report.json",
                    "rerun:target/failed.txt"
            }
    )

    public class PracticeRunner  {

    }

