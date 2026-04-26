# 🚗 RideEase – Selenium Test Website

A static HTML/CSS/JS website built as a test environment for Selenium automation.

---

## 📁 Project Structure

```
RideEase/
├── index.html              ← Login Page
├── css/
│   └── style.css           ← All styles
├── js/
│   ├── login.js            ← Login logic
│   ├── search.js           ← Search logic
│   ├── rides.js            ← Ride list + booking logic
│   └── confirm.js          ← Confirmation page logic
└── pages/
    ├── search.html         ← Search Ride Page
    ├── rides.html          ← Available Rides Page
    └── confirm.html        ← Booking Confirmation Page
```

---

## 🔐 Login Credentials (for Selenium)

| Field    | Value              |
|----------|--------------------|
| Email    | test@rideease.com  |
| Password | test123            |

---

## 🤖 Selenium Element IDs Reference

### Login Page (index.html)
| Element         | ID / Selector         | Action            |
|-----------------|-----------------------|-------------------|
| Email input     | `#email`              | `sendKeys()`      |
| Password input  | `#password`           | `sendKeys()`      |
| Login button    | `#loginBtn`           | `click()`         |
| Error message   | `#errorMsg`           | `getText()`       |
| Success message | `#successMsg`         | `getText()`       |

### Search Page (pages/search.html)
| Element           | ID / Selector   | Action        |
|-------------------|-----------------|---------------|
| Source input      | `#source`       | `sendKeys()`  |
| Destination input | `#destination`  | `sendKeys()`  |
| Date input        | `#rideDate`     | `sendKeys()`  |
| Seats dropdown    | `#seats`        | `Select`      |
| Search button     | `#searchBtn`    | `click()`     |
| Error message     | `#errorSearch`  | `getText()`   |

### Ride List Page (pages/rides.html)
| Element         | ID / Selector   | Action        |
|-----------------|-----------------|---------------|
| Ride card 1     | `#ride-1`       | `findElement` |
| Driver name 1   | `#driver1`      | `getText()`   |
| Book button 1   | `#bookBtn1`     | `click()`     |
| Book button 2   | `#bookBtn2`     | `click()`     |
| Book button 3   | `#bookBtn3`     | `click()`     |
| Route summary   | `#routeSummary` | `getText()`   |

### Confirmation Page (pages/confirm.html)
| Element           | ID / Selector    | Action      |
|-------------------|------------------|-------------|
| Confirm message   | `#confirmMsg`    | `getText()` |
| Sub message       | `#confirmSubMsg` | `getText()` |
| Booking ID        | `#bookingId`     | `getText()` |
| Driver name       | `#driverName`    | `getText()` |
| Route info        | `#routeInfo`     | `getText()` |
| Booking status    | `#bookingStatus` | `getText()` |

---

## ▶️ How to Run

1. Open `index.html` directly in Chrome (no server needed)
2. Or use VS Code Live Server extension
3. Point Selenium WebDriver to the full file path:
   ```
   driver.get("file:///path/to/RideEase/index.html");
   ```

---

## ✅ Selenium Test Cases to Demonstrate

1. **Valid Login** – Enter correct credentials → check redirect to search page
2. **Invalid Login** – Wrong password → check `#errorMsg` text
3. **Empty Login** – Click login with no input → check error appears
4. **Search Ride** – Fill source/destination → click Search → verify ride list loads
5. **Book a Ride** – Click `#bookBtn1` → verify `#confirmMsg` = "Ride Booked Successfully!"
6. **Verify Booking Details** – `getText()` on `#driverName`, `#routeInfo`, `#bookingStatus`
