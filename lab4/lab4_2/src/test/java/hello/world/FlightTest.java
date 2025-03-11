package hello.world;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

@ExtendWith(SeleniumJupiter.class)
public class FlightTest {

  @Test
  void test(FirefoxDriver driver) {
    driver.get("https://blazedemo.com/");
    driver.manage().window().setSize(new Dimension(864, 696));

    driver.findElement(By.name("fromPort")).click();
    WebElement fromDropdown = driver.findElement(By.name("fromPort"));
    fromDropdown.findElement(By.xpath("//option[. = 'San Diego']")).click();
    assertThat(fromDropdown.getDomProperty("value")).isEqualTo("San Diego");

    driver.findElement(By.name("toPort")).click();
    WebElement toDropdown = driver.findElement(By.name("toPort"));
    toDropdown.findElement(By.xpath("//option[. = 'Rome']")).click();
    assertThat(toDropdown.getDomProperty("value")).isEqualTo("Rome");

    driver.findElement(By.cssSelector(".btn-primary")).click();

    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("tr:nth-child(2) .btn")));
    assertThat(driver.getTitle()).isEqualTo("BlazeDemo - reserve");

    driver.findElement(By.cssSelector("tr:nth-child(2) .btn")).click();

    wait.until(ExpectedConditions.presenceOfElementLocated(By.id("inputName")));
    assertThat(driver.getTitle()).isEqualTo("BlazeDemo Purchase");

    driver.findElement(By.id("inputName")).sendKeys("Eduard Antonic");
    driver.findElement(By.id("address")).sendKeys("123 Rua 2");
    driver.findElement(By.id("city")).sendKeys("Anywere");
    driver.findElement(By.id("state")).sendKeys("Solid");
    driver.findElement(By.id("zipCode")).sendKeys("32478");

    WebElement cardDropdown = driver.findElement(By.id("cardType"));
    cardDropdown.findElement(By.xpath("//option[. = 'American Express']")).click();
    assertThat(cardDropdown.getDomProperty("value")).isEqualTo("amex");

    driver.findElement(By.id("creditCardNumber")).sendKeys("918943189379398");
    driver.findElement(By.id("nameOnCard")).sendKeys("Jonh Swon");
    driver.findElement(By.id("rememberMe")).click();

    driver.findElement(By.cssSelector(".btn-primary")).click();

    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("h1")));
    WebElement confirmationTitle = driver.findElement(By.cssSelector("h1"));
    assertThat(confirmationTitle.getText()).isEqualTo("Thank you for your purchase today!");

  }

}