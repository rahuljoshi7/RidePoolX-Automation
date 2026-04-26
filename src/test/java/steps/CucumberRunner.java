package steps;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

/**
 * CucumberRunner.java
 * ─────────────────────────────────────────────────────────────────────────
 * Entry point for running all Cucumber BDD feature files.
 *
 * @CucumberOptions:
 *   features — path to .feature files
 *   glue     — package containing step definition classes
 *   plugin   — report formats (pretty console + HTML report)
 *   monochrome — cleaner console output
 * ─────────────────────────────────────────────────────────────────────────
 */
@CucumberOptions(
    features  = "src/test/resources/features",
    glue      = "steps",
    plugin    = {
        "pretty",
        "html:target/cucumber-reports/cucumber-report.html"
    },
    monochrome = true
)
public class CucumberRunner extends AbstractTestNGCucumberTests {
    // AbstractTestNGCucumberTests handles test execution
    // No additional code needed here
}
