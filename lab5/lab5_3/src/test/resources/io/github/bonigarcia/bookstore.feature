Feature: Book Search on Online Library

  Scenario: Search for a book by title
    Given the user is on the library homepage
    When the user searches for the book "Harry Potter and the Sorcerer's Stone"
    Then the search results should display "Harry Potter and the Sorcerer's Stone" by "J.K. Rowling"

  Scenario: Search for books by author
    Given the user is on the library homepage
    When the user searches for books by "J.K. Rowling"
    Then the search results should show books written by "J.K. Rowling"

  Scenario: Search for a book that does not exist
    Given the user is on the library homepage
    When the user searches for the book "Unknown Book"
    Then the search results should display a message "No books found"