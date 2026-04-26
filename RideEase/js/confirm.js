// confirm.js – Populates booking confirmation details
window.onload = function() {
  const driver    = sessionStorage.getItem("bookedDriver") || "Arjun Kumar";
  const route     = sessionStorage.getItem("bookedRoute")  || "Mumbai → Pune";
  const bookingId = sessionStorage.getItem("bookingId")    || "#RDE-00123";

  document.getElementById("driverName").textContent = driver;
  document.getElementById("routeInfo").textContent  = route;
  document.getElementById("bookingId").textContent  = bookingId;

  // These IDs are used by Selenium for getText() validation
  document.getElementById("confirmMsg").textContent    = "Ride Booked Successfully!";
  document.getElementById("confirmSubMsg").textContent = "Your ride with " + driver + " is confirmed.";
  document.getElementById("bookingStatus").textContent = "Confirmed";
};

function logout() {
  sessionStorage.clear();
  window.location.href = "../index.html";
}
