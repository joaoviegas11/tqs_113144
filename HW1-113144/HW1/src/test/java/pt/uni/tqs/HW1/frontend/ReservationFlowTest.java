package pt.uni.tqs.HW1.frontend;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.seljup.SeleniumJupiter;

import java.time.Duration;

@ExtendWith(SeleniumJupiter.class)
public class ReservationFlowTest {

  @Test
  void testReservationFlow(FirefoxDriver driver) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1000));

    driver.get("http://localhost:8080/site/refectories");
    driver.manage().window().setSize(new Dimension(1024, 768));

    // Selecionar refeitório
    wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".col:nth-child(1) .btn"))).click();

    // Fazer reserva num menu (3º card visível)
    wait.until(ExpectedConditions.elementToBeClickable(
        By.cssSelector(".row-cols-1 .col:nth-child(3) button.btn-primary"))).click();


    // Capturar o token da página de sucesso
    WebElement tokenAlert = wait.until(ExpectedConditions.visibilityOfElementLocated(
        By.cssSelector(".alert.alert-success")
    ));
    String token = tokenAlert.getText();
    System.out.println("Token capturado: " + token);

    // Voltar aos refeitórios
    wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Voltar aos Refeitórios"))).click();

    // Consultar reserva com o token capturado
    wait.until(ExpectedConditions.elementToBeClickable(By.linkText("Consultar Reserva"))).click();
    WebElement tokenInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("token")));
    tokenInput.sendKeys(token);

    wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn-primary"))).click();


    wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector(".btn-danger"))).click();

    WebElement alert = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert")));
    assertThat(alert.isDisplayed()).isTrue();

    WebElement confirmation = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".alert.alert-warning")));
    assertThat(confirmation.getText())
    .contains("Reserva com token")
    .contains(token)
    .contains("não encontrada ou já foi usada/cancelada");


  }
}
