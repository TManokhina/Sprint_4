import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertTrue;

public class FaqTest {
    private WebDriver driver;
    private ScooterPage scooterPage;

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        //FirefoxOptions options = new FirefoxOptions().addArguments("--no-sandbox", "--disable-dev-shm-usage");
        ChromeOptions options = new ChromeOptions().addArguments("--no-sandbox", "--disable-dev-shm-usage");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://qa-scooter.praktikum-services.ru/");
        scooterPage = new ScooterPage(driver);
        scooterPage.clickInButtonForCookie();
    }

    /**
     * жмем на стрелку - проверяем что вывалился текст
     */
    @Test
    public void whenArrowClickedTextShouldBeShown() {
        //вызвать скролл
        scooterPage.scrollToQuestions();
        // находим стрелку
        List<WebElement> questions = scooterPage.getQuestions();
        for (WebElement item : questions) {
            //жмем на стрелочку
            scooterPage.openItem(item);
            // проверяем что появился текст
            //questionsAboutImportant.isAnswerDisplayed(item);
            assertTrue("Ответ отобразился", scooterPage.isAnswerDisplayed(item));
        }
    }

    @After
    public void tearDown() {
        //закрываем браузер
        driver.quit();
    }
}
