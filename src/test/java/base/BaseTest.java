package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.time.Duration;

/**
 * BaseTest.java
 * ─────────────────────────────────────────────────────────────────────────
 * Parent class for all TestNG test classes.
 * Responsibilities:
 *   - Launch Chrome browser before tests  (@BeforeClass)
 *   - Set implicit + explicit waits
 *   - Close browser after tests           (@AfterClass)
 *   - Support headless mode for CI/CD     (reads -Dheadless system property)
 *
 * All test classes (LoginTest, BookingTest) extend this.
 * Step definition classes (BDD) also extend this via CucumberBase.
 * ─────────────────────────────────────────────────────────────────────────
 */
public class BaseTest {

    // Shared WebDriver — accessible to all subclasses
    protected WebDriver driver;

    // Explicit wait — used for dynamic elements (better than Thread.sleep)
    protected WebDriverWait wait;

    // ── UPDATE THIS PATH to point to your RideEase folder ──────────────────
    // Windows : "file:///C:/Users/YourName/Desktop/RideEase/index.html"
    // Mac/Linux: "file:///home/yourname/Desktop/RideEase/index.html"
    protected static final String BASE_URL =
            "http://localhost:8090/index.html";

    // Login credentials — must match login.js in RideEase website
    protected static final String VALID_EMAIL    = "test@rideease.com";
    protected static final String VALID_PASSWORD = "test123";

    /**
     * @BeforeClass — runs ONCE before all @Test methods in the class.
     * Sets up ChromeDriver using WebDriverManager (no manual download needed).
     */
    @BeforeClass
    public void setUp() {
        // Auto-download correct ChromeDriver version
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");

        // Read headless flag — set to true by CI/CD pipeline
        String headless = System.getProperty("headless", "false");
        if (headless.equalsIgnoreCase("true")) {
            options.addArguments("--headless");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu");
            options.addArguments("--window-size=1920,1080");
        }

        driver = new ChromeDriver(options);

        // Implicit wait: driver waits up to 5s for elements to appear
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        // Explicit wait: used with ExpectedConditions for specific elements
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        System.out.println("✅ Browser launched | headless=" + headless);
    }

    /**
     * @AfterClass — runs ONCE after all @Test methods in the class.
     * Closes the browser and releases resources.
     */
    @AfterClass
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            System.out.println("✅ Browser closed");
        }
    }
}