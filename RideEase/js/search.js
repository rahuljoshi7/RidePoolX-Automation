// search.js – Handles ride search
function searchRides() {
  const source      = document.getElementById("source").value.trim();
  const destination = document.getElementById("destination").value.trim();
  const errorSearch = document.getElementById("errorSearch");

  errorSearch.classList.add("hidden");

  if (!source || !destination) {
    errorSearch.classList.remove("hidden");
    return;
  }

  // Store search params and navigate to ride list
  sessionStorage.setItem("source", source);
  sessionStorage.setItem("destination", destination);
  window.location.href = "rides.html";
}

// Allow Enter key
document.addEventListener("keydown", function(e) {
  if (e.key === "Enter") searchRides();
});

function logout() {
  sessionStorage.clear();
  window.location.href = "../index.html";
}
