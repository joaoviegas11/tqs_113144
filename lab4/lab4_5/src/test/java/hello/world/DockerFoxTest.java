
package hello.world;

import static io.github.bonigarcia.seljup.BrowserType.FIREFOX;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.seljup.DockerBrowser;
import io.github.bonigarcia.seljup.SeleniumJupiter;

@ExtendWith(SeleniumJupiter.class)
public class DockerFoxTest {

    @Test
    void testDockerIpma(@DockerBrowser(type = FIREFOX) WebDriver driver) {
        driver.get("https://www.ipma.pt/pt/otempo/prev.localidade.hora/#Aveiro&Aveiro");
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));
        System.out.println(driver.findElement(By.xpath("//div[3]/div/div[3]")).getText());
        System.out.println(driver.findElement(By.xpath("//div[2]/div[3]")).getText());
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[4]/div[3]")));
        System.out.println(driver.findElement(By.xpath("//div[4]/div[3]")).getText());
    }

}