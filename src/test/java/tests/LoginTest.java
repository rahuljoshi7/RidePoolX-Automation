package tests;

import base.BaseTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * LoginTest.java
 * ─────────────────────────────────────────────────────────────────────────
 * TestNG test class for the Login Page (index.html)
 *
 * Selenium concepts demonstrated:
 *   ✅ Locators      — By.id, By.cssSelector, By.xpath
 *   ✅ Actions       — sendKeys(), click(), clear(), getText()
 *   ✅ Waits         — Implicit (BaseTest) + Explicit (ExpectedConditions)
 *   ✅ Assertions    — Assert.assertEquals, assertTrue, assertFalse
 *   ✅ Annotations   — @BeforeMethod, @Test(priority)
 * ─────────────────────────────────────────────────────────────────────────
 */
public class LoginTest extends BaseTest {

    // ── Locators stored as fields (reusable, readable) ──────────────────
    private final By emailField    = By.id("email");
    private final By passwordField = By.id("password");
    private final By loginButton   = By.id("loginBtn");
    private final By errorMsg      = By.id("errorMsg");
    private final By successMsg    = By.id("successMsg");

    /**
     * @BeforeMethod — resets to login page before EACH test method.
     */
    @BeforeMethod
    public void goToLoginPage() {
        driver.get(BASE_URL);
        System.out.println("\n▶ Navigated to Login page");
    }

    // ────────────────────────────────────────────────────────────────────
    // TC01 — Page title verification
    // ────────────────────────────────────────────────────────────────────
    @Test(priority = 1, description = "Verify Login Page Title")
    public void testLoginPageTitle() {
        String title = driver.getTitle();
        System.out.println("Page title: " + title);

        Assert.assertTrue(title.contains("RideEase"),
            "❌ Expected title to contain 'RideEase', got: " + title);

        System.out.println("✅ TC01 PASSED — Page title correct");
    }

    // ────────────────────────────────────────────────────────────────────
    // TC02 — Verify all login fields are present and visible
    // ────────────────────────────────────────────────────────────────────
    @Test(priority = 2, description = "Verify Login Fields Are Displayed")
    public void testLoginFieldsPresent() {
        Assert.assertTrue(driver.findElement(emailField).isDisplayed(),
            "❌ Email field not visible");
        Assert.assertTrue(driver.findElement(passwordField).isDisplayed(),
            "❌ Password field not visible");
        Assert.assertTrue(driver.findElement(loginButton).isDisplayed(),
            "❌ Login button not visible");

        System.out.println("✅ TC02 PASSED — All fields visible");
    }

    // ────────────────────────────────────────────────────────────────────
    // TC03 — Empty fields → error message shown
    // ────────────────────────────────────────────────────────────────────
    @Test(priority = 3, description = "Empty Login Shows Error Message")
    public void testEmptyLoginShowsError() {
        // Click login without entering anything
        driver.findElement(loginButton).click();

        // Explicit wait — wait until error div becomes visible
        WebElement error = wait.until(
            ExpectedConditions.visibilityOfElementLocated(errorMsg)
        );

        Assert.assertTrue(error.isDisplayed(),
            "❌ Error message not shown for empty login");
        Assert.assertFalse(error.getText().isEmpty(),
            "❌ Error message text is empty");

        System.out.println("✅ TC03 PASSED — Error shown: " + error.getText());
    }

    // ────────────────────────────────────────────────────────────────────
    // TC04 — Wrong credentials → error message
    // ────────────────────────────────────────────────────────────────────
    @Test(priority = 4, description = "Invalid Credentials Show Error")
    public void testInvalidLoginShowsError() {
        // sendKeys() — type into fields
        driver.findElement(emailField).sendKeys("wrong@email.com");
        driver.findElement(passwordField).sendKeys("wrongpass");
        driver.findElement(loginButton).click();

        WebElement error = wait.until(
            ExpectedConditions.visibilityOfElementLocated(errorMsg)
        );

        String errorText = error.getText();
        Assert.assertTrue(error.isDisplayed(), "❌ Error not shown");
        Assert.assertTrue(errorText.toLowerCase().contains("invalid"),
            "❌ Expected 'invalid' in error, got: " + errorText);

        System.out.println("✅ TC04 PASSED — Error: " + errorText);
    }

    // ────────────────────────────────────────────────────────────────────
    // TC05 — Valid credentials → success + redirect to search page
    // ────────────────────────────────────────────────────────────────────
    @Test(priority = 5, description = "Valid Login Redirects to Search Page")
    public void testValidLoginSuccess() {
        // CSS Selector locator (alternative to By.id)
        driver.findElement(By.cssSelector("#email")).clear();
        driver.findElement(By.cssSelector("#email")).sendKeys(VALID_EMAIL);

        driver.findElement(By.cssSelector("#password")).clear();
        driver.findElement(By.cssSelector("#password")).sendKeys(VALID_PASSWORD);

        driver.findElement(loginButton).click();

        // Wait for success message
        WebElement success = wait.until(
            ExpectedConditions.visibilityOfElementLocated(successMsg)
        );
        Assert.assertTrue(success.isDisplayed(), "❌ Success message not shown");

        // Wait for redirect
        wait.until(ExpectedConditions.urlContains("search.html"));
        Assert.assertTrue(driver.getCurrentUrl().contains("search.html"),
            "❌ Not redirected to search page");

        System.out.println("✅ TC05 PASSED — Redirected to: " + driver.getCurrentUrl());
    }

    // ────────────────────────────────────────────────────────────────────
    // TC06 — Locate login button using XPath
    // ────────────────────────────────────────────────────────────────────
    @Test(priority = 6, description = "Find Login Button via XPath")
    public void testLoginButtonXPath() {
        // XPath locator — demonstrates XPath for examiner
        WebElement btn = driver.findElement(By.xpath("//button[@id='loginBtn']"));

        Assert.assertTrue(btn.isDisplayed(), "❌ Button not found via XPath");
        Assert.assertEquals(btn.getText(), "Login",
            "❌ Button text mismatch");

        System.out.println("✅ TC06 PASSED — XPath found button: " + btn.getText());
    }
}
