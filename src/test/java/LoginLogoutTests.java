import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginLogoutTests {
    private WebDriver driver;
    private WebDriverWait wait;

    @BeforeEach
    public void setUp() {
        // Ініціалізація драйвера
        driver = new ChromeDriver();
        //    driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testLoginAndLogout() {
        // Відкриваємо сторінку логіну
        driver.get("https://opensource-demo.orangehrmlive.com/");

        // Чекаємо появу полів логіну та паролю
        WebElement username = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("username")));
        WebElement password = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("password")));
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button[type='submit']")));

        // Вводимо коректні дані
        username.sendKeys("Admin");
        password.sendKeys("admin123");
        loginButton.click();

        // Чекаємо, поки завантажиться Dashboard
        WebElement dashboard = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h6[text()='Dashboard']")));
        assertTrue(dashboard.isDisplayed(), "Dashboard не відображається, вхід неуспішний");

        // Знаходимо кнопку профілю (або іконку користувача) для відкриття меню
        WebElement profileMenu = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("p.oxd-userdropdown-name")));
        profileMenu.click();

        // Клікаємо на кнопку Logout у меню
        WebElement logoutButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Logout']")));
        logoutButton.click();

        // Чекаємо, поки повернемося на сторінку логіну (перевіряємо наявність кнопки логіну)
        WebElement loginBtnAfterLogout = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[type='submit']")));
        assertTrue(loginBtnAfterLogout.isDisplayed(), "Вихід неуспішний, кнопка логіну не знайдена");
    }
}
