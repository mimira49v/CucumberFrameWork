package runners;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


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

    public class PracticeRunner extends AbstractTestNGCucumberTests {

    }

