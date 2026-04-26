package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 * BookingTest.java
 * ─────────────────────────────────────────────────────────────────────────
 * TestNG test class for the full booking flow:
 *   Login → Search → Ride List → Book → Confirmation
 *
 * Selenium concepts demonstrated:
 *   ✅ Locators   — By.id, By.cssSelector, By.xpath
 *   ✅ Actions    — sendKeys(), click(), getText(), clear()
 *   ✅ Waits      — Explicit wait with ExpectedConditions
 *   ✅ Assertions — assertEquals, assertTrue, assertFalse
 *   ✅ Select     — Dropdown handling
 *   ✅ Navigation — Multi-page flow
 * ─────────────────────────────────────────────────────────────────────────
 */
public class BookingTest extends BaseTest {

    /**
     * @BeforeClass — Login once before all booking tests start.
     * Session is maintained via sessionStorage across pages.
     */
    @BeforeClass
    public void loginFirst() {
        driver.get(BASE_URL);

        driver.findElement(By.id("email")).sendKeys(VALID_EMAIL);
        driver.findElement(By.id("password")).sendKeys(VALID_PASSWORD);
        driver.findElement(By.id("loginBtn")).click();

        wait.until(ExpectedConditions.urlContains("search.html"));
        System.out.println("✅ Pre-condition: Logged in — on Search page");
    }

    // ────────────────────────────────────────────────────────────────────
    // TC01 — Verify search page loaded
    // ────────────────────────────────────────────────────────────────────
    @Test(priority = 1, description = "Search Page Loads After Login")
    public void testSearchPageLoaded() {
        String title = driver.getTitle();
        Assert.assertTrue(title.contains("Search"),
            "❌ Not on search page. Title: " + title);

        Assert.assertTrue(driver.findElement(By.id("source")).isDisplayed(),
            "❌ Source field not visible");
        Assert.assertTrue(driver.findElement(By.id("destination")).isDisplayed(),
            "❌ Destination field not visible");
        Assert.assertTrue(driver.findElement(By.id("searchBtn")).isDisplayed(),
            "❌ Search button not visible");

        System.out.println("✅ TC01 PASSED — Search page loaded");
    }

    // ────────────────────────────────────────────────────────────────────
    // TC02 — Empty search shows error
    // ────────────────────────────────────────────────────────────────────
    @Test(priority = 2, description = "Empty Search Shows Error")
    public void testEmptySearchShowsError() {
        driver.findElement(By.id("source")).clear();
        driver.findElement(By.id("destination")).clear();
        driver.findElement(By.id("searchBtn")).click();

        WebElement error = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("errorSearch"))
        );

        Assert.assertTrue(error.isDisplayed(), "❌ Error not shown for empty search");
        System.out.println("✅ TC02 PASSED — Empty search error: " + error.getText());
    }

    // ────────────────────────────────────────────────────────────────────
    // TC03 — Fill search form → navigate to ride list
    // ────────────────────────────────────────────────────────────────────
    @Test(priority = 3, description = "Fill Search Form and Go to Ride List")
    public void testSearchRide() {
        // ID locator for source
        WebElement source = driver.findElement(By.id("source"));
        source.clear();
        source.sendKeys("Mumbai");

        // CSS selector for destination
        WebElement dest = driver.findElement(By.cssSelector("#destination"));
        dest.clear();
        dest.sendKeys("Pune");

        // Date field
        driver.findElement(By.id("rideDate")).sendKeys("2025-12-25");

        // Dropdown — Select class
        Select seats = new Select(driver.findElement(By.id("seats")));
        seats.selectByValue("2");
        Assert.assertEquals(seats.getFirstSelectedOption().getText(), "2 Seats",
            "❌ Seat selection wrong");

        // Click search
        driver.findElement(By.id("searchBtn")).click();

        // Wait for rides page
        wait.until(ExpectedConditions.urlContains("rides.html"));
        Assert.assertTrue(driver.getCurrentUrl().contains("rides.html"),
            "❌ Not on rides page");

        System.out.println("✅ TC03 PASSED — Navigated to rides page");
    }

    // ────────────────────────────────────────────────────────────────────
    // TC04 — Verify ride cards on ride list page
    // ────────────────────────────────────────────────────────────────────
    @Test(priority = 4, description = "Ride Cards Are Displayed")
    public void testRideCardsDisplayed() {
        // Verify route summary
        WebElement summary = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("routeSummary"))
        );
        String summaryText = summary.getText();
        Assert.assertTrue(summaryText.contains("Mumbai"),
            "❌ 'Mumbai' not in summary: " + summaryText);
        Assert.assertTrue(summaryText.contains("Pune"),
            "❌ 'Pune' not in summary: " + summaryText);

        // Verify first ride card visible (By.id)
        Assert.assertTrue(driver.findElement(By.id("ride-1")).isDisplayed(),
            "❌ Ride card 1 not visible");

        // Verify driver name with XPath
        WebElement driver1 = driver.findElement(By.xpath("//h3[@id='driver1']"));
        Assert.assertTrue(driver1.isDisplayed(), "❌ Driver name not visible");

        // Verify Book Now button
        WebElement bookBtn = driver.findElement(By.id("bookBtn1"));
        Assert.assertEquals(bookBtn.getText(), "Book Now",
            "❌ Button text mismatch");

        System.out.println("✅ TC04 PASSED — Ride cards visible, driver: " + driver1.getText());
    }

    // ────────────────────────────────────────────────────────────────────
    // TC05 — Click Book Now → verify confirmation message (KEY assertion)
    // ────────────────────────────────────────────────────────────────────
    @Test(priority = 5, description = "Book Ride and Verify Confirmation Message")
    public void testBookRideAndConfirm() {
        // Wait for button to be clickable
        WebElement bookBtn = wait.until(
            ExpectedConditions.elementToBeClickable(By.id("bookBtn1"))
        );
        bookBtn.click();

        // Wait for confirmation page
        wait.until(ExpectedConditions.urlContains("confirm.html"));
        Assert.assertTrue(driver.getCurrentUrl().contains("confirm.html"),
            "❌ Not on confirmation page");

        // ── MAIN ASSERTION — what examiner looks for ──
        WebElement confirmMsg = wait.until(
            ExpectedConditions.visibilityOfElementLocated(By.id("confirmMsg"))
        );
        String confirmText = confirmMsg.getText();

        Assert.assertEquals(confirmText, "Ride Booked Successfully!",
            "❌ Confirmation message wrong. Got: " + confirmText);

        System.out.println("✅ TC05 PASSED — Confirmation: '" + confirmText + "'");
    }

    // ────────────────────────────────────────────────────────────────────
    // TC06 — Validate all booking details on confirmation page
    // ────────────────────────────────────────────────────────────────────
    @Test(priority = 6, description = "Validate All Booking Details")
    public void testBookingDetailsValidation() {
        // Booking ID format check
        String bookingId = driver.findElement(By.id("bookingId")).getText();
        Assert.assertTrue(bookingId.startsWith("#RDE-"),
            "❌ Booking ID format wrong: " + bookingId);

        // Driver name not empty
        String driverName = driver.findElement(By.id("driverName")).getText();
        Assert.assertFalse(driverName.isEmpty(), "❌ Driver name empty");

        // Route contains arrow (XPath)
        String route = driver.findElement(
            By.xpath("//*[@id='routeInfo']")
        ).getText();
        Assert.assertTrue(route.contains("→"),
            "❌ Route missing arrow: " + route);

        // Status = Confirmed
        String status = driver.findElement(By.id("bookingStatus")).getText();
        Assert.assertEquals(status, "Confirmed",
            "❌ Status wrong: " + status);

        System.out.println("✅ TC06 PASSED — All details valid");
        System.out.println("   Booking ID : " + bookingId);
        System.out.println("   Driver     : " + driverName);
        System.out.println("   Route      : " + route);
        System.out.println("   Status     : " + status);
        System.out.println("────────────────────────────────────────────");
        System.out.println("   FULL FLOW COMPLETE ✅");
        System.out.println("   Login → Search → Rides → Booked!");
        System.out.println("────────────────────────────────────────────");
    }
}
