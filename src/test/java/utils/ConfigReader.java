package utils;

/**
 * ConfigReader.java
 * ─────────────────────────────────────────────────────────────────────────
 * Utility class: central store for all configuration constants.
 *
 * In a production project these would be read from a config.properties file.
 * For this micro-project, constants are defined directly here.
 * ─────────────────────────────────────────────────────────────────────────
 */
public class ConfigReader {

    // ── Website URL (update to match your machine) ──────────────────────
    public static final String BASE_URL =
        "http://127.0.0.1:5500/RideEase/index.html";

    // ── Login credentials ────────────────────────────────────────────────
    public static final String VALID_EMAIL    = "test@rideease.com";
    public static final String VALID_PASSWORD = "test123";
    public static final String WRONG_EMAIL    = "wrong@email.com";
    public static final String WRONG_PASSWORD = "wrong123";

    // ── URL identifiers for assertions ───────────────────────────────────
    public static final String SEARCH_PAGE  = "search.html";
    public static final String RIDES_PAGE   = "rides.html";
    public static final String CONFIRM_PAGE = "confirm.html";

    // ── Expected text values ─────────────────────────────────────────────
    public static final String EXPECTED_CONFIRM_MSG = "Ride Booked Successfully!";
    public static final String EXPECTED_STATUS      = "Confirmed";

    // ── Wait durations (seconds) ─────────────────────────────────────────
    public static final int IMPLICIT_WAIT = 5;
    public static final int EXPLICIT_WAIT = 10;

    // Prevent instantiation
    private ConfigReader() {}
}
