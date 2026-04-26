package steps;

import base.CucumberBase;
import io.cucumber.java.en.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

/**
 * BookingSteps.java
 * ─────────────────────────────────────────────────────────────────────────
 * BDD Step Definitions for booking.feature
 *
 * Covers the full ride booking flow:
 *   Background (login) → Search → Ride list → Book → Confirmation
 *
 * Shares driver with LoginSteps via CucumberBase static fields.
 * ─────────────────────────────────────────────────────────────────────────
 */
public class BookingSteps extends CucumberBase {

    // ── Background steps (reused from LoginSteps via shared driver) ────

    @Given("I login with valid credentials")
    public void i_login_with_valid_credentials() {
        driver.findElement(By.id("email")).sendKeys(VALID_EMAIL);
        driver.findElement(By.id("password")).sendKeys(VALID_PASSWORD);
        driver.findElement(By.id("loginBtn")).click();

        wait.until(ExpectedConditions.urlContains("search.html"));
        System.out.println("[BDD] Logged in successfully");
    }

    // ── Given ──────────────────────────────────────────────────────────

    @Given("I am on the search page")
    public void i_am_on_search_page() {
        Assert.assertTrue(driver.getCurrentUrl().contains("search.html"),
            "❌ Not on search page");
        System.out.println("[BDD] On search page");
    }

    // ── When ───────────────────────────────────────────────────────────

    @When("I enter source {string} and destination {string}")
    public void i_enter_source_and_destination(String source, String dest) {
        driver.findElement(By.id("source")).clear();
        driver.findElement(By.id("source")).sendKeys(source);

        driver.findElement(By.id("destination")).clear();
        driver.findElement(By.id("destination")).sendKeys(dest);

        System.out.println("[BDD] Source: " + source + ", Destination: " + dest);
    }

    @When("I click the Search button")
    public void i_click_search_button() {
        driver.findElement(By.id("searchBtn")).click();
        wait.until(ExpectedConditions.urlContains("rides.html"));
        System.out.println("[BDD] Clicked Search, on rides page");
    }

    @When("I click the Book Now button on the first ride")
    public void i_click_book_now() {
        WebElement bookBtn = wait.until(
            ExpectedConditions.elementToBeClickable(By.id("bookBtn1"))
        );
        bookBtn.click();
        wait.until(ExpectedConditions.urlContains("confirm.html"));
        System.out.println("[BDD] Clicked Book Now, on confirm page");
    }

    // ── Then ───────────────────────────────────────────────────────────

    @Then("I should see the list of available rides")
    public void i_should_see_ride_list() {
        Assert.assertTrue(driver.findElement(By.id("ride-1")).isDisplayed(),
            "❌ Ride list not shown");
        Assert.assertTrue(driver.findElement(By.id("bookBtn1")).isDisplayed(),
            "❌ Book Now button not visible");
        System.out.println("[BDD] Ride list visible ✅");
    }

    @Then("I should see the confirmation message {string}")
    public void i_should_see_confirmation(String expectedMsg) {
        WebElement confirmMsg = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("confirmMsg"))
        );
        // ── KEY ASSERTION ──
        Assert.assertEquals(confirmMsg.getText(), expectedMsg,
            "❌ Confirmation message wrong. Got: " + confirmMsg.getText());
        System.out.println("[BDD] Confirmation: " + confirmMsg.getText());
    }

    @Then("the booking status should be {string}")
    public void booking_status_should_be(String expectedStatus) {
        String status = driver.findElement(By.id("bookingStatus")).getText();
        Assert.assertEquals(status, expectedStatus,
            "❌ Status wrong. Got: " + status);
        System.out.println("[BDD] Status: " + status + " ✅");
    }
}
