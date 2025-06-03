import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.Dimension;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTests {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        driver = new ChromeDriver();
        // driver.manage().window().maximize(); // CDP не підтримується у Chrome 136
        driver.manage().window().setSize(new Dimension(1280, 800)); // Альтернатива
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testSuccessfulLogin() {
        driver.get("https://opensource-demo.orangehrmlive.com/");

        // Очікуємо, поки з’явиться поле логіна
        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));

        username.sendKeys("Admin");
        password.sendKeys("admin123");
        loginButton.click();

        // Очікуємо, поки з’явиться елемент після входу (наприклад, заголовок або кнопка виходу)
        WebElement dashboard = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Dashboard']")));

        assertTrue(dashboard.isDisplayed());
    }
}
