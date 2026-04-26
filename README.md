# 🚗 RidePoolX-Automation
### End-to-End Automation using AI, BDD and CI/CD

---

## 📁 Complete Project Structure

```
RidePoolX-Automation/
│
├── .github/
│   └── workflows/
│       └── selenium-tests.yml        ← CI/CD: GitHub Actions pipeline
│
├── src/test/
│   ├── java/
│   │   ├── base/
│   │   │   ├── BaseTest.java         ← Browser setup/teardown for TestNG
│   │   │   └── CucumberBase.java     ← Shared driver for BDD steps
│   │   │
│   │   ├── tests/
│   │   │   ├── LoginTest.java        ← 6 TestNG login test cases
│   │   │   └── BookingTest.java      ← 6 TestNG booking flow tests
│   │   │
│   │   ├── steps/
│   │   │   ├── LoginSteps.java       ← BDD glue code for login.feature
│   │   │   ├── BookingSteps.java     ← BDD glue code for booking.feature
│   │   │   └── CucumberRunner.java   ← Runs all .feature files
│   │   │
│   │   └── utils/
│   │       └── ConfigReader.java     ← Constants (URLs, credentials)
│   │
│   └── resources/
│       └── features/
│           ├── login.feature         ← BDD: Login scenarios (Gherkin)
│           └── booking.feature       ← BDD: Booking scenarios (Gherkin)
│
├── testng.xml                        ← TestNG suite runner
└── pom.xml                           ← Maven: Selenium + TestNG + Cucumber
```

---

## ⚙️ Setup Instructions

### Prerequisites
| Tool | Version |
|------|---------|
| Java JDK | 11 or higher |
| Maven | 3.6+ |
| Google Chrome | Latest |
| IntelliJ IDEA | Any |

### Step 1 — Update BASE_URL
Open `src/test/java/base/BaseTest.java` and update:
```java
protected static final String BASE_URL =
    "file:///C:/Users/YourName/Desktop/RideEase/index.html";
```
Also update the same in `CucumberBase.java`.

### Step 2 — Import Project
1. Open IntelliJ IDEA
2. **File → Open** → select `RidePoolX-Automation` folder
3. Wait for Maven to download all dependencies (~2 min first time)

### Step 3 — Run Tests

| Method | Command |
|--------|---------|
| All TestNG tests | `mvn test` |
| Specific class | Right-click `LoginTest.java` → Run |
| BDD (Cucumber) | Right-click `CucumberRunner.java` → Run |
| via testng.xml | Right-click `testng.xml` → Run |

---

## 🤖 Part 1: AI Integration

**What was done:**
- Used **Claude AI (Anthropic)** to generate BDD Gherkin scenarios
- Prompt used: *"Generate Gherkin BDD test scenarios for a ride-booking application login and booking flow"*
- AI output was reviewed, modified, and validated by the developer
- This is standard industry practice: **AI-assisted test generation**

**Evidence in code:**
- See the comment block at the top of `login.feature` and `booking.feature`

**What AI helps with in real testing:**
- Generating test scenarios from requirements
- Writing step definition skeletons
- Suggesting edge cases and negative test scenarios
- Reviewing test coverage

---

## 🥒 Part 2: BDD (Behaviour Driven Development) with Cucumber

**What is BDD?**
BDD means writing tests in plain English (readable by everyone — developers, testers, business stakeholders) before writing code.

**Tools used:** Cucumber + Gherkin

**How it works:**
```
Feature File (.feature)     →     Step Definitions (.java)     →     Selenium Actions
─────────────────────────────────────────────────────────────────────────────────────
Given I open the login page  →  driver.get(BASE_URL)            →  Chrome opens page
When I click Login button    →  driver.findElement(...).click()  →  Button is clicked
Then I see success message   →  Assert.assertTrue(...)          →  Test passes/fails
```

**Gherkin keywords:**
| Keyword | Meaning |
|---------|---------|
| `Feature` | Name of the feature being tested |
| `Scenario` | One test case |
| `Background` | Steps that run before every scenario |
| `Given` | Precondition/setup |
| `When` | User action |
| `Then` | Expected outcome (assertion) |
| `And` | Continuation of previous step |

---

## 🔄 Part 3: CI/CD with GitHub Actions

**What is CI/CD?**
- **CI** (Continuous Integration): Every code push automatically runs all tests
- **CD** (Continuous Deployment): After tests pass, code can be deployed

**Pipeline flow:**
```
Developer pushes code to GitHub
         ↓
GitHub Actions detects push
         ↓
Ubuntu server spun up automatically
         ↓
Java 11 + Chrome installed
         ↓
mvn test -Dheadless=true runs
         ↓
All Selenium tests execute
         ↓
Test reports saved as artifacts
         ↓
Pass ✅ or Fail ❌ shown on GitHub
```

**How to set it up:**
1. Push this project to a GitHub repository
2. The `.github/workflows/selenium-tests.yml` file is automatically detected
3. Go to **Actions** tab on GitHub to see runs

---

## ✅ All Test Cases

### LoginTest.java (TestNG)
| # | Test | What it checks |
|---|------|---------------|
| TC01 | Page title | Title contains "RideEase" |
| TC02 | Fields present | Email, Password, Button visible |
| TC03 | Empty login | Error message shown |
| TC04 | Wrong credentials | Error contains "Invalid" |
| TC05 | Valid login | Redirect to search.html |
| TC06 | XPath locator | Button found via `//button[@id='loginBtn']` |

### BookingTest.java (TestNG)
| # | Test | What it checks |
|---|------|---------------|
| TC01 | Search page loaded | Title + fields visible |
| TC02 | Empty search | Error shown |
| TC03 | Fill & submit | Navigates to rides.html |
| TC04 | Ride cards | Cards + driver names + Book Now button |
| TC05 | Book ride | `confirmMsg` = "Ride Booked Successfully!" |
| TC06 | Booking details | ID format, driver, route, status = "Confirmed" |

### login.feature (BDD)
- Successful login → redirect to search page
- Invalid login → error message
- Empty login → error message

### booking.feature (BDD)
- Search for ride → ride list shown
- Book ride → confirmation message + status

---

## 🔐 Login Credentials

| Field | Value |
|-------|-------|
| Email | `test@rideease.com` |
| Password | `test123` |

---

## 💬 Viva Q&A

**Q: What is Selenium WebDriver?**
A: A tool for automating web browser actions like clicking, typing, and navigating pages. We use it to automate testing of the RideEase website.

**Q: What is TestNG?**
A: A testing framework for Java. It manages test execution, annotations like @Test/@BeforeClass, and generates test reports.

**Q: What is the difference between implicit and explicit wait?**
A: Implicit wait applies globally — driver waits up to N seconds for any element. Explicit wait is for a specific element with a specific condition (like `visibilityOfElementLocated`).

**Q: What is BDD?**
A: Behaviour Driven Development — writing tests in plain English using Gherkin so business stakeholders can understand them. We use Cucumber to map Gherkin steps to Java Selenium code.

**Q: What is Gherkin?**
A: The plain English language for writing BDD test scenarios using keywords: Given, When, Then, And, But.

**Q: What is Cucumber?**
A: A BDD framework that reads `.feature` files and maps each Gherkin step to a Java method (step definition) using annotations like `@Given`, `@When`, `@Then`.

**Q: What is CI/CD?**
A: Continuous Integration / Continuous Deployment. Every time we push code to GitHub, GitHub Actions automatically runs all our Selenium tests on a cloud server, ensuring nothing is broken.

**Q: What is GitHub Actions?**
A: A CI/CD platform built into GitHub. We define a YAML workflow file that tells it what to do when code is pushed (install Java, Chrome, run mvn test).

**Q: What is the role of AI in your project?**
A: We used Claude AI to generate BDD test scenarios (Gherkin feature files). The AI suggested test cases based on the application's login and booking features, which we then reviewed and implemented. This is AI-assisted test generation.

**Q: What is WebDriverManager?**
A: A library that automatically downloads the correct ChromeDriver version for your installed Chrome browser — no manual driver setup needed.

**Q: What is `Assert.assertEquals`?**
A: A TestNG assertion that checks if two values are exactly equal. If they differ, the test fails immediately with an error message.
