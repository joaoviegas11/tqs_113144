/*
 * (C) Copyright 2020 Boni Garcia (https://bonigarcia.github.io/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
package io.github.bonigarcia;

import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.assertj.core.api.Assertions.assertThat;

public class BookstoreSteps {
    private static WebDriver driver;
    private static WebDriverWait wait;
    private String titleResult;
    private String authorResult;
    private String error;
    
    @Before
    public void setup() {        
        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @After
    public void teardown() {
        if (driver != null) {
            driver.quit();
        }
    }
    @Given("the user is on the library homepage")
    public void the_user_is_on_the_library_homepage() {
        driver.get("https://cover-bookstore.onrender.com/");
    }

    @When("the user searches for books by {string}")
    public void the_user_searches_for_books_by(String author) {
        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.className("Navbar_searchBarInput__w8FwI")));
        searchBox.click();
        searchBox.sendKeys(author);
        searchBox.sendKeys(Keys.ENTER);
    }

    @Then("the search results should show books written by {string}")
    public void the_search_results_should_show_books_written_by(String author) {
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-testid='book-search-item']")));

        List<WebElement> books = driver.findElements(By.cssSelector("[data-testid='book-search-item']"));
        boolean authorFound = books.stream()
            .anyMatch(book -> book.findElement(By.cssSelector(".SearchList_bookAuthor__3giPc")).getText().contains(author));

        assertThat(authorFound).isTrue();
    }

    @When("the user searches for the book {string}")
    public void the_user_searches_for(String bookTitle) {
        WebElement searchBox = wait.until(ExpectedConditions.elementToBeClickable(By.className("Navbar_searchBarInput__w8FwI")));
        searchBox.click();
        searchBox.sendKeys(bookTitle);
        searchBox.sendKeys(Keys.ENTER);
       try {
        (new WebDriverWait(driver, Duration.ofSeconds(5))).until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-testid='book-search-item']")));

        List<WebElement> books = driver.findElements(By.cssSelector("[data-testid='book-search-item']"));

        for (WebElement book : books) {
            String title = book.findElement(By.cssSelector(".SearchList_bookTitle__1wo4a")).getText();
            String author = book.findElement(By.cssSelector(".SearchList_bookAuthor__3giPc")).getText();

            if (title.contains(bookTitle)) {
                titleResult = title;
                authorResult = author;
                wait.until(ExpectedConditions.elementToBeClickable(book)).click();
                break;
            }
        }
       } catch (Exception e) {
        error="No books found";
       }
       
    }

    @Then("the search results should display a message {string}")
    public void the_search_results_should_display_a_message(String message) {
        assertThat(error).isEqualTo(message);
        error=null;
    }

    @Then("the search results should display {string} by {string}")
    public void the_search_results_should_display_title_by_author(String expectedTitle, String expectedAuthor) {
        assertThat(titleResult).isEqualTo(expectedTitle);
        assertThat(authorResult).isEqualTo(expectedAuthor);
    }
}

