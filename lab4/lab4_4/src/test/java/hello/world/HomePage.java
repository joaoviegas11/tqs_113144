package hello.world;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class HomePage {
    WebDriver driver;

    @FindBy(name = "fromPort")
    private WebElement fromPortDropdown;

    @FindBy(name = "toPort")
    private WebElement toPortDropdown;

    @FindBy(css = ".btn-primary")
    private WebElement findFlightsButton;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void selectFromPort(String fromCity) {
        new Select(fromPortDropdown).selectByVisibleText(fromCity);
    }

    public void selectToPort(String toCity) {
        new Select(toPortDropdown).selectByVisibleText(toCity);
    }

    public void findFlights() {
        findFlightsButton.click();
    }
}
