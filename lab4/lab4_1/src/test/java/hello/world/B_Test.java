
package hello.world;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;


import io.github.bonigarcia.wdm.WebDriverManager; 

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import java.time.Duration;


public class B_Test {

    static final Logger log = getLogger(lookup().lookupClass());

    private WebDriver driver; 

    @BeforeAll
    static void setupClass() {
        WebDriverManager.firefoxdriver().setup(); 
    }

    @BeforeEach
    void setup() {
        driver = new FirefoxDriver(); 
    }

    @Test
    void test() {
        // Exercise
        driver.get("https://bonigarcia.dev/selenium-webdriver-java/");
        driver.findElement(By.linkText("Slow calculator")).click();
        assertThat(driver.getCurrentUrl()).isEqualTo("https://bonigarcia.dev/selenium-webdriver-java/slow-calculator.html");
        driver.findElement(By.cssSelector(".btn:nth-child(3)")).click();
        driver.findElement(By.cssSelector(".operator:nth-child(16)")).click();
        driver.findElement(By.cssSelector(".btn:nth-child(3)")).click();
        driver.findElement(By.cssSelector(".btn-outline-warning")).click();
        
        String title = driver.getTitle(); 

        // Verify
        assertThat(title).isEqualTo("Hands-On Selenium WebDriver with Java"); 
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(9));
        wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#spinner"))));
        assertThat(driver.findElement(By.cssSelector(".screen")).getText()).isEqualTo("81"); 
    }

    @AfterEach
    void teardown() {
        driver.quit(); 
    }

}