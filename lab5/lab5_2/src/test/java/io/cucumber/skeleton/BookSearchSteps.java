package io.cucumber.skeleton;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ParameterType;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class BookSearchSteps {
    Library library = new Library();
    List<Book> result = new ArrayList<>();

    @Given("a book with the title {string}, written by {string}, published in {iso8601Date}")
    public void addNewBook(final String title, final String author, final LocalDateTime publishDate) {

        Book book = new Book(title, author, publishDate);
        library.addBook(book);

    }

    @When("the customer searches for books published between {iso8601Date} and {iso8601Date}")
    public void setSearchParameters(final LocalDateTime fromYear, final LocalDateTime toYear) {
        result = library.findBooks(fromYear, toYear);
    }

    @Then("{int} books should have been found")
    public void verifyAmountOfBooksFound(final int booksFound) {
        assertThat(result.size()).isEqualTo(booksFound);
    }

    @Then("Book {int} should have the title {string}")
    public void verifyBookAtPosition(final int position, final String title) {
        assertThat(result.get(position - 1).getTitle()).isEqualTo(title);
    }

    @Given("another book with the title {string}, written by {string}, published in {iso8601Date}")
    public void another_book_with_the_title_written_by_published_in(String title, String author,
            LocalDateTime publishDate) {
        addNewBook(title, author, publishDate);
    }

    @When("the user searches for books by {string}")
    public void the_user_searches_for_books_by(String author) {
        result = library.findBooksByAuthor(author);
    }

    @Given("I have the following books in the library")
    public void haveBooksInTheStoreByList(DataTable table) {

        List<List<String>> rows = table.asLists(String.class);

        for (List<String> columns : rows) {
            library.addBook(new Book(columns.get(0), columns.get(1)));
        }
    }

    @When("I search for books by author {string}")
    public void i_search_for_books_by_author(String author) {
        the_user_searches_for_books_by(author);
    }

    @Then("I find {int} books")
    public void i_find_books(Integer int1) {
        assertThat(int1).isEqualTo(result.size());
    }

    @ParameterType("([0-9]{4})-([0-9]{2})-([0-9]{2})")
    public LocalDateTime iso8601Date(final String year, final String month, final String day) {
        return LocalDateTime.of(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), 0, 0);
    }
}