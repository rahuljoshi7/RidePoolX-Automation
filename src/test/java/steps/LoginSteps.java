package steps;

import base.CucumberBase;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

/**
 * LoginSteps.java
 * ─────────────────────────────────────────────────────────────────────────
 * BDD Step Definitions for login.feature
 *
 * Each method maps to one Gherkin step using annotations:
 *   @Given  — precondition / setup
 *   @When   — user action
 *   @Then   — expected outcome / assertion
 *   @And    — continuation of previous step type
 *
 * This is the "glue code" that connects plain English scenarios
 * to actual Selenium WebDriver actions.
 * ─────────────────────────────────────────────────────────────────────────
 */
public class LoginSteps extends CucumberBase {

    @Before
    public void setup() {
        initDriver();
    }

    @After
    public void teardown() {
        quitDriver();
    }

    // ── Given ──────────────────────────────────────────────────────────

    @Given("I open the RideEase login page")
    public void i_open_the_login_page() {
        driver.get(BASE_URL);
        System.out.println("[BDD] Opened login page");
    }

    // ── When ───────────────────────────────────────────────────────────

    @When("I enter email {string} and password {string}")
    public void i_enter_credentials(String email, String password) {
        driver.findElement(By.id("email")).clear();
        driver.findElement(By.id("email")).sendKeys(email);

        driver.findElement(By.id("password")).clear();
        driver.findElement(By.id("password")).sendKeys(password);

        System.out.println("[BDD] Entered email: " + email);
    }

    @When("I click the Login button")
    public void i_click_login_button() {
        driver.findElement(By.id("loginBtn")).click();
        System.out.println("[BDD] Clicked Login button");
    }

    // ── Then ───────────────────────────────────────────────────────────

    @Then("I should see the success message")
    public void i_should_see_success_message() {
        WebElement success = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("successMsg"))
        );
        Assert.assertTrue(success.isDisplayed(), "❌ Success message not shown");
        System.out.println("[BDD] Success message: " + success.getText());
    }

    @Then("I should be redirected to the search page")
    public void i_should_be_redirected_to_search() {
        wait.until(ExpectedConditions.urlContains("search.html"));
        Assert.assertTrue(driver.getCurrentUrl().contains("search.html"),
            "❌ Not redirected to search page");
        System.out.println("[BDD] Redirected to: " + driver.getCurrentUrl());
    }

    @Then("I should see an error message containing {string}")
    public void i_should_see_error_containing(String expectedText) {
        WebElement error = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("errorMsg"))
        );
        Assert.assertTrue(error.isDisplayed(), "❌ Error message not shown");
        Assert.assertTrue(error.getText().contains(expectedText),
            "❌ Expected '" + expectedText + "' in error, got: " + error.getText());
        System.out.println("[BDD] Error message: " + error.getText());
    }
}
