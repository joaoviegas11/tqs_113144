Feature: Create and cancel a reservation

  Scenario: Create a reservation and attempt to cancel it with an invalid token
    Given the user is on the refectories page
    When the user selects a refectory
    And the user chooses a menu and makes a reservation
    Then a reservation token should be displayed

    When the user returns to the main page
    And the user navigates to the reservation lookup page
    And the user enters the token and checks the reservation
    And the user attempts to cancel the reservation
    Then a message should appear indicating that the token is invalid

Scenario: Create a reservation and check in with a valid token
  Given the user is on the refectories page
  When the user selects a refectory
  And the user chooses a menu and makes a reservation
  Then a reservation token should be displayed

  When the user returns to the main page
  And the user navigates to the employee check-in page
  And the user enters the token and confirms the check-in
  Then a message should appear indicating that the check-in was valid
