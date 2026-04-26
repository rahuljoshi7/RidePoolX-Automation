// login.js – Handles login validation
// Valid credentials (for demo/Selenium testing)
const VALID_EMAIL = "test@rideease.com";
const VALID_PASSWORD = "test123";

function handleLogin() {
  const email    = document.getElementById("email").value.trim();
  const password = document.getElementById("password").value.trim();
  const errorMsg   = document.getElementById("errorMsg");
  const successMsg = document.getElementById("successMsg");

  // Reset messages
  errorMsg.classList.add("hidden");
  successMsg.classList.add("hidden");

  // Validation
  if (!email || !password) {
    errorMsg.textContent = "Please enter both email and password.";
    errorMsg.classList.remove("hidden");
    return;
  }

  if (email === VALID_EMAIL && password === VALID_PASSWORD) {
    successMsg.classList.remove("hidden");
    sessionStorage.setItem("loggedIn", "true");
    sessionStorage.setItem("userEmail", email);
    // Redirect after brief delay
    setTimeout(() => {
      window.location.href = "pages/search.html";
    }, 1200);
  } else {
    errorMsg.textContent = "Invalid email or password. Try again.";
    errorMsg.classList.remove("hidden");
  }
}

// Allow Enter key to trigger login
document.addEventListener("keydown", function(e) {
  if (e.key === "Enter") handleLogin();
});
