package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

/**
 * CucumberBase.java
 * ─────────────────────────────────────────────────────────────────────────
 * Shared base for BDD Step Definition classes.
 * Cannot use @BeforeClass/@AfterClass (those are TestNG-only).
 * Cucumber manages lifecycle via @Before/@After in the step definitions.
 *
 * Provides static driver/wait so they are shared across all step classes
 * within the same Cucumber scenario run.
 * ─────────────────────────────────────────────────────────────────────────
 */
public class CucumberBase {

    protected static WebDriver driver;
    protected static WebDriverWait wait;

    protected static final String BASE_URL =
            "http://localhost:9090/index.html";

    protected static final String VALID_EMAIL    = "test@rideease.com";
    protected static final String VALID_PASSWORD = "test123";

    public static void initDriver() {
        if (driver == null) {
            WebDriverManager.chromedriver().setup();

            ChromeOptions options = new ChromeOptions();
            options.addArguments("--start-maximized");

            String headless = System.getProperty("headless", "false");
            if (headless.equalsIgnoreCase("true")) {
                options.addArguments("--headless", "--no-sandbox",
                        "--disable-dev-shm-usage", "--disable-gpu",
                        "--window-size=1920,1080");
            }

            driver = new ChromeDriver(options);
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
            wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            System.out.println("✅ [Cucumber] Browser launched");
        }
    }

    public static void quitDriver() {
        if (driver != null) {
            driver.quit();
            driver = null;
            System.out.println("✅ [Cucumber] Browser closed");
        }
    }
}