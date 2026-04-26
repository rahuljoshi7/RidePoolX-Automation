

Feature: User Login

  Background:
    Given I open the RideEase login page

  Scenario: Successful login with valid credentials
    When I enter email "test@rideease.com" and password "test123"
    And I click the Login button
    Then I should see the success message
    And I should be redirected to the search page

  Scenario: Login fails with invalid credentials
    When I enter email "wrong@test.com" and password "wrong123"
    And I click the Login button
    Then I should see an error message containing "Invalid"

  Scenario: Login fails when fields are empty
    When I click the Login button
    Then I should see an error message containing "Please enter"
