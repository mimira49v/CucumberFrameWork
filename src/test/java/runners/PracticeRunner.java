package runners;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;


@CucumberOptions(
            features = "src/test/resources/featuresPractice/", //CUZ OF THIS EXECUTED DELTA AND SYNTAX
            glue = "steps",
            dryRun = false,  // <<< this enables actual execution
            monochrome = true,
            // tags = "@api",
            plugin = {
                    "html:target/cucumber/report.html",
                    "pretty",
                    "json:target/cucumber/report.json",
                    "rerun:target/failed.txt"
            }
    )

    public class PracticeRunner extends AbstractTestNGCucumberTests {
    @Override
    @DataProvider(parallel = true)
    public Object[][] scenarios() {
        return super.scenarios();
    }
}

