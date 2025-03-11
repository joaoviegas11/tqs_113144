package hello.world;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.seljup.SeleniumJupiter;

@ExtendWith(SeleniumJupiter.class)
public class PotterTest {

    @Test
    void test(FirefoxDriver driver) {
        driver.get("https://cover-bookstore.onrender.com/");
        driver.manage().window().setSize(new Dimension(864, 696));
        driver.findElement(By.xpath("//input[@value=\'\']")).click();
        driver.findElement(By.xpath("//input[@value=\'\']")).sendKeys("Harry Potter");
        driver.findElement(By.xpath("//input[@value=\'Harry Potter\']")).sendKeys(Keys.ENTER);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[data-testid='book-search-item']")));

        List<WebElement> books = driver.findElements(By.cssSelector("[data-testid='book-search-item']"));

        WebElement foundBook = null;
        for (WebElement book : books) {
            String title = book.findElement(By.cssSelector(".SearchList_bookTitle__1wo4a")).getText();
            String author = book.findElement(By.cssSelector(".SearchList_bookAuthor__3giPc")).getText();

            if (title.contains("Harry Potter and the Sorcerer's Stone") && author.contains("J.K. Rowling")) {
                foundBook = book;
                break;
            }
        }

        assertThat(foundBook).isNotNull();
        assertThat(foundBook.isDisplayed()).isTrue();

        foundBook.click();
    }

}