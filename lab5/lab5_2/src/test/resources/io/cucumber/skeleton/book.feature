Feature: Book search
  To allow a customer to find his favourite books quickly, the library must offer multiple ways to search for a book.

  Scenario: Search books by publication year
    Given a book with the title 'One good book', written by 'Anonymous', published in 2013-03-14
      And another book with the title 'Some other book', written by 'Tim Tomson', published in 2014-08-23
      And another book with the title 'How to cook a dino', written by 'Fred Flintstone', published in 2012-01-01
    When the customer searches for books published between 2013-01-01 and 2014-11-01
    Then 2 books should have been found
      And Book 1 should have the title 'Some other book'
      And Book 2 should have the title 'One good book'

  Scenario: Search books by author
      Given a book with the title 'The Hobbit', written by 'J.R.R. Tolkien', published in 1937-09-21
        And another book with the title 'The Fellowship of the Ring', written by 'J.R.R. Tolkien', published in 1954-07-29
        And another book with the title 'The Two Towers', written by 'J.R.R. Tolkien', published in 1954-11-11
        And another book with the title 'The Return of the King', written by 'J.R.R. Tolkien', published in 1955-10-20
        And another book with the title 'Harry Potter and the Philosopher’s Stone', written by 'J.K. Rowling', published in 1997-06-26
        And another book with the title 'Harry Potter and the Chamber of Secrets', written by 'J.K. Rowling', published in 1998-07-02
        And another book with the title 'Harry Potter and the Prisoner of Azkaban', written by 'J.K. Rowling', published in 1999-07-08
        And another book with the title 'Harry Potter and the Goblet of Fire', written by 'J.K. Rowling', published in 2000-07-08
        And another book with the title 'Harry Potter and the Order of the Phoenix', written by 'J.K. Rowling', published in 2003-06-21
        And another book with the title 'Harry Potter and the Half-Blood Prince', written by 'J.K. Rowling', published in 2005-07-16
        And another book with the title 'Harry Potter and the Deathly Hallows', written by 'J.K. Rowling', published in 2007-07-21
      When the user searches for books by 'J.R.R. Tolkien'
      Then 4 books should have been found
        And Book 1 should have the title 'The Hobbit'
        And Book 2 should have the title 'The Fellowship of the Ring'
        And Book 3 should have the title 'The Two Towers'
        And Book 4 should have the title 'The Return of the King'

Scenario: Correct non-zero number of books found by author
  Given I have the following books in the library
    | The Devil in the White City          | Erik Larson |
    | The Lion, the Witch and the Wardrobe | C.S. Lewis  |
    | In the Garden of Beasts              | Erik Larson |
  When I search for books by author 'Erik Larson'
  Then I find 2 books


