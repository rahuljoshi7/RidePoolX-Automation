# ============================================================
# Feature: Ride Booking Flow — RideEase Application
# ============================================================
# AI-ASSISTED: These BDD scenarios were generated with the
# help of Claude AI (Anthropic) using the prompt:
#   "Generate BDD Gherkin scenarios for a complete ride
#    booking flow covering search, selection and confirmation"
# Scenarios were reviewed and validated by the developer.
# ============================================================

Feature: Ride Booking

  Background:
    Given I open the RideEase login page
    And I login with valid credentials

  Scenario: Search for a ride between two cities
    Given I am on the search page
    When I enter source "Mumbai" and destination "Pune"
    And I click the Search button
    Then I should see the list of available rides

  Scenario: Book a ride and see confirmation
    Given I am on the search page
    When I enter source "Mumbai" and destination "Pune"
    And I click the Search button
    And I click the Book Now button on the first ride
    Then I should see the confirmation message "Ride Booked Successfully!"
    And the booking status should be "Confirmed"
