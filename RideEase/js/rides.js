// rides.js – Populates ride list and handles booking
window.onload = function() {
  const source = sessionStorage.getItem("source") || "Mumbai";
  const dest   = sessionStorage.getItem("destination") || "Pune";

  document.getElementById("routeSummary").textContent =
    `Showing rides: ${source} → ${dest}`;

  // Update all ride card routes dynamically
  ["1","2","3","4"].forEach(function(n) {
    const s = document.getElementById("src" + n);
    const d = document.getElementById("dst" + n);
    if (s) s.textContent = source;
    if (d) d.textContent = dest;
  });
};

function bookRide(rideId, driverName) {
  const source = sessionStorage.getItem("source") || "Mumbai";
  const dest   = sessionStorage.getItem("destination") || "Pune";

  // Store booking info for confirmation page
  sessionStorage.setItem("bookedDriver", driverName);
  sessionStorage.setItem("bookedRoute", source + " → " + dest);
  sessionStorage.setItem("bookingId", "#RDE-" + Math.floor(10000 + Math.random() * 90000));

  window.location.href = "confirm.html";
}

function logout() {
  sessionStorage.clear();
  window.location.href = "../index.html";
}
