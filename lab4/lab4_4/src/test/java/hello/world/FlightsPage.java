package hello.world;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class FlightsPage {
    WebDriver driver;
    WebDriverWait wait;

    @FindBy(css = "tr:nth-child(2) .btn")
    private WebElement chooseFlightButton;

    public FlightsPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        PageFactory.initElements(driver, this);
    }

    public void selectFlight() {
        wait.until(ExpectedConditions.elementToBeClickable(chooseFlightButton)).click();
    }
}
