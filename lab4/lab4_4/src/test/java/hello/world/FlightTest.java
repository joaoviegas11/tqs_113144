package hello.world;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.firefox.FirefoxDriver;

import io.github.bonigarcia.seljup.SeleniumJupiter;
import org.openqa.selenium.WebDriver;

@ExtendWith(SeleniumJupiter.class)
public class FlightTest {
    WebDriver driver;
    HomePage homePage;
    FlightsPage flightsPage;
    PurchasePage purchasePage;
    ConfirmationPage confirmationPage;

    @BeforeEach
    void setup() {
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("https://blazedemo.com/");

        homePage = new HomePage(driver);
        flightsPage = new FlightsPage(driver);
        purchasePage = new PurchasePage(driver);
        confirmationPage = new ConfirmationPage(driver);
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    void testFlightPurchase() {
        homePage.selectFromPort("San Diego");
        homePage.selectToPort("Rome");
        homePage.findFlights();

        flightsPage.selectFlight();

        purchasePage.enterPassengerDetails("Eduard Antonic", "123 Rua 2", "Anywere", "Solid", "32478");
        purchasePage.enterPaymentDetails("American Express", "918943189379398", "Jonh Swon");
        purchasePage.confirmPurchase();

        assertThat(confirmationPage.getConfirmationText()).isEqualTo("Thank you for your purchase today!");
    }

}