package hello.world;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class PurchasePage {
    WebDriver driver;

    @FindBy(id = "inputName")
    private WebElement nameInput;

    @FindBy(id = "address")
    private WebElement addressInput;

    @FindBy(id = "city")
    private WebElement cityInput;

    @FindBy(id = "state")
    private WebElement stateInput;

    @FindBy(id = "zipCode")
    private WebElement zipCodeInput;

    @FindBy(id = "cardType")
    private WebElement cardTypeDropdown;

    @FindBy(id = "creditCardNumber")
    private WebElement creditCardNumberInput;

    @FindBy(id = "nameOnCard")
    private WebElement nameOnCardInput;

    @FindBy(id = "rememberMe")
    private WebElement rememberMeCheckbox;

    @FindBy(css = ".btn-primary")
    private WebElement purchaseButton;

    public PurchasePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void enterPassengerDetails(String name, String address, String city, String state, String zip) {
        nameInput.sendKeys(name);
        addressInput.sendKeys(address);
        cityInput.sendKeys(city);
        stateInput.sendKeys(state);
        zipCodeInput.sendKeys(zip);
    }

    public void enterPaymentDetails(String cardType, String cardNumber, String nameOnCard) {
        new Select(cardTypeDropdown).selectByVisibleText(cardType);
        creditCardNumberInput.sendKeys(cardNumber);
        nameOnCardInput.sendKeys(nameOnCard);
    }

    public void confirmPurchase() {
        rememberMeCheckbox.click();
        purchaseButton.click();
    }
}
