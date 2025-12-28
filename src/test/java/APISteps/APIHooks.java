package APISteps;

import io.cucumber.java.*;

public class APIHooks {

    @Before
    public void beforeAPIScenario(Scenario scenario) {
        System.out.println("═══════════════════════════════════════════════════════");
        System.out.println("Starting API Test: " + scenario.getName());
        System.out.println("Tags: " + scenario.getSourceTagNames());
        System.out.println("═══════════════════════════════════════════════════════");
    }

    @After
    public void afterAPIScenario(Scenario scenario) {
        System.out.println("═══════════════════════════════════════════════════════");
        if (scenario.isFailed()) {
            System.out.println("API Test FAILED: " + scenario.getName());
            // Log additional debugging info if needed
        } else {
            System.out.println("API Test PASSED: " + scenario.getName());
        }
        System.out.println("Status: " + scenario.getStatus());
        System.out.println("═══════════════════════════════════════════════════════");
    }

    // Optional: Hook that runs once before all scenarios
    @BeforeAll
    public static void beforeAllScenarios() {
        System.out.println("\nAPI Test Suite Starting...\n");
    }

    // Optional: Hook that runs once after all scenarios
    @AfterAll
    public static void afterAllScenarios() {
        System.out.println("\nAPI Test Suite Completed!\n");
    }
}
