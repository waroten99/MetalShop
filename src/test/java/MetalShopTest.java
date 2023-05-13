import com.github.javafaker.Faker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;

import java.util.concurrent.TimeUnit;

public class MetalShopTest {

    WebDriver driver = new ChromeDriver();
    Faker faker = new Faker();

    @Test
    void emptyUsernameTest() {
        driver.manage().window().maximize();
        driver.get("http://serwer169007.lh.pl/autoinstalator/serwer169007.lh.pl/wordpress10772/");
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.findElement(By.linkText("Moje konto")).click();
        driver.findElement(By.id("password")).sendKeys("takakr");
        driver.findElement(By.cssSelector(".woocommerce-form-login__submit")).click();
        Assertions.assertEquals("Błąd: Nazwa użytkownika jest wymagana.", driver.findElement(By.cssSelector(".woocommerce-error")).getText());
        driver.quit();
    }

    @Test
    void emptyPasswordTest() {
        driver.manage().window().maximize();
        driver.get("http://serwer169007.lh.pl/autoinstalator/serwer169007.lh.pl/wordpress10772/");
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.findElement(By.linkText("Moje konto")).click();
        driver.findElement(By.cssSelector("#username")).sendKeys("test");
        driver.findElement(By.cssSelector(".woocommerce-form-login__submit")).click();
        Assertions.assertEquals("Błąd: pole hasła jest puste.", driver.findElement(By.cssSelector(".woocommerce-error")).getText());
        driver.quit();
    }

    @Test
    void createUser() {
        driver.manage().window().maximize();
        driver.get("http://serwer169007.lh.pl/autoinstalator/serwer169007.lh.pl/wordpress10772/");
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.findElement(By.xpath("//a[text()='register']")).click();
        String username = faker.name().username();
        String email = username + faker.random().nextInt(1000) + "@wp.pl";
        String password = "Test123";
        driver.findElement(By.cssSelector("#user_login")).sendKeys(username);
        driver.findElement(By.cssSelector("#user_pass")).sendKeys(password);
        driver.findElement(By.cssSelector("#user_email")).sendKeys(email);
        driver.findElement(By.cssSelector("#user_confirm_password")).sendKeys(password);
        driver.findElement(By.cssSelector(".ur-submit-button")).click();
        Assertions.assertEquals("User successfully registered.", driver.findElement(By.cssSelector("#ur-submit-message-node")).getText());
        driver.quit();
    }

    @Test
    void checkLogoAndSearchOnHomePage() {
        driver.manage().window().maximize();
        driver.get("http://serwer169007.lh.pl/autoinstalator/serwer169007.lh.pl/wordpress10772/");
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        Assertions.assertTrue(driver.findElement(By.xpath("//a[text() = 'Softie Metal Shop']")).isDisplayed()
                && driver.findElement(By.cssSelector("#woocommerce-product-search-field-0")).isDisplayed());
        driver.quit();
    }

    @Test
    void checkLogoAndSearchOnLoginPage() {
        driver.manage().window().maximize();
        driver.get("http://serwer169007.lh.pl/autoinstalator/serwer169007.lh.pl/wordpress10772/");
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.findElement(By.linkText("Moje konto")).click();
        Assertions.assertTrue(driver.findElement(By.xpath("//a[text() = 'Softie Metal Shop']")).isDisplayed()
                && driver.findElement(By.cssSelector("#woocommerce-product-search-field-0")).isDisplayed());
        driver.quit();
    }

    @Test
    void stepFromMainSiteToContact() {
        driver.manage().window().maximize();
        driver.get("http://serwer169007.lh.pl/autoinstalator/serwer169007.lh.pl/wordpress10772/");
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.findElement(By.linkText("Kontakt")).click();
        Assertions.assertTrue(driver.findElement(By.xpath("//h1[text() = 'Kontakt']")).isDisplayed());
        driver.quit();
    }

    @Test
    void stepFromLoginSiteToMainSite() {
        driver.manage().window().maximize();
        driver.get("http://serwer169007.lh.pl/autoinstalator/serwer169007.lh.pl/wordpress10772/moje-konto/");
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.findElement(By.cssSelector(".woocommerce-breadcrumb > a")).click();
        Assertions.assertTrue(driver.findElement(By.cssSelector(".site-main > header > h1")).isDisplayed());
        driver.quit();
    }

    @Test
    void sendingMessageTry() {
        driver.manage().window().maximize();
        driver.get("http://serwer169007.lh.pl/autoinstalator/serwer169007.lh.pl/wordpress10772/kontakt/");
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.findElement(By.cssSelector("input[name = 'your-name']")).sendKeys("test test");
        driver.findElement(By.cssSelector("input[name = 'your-email']")).sendKeys("test@wp.pl");
        driver.findElement(By.cssSelector("input[name = 'your-subject']")).sendKeys("test123");
        driver.findElement(By.cssSelector("textarea[name = 'your-message']")).sendKeys("test test test");
        driver.findElement(By.xpath("//input[@value='Wyślij']")).click();
        Assertions.assertEquals("Wystąpił problem z wysłaniem twojej wiadomości. Spróbuj ponownie później.", driver.findElement(By.xpath("//div[text()='Wystąpił problem z wysłaniem twojej wiadomości. Spróbuj ponownie później.']")).getText());
        driver.quit();
    }

    @Test
    void checkAddCheckShopBasket() {
        driver.manage().window().maximize();
        driver.get("http://serwer169007.lh.pl/autoinstalator/serwer169007.lh.pl/wordpress10772/");
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.findElement(By.linkText("Koszyk")).click();
        Assertions.assertTrue(driver.findElement(By.cssSelector(".cart-empty")).isDisplayed());
        driver.findElement(By.cssSelector(".woocommerce-breadcrumb > a")).click();
        driver.findElement(By.xpath("//a[@aria-label = 'Dodaj „Srebrna moneta 8g - USA 2002” do koszyka']")).click();
        Assertions.assertFalse(driver.findElement(By.cssSelector(".cart-empty")).isDisplayed());
        //        && driver.findElement(By.cssSelector("#woocommerce-product-search-field-0")).isDisplayed());
        //driver.quit();
    }
}
