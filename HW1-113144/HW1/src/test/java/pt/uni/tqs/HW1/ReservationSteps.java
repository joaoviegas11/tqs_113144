package pt.uni.tqs.HW1;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
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

public class ReservationSteps {

    private WebDriver driver;
    private WebDriverWait wait;
    private String token;

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

    @Given("the user is on the refectories page")
    public void the_user_is_on_the_refectories_page() {
        driver.get("http://localhost:8080/site/refectories");
        driver.manage().window().setSize(new Dimension(1024, 768));
    }

    @When("the user selects a refectory")
    public void the_user_selects_a_refectory() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".col:nth-child(2) .btn"))).click();
    }

    @When("the user chooses a menu and makes a reservation")
    public void the_user_chooses_a_menu_and_makes_a_reservation() {
        wait.until(ExpectedConditions.elementToBeClickable(
                By.cssSelector(".row-cols-1 .col:nth-child(3) button.btn-primary"))).click();
    }

    @Then("a reservation token should be displayed")
    public void a_reservation_token_should_be_displayed() {
        WebElement tokenAlert = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".alert.alert-success")));
        token = tokenAlert.getText();
        assertThat(token).isNotBlank();
        System.out.println("Captured token: " + token);
    }

    @When("the user returns to the main page")
    public void the_user_returns_to_the_main_page() {
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Voltar aos Refeitórios"))).click();
    }

    @When("the user navigates to the reservation lookup page")
    public void the_user_navigates_to_the_reservation_lookup_page() {
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Consultar Reserva"))).click();
    }

    @When("the user enters the token and checks the reservation")
    public void the_user_enters_the_token_and_checks_the_reservation() {
        WebElement tokenInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("token")));
        tokenInput.sendKeys(token);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn-primary"))).click();
    }

    @When("the user attempts to cancel the reservation")
    public void the_user_attempts_to_cancel_the_reservation() {
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn-danger"))).click();
    }

    @Then("a message should appear indicating that the token is invalid")
    public void a_message_should_appear_indicating_that_the_token_is_invalid() {
        WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".alert.alert-warning")));
        assertThat(alert.getText())
                .contains("Reserva com token")
                .contains("não encontrada ou já foi usada/cancelada");
    }

    @When("the user navigates to the employee check-in page")
    public void the_user_navigates_to_the_employee_check_in_page() {
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Check-in Funcionário"))).click();
    }

    @When("the user enters the token and confirms the check-in")
    public void the_user_enters_the_token_and_confirms_the_check_in() {
        WebElement tokenInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("token")));
        tokenInput.sendKeys(token);
        wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button.btn.btn-primary"))).click();
    }

    @Then("a message should appear indicating that the check-in was valid")
    public void a_message_should_appear_indicating_that_the_check_in_was_valid() {
        WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".alert.alert-success")));

        assertThat(alert.getText())
                .contains("Reserva com token")
                .contains("foi")
                .contains("confirmada com sucesso");
    }

}
