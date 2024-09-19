import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class OrderTest {

    ScooterPage scooterPage;
    private final String clientFirstName;
    private final String clientLastName;
    private final String deliveryAddress;
    private final int clientMetroStation;
    private final String clientPhoneNumber;
    private final String deliveryDate;
    private final String rentalPeriod;
    private final String scooterColor;
    private final String commentForCourier;
    private WebDriver driver;

    public OrderTest(String clientFirstName, String clientLastName, String deliveryAddress, int clientMetroStation, String clientPhoneNumber, String deliveryDate, String rentalPeriod, String scooterColor, String commentForCourier) {
        this.clientFirstName = clientFirstName;
        this.clientLastName = clientLastName;
        this.deliveryAddress = deliveryAddress;
        this.clientMetroStation = clientMetroStation;
        this.clientPhoneNumber = clientPhoneNumber;
        this.deliveryDate = deliveryDate;
        this.rentalPeriod = rentalPeriod;
        this.scooterColor = scooterColor;
        this.commentForCourier = commentForCourier;
    }

    @Parameterized.Parameters
    public static Object[][] getClientData() {
        return new Object[][]{
                {"Карлос", "Санчез", "ул. Комарова 3в", 1, "89531183456", "26.09.2024", "четверо суток", "black", "мямя"},
                {"Чёрный", "Мечник", "Нахимовский пер. 23", 3, "+79507504346", "27.09.2024", "сутки", "grey", "мя"},
        };
    }

    //создание драйвера
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

    @Test
    public void checkOrder() {
        //кликаем на верхнюю кнопку заказа
        scooterPage.clickOnTopOrderButton();
        checkOrderProcess();
    }

    @Test
    public void checkAnotherOrder() {
        //кликаем на верхнюю кнопку заказа
        scooterPage.clickOnBottomOrderButton();
        checkOrderProcess();
    }

    private void checkOrderProcess() {
        //заполнить форму заказа о клиенте
        scooterPage.fillFormAboutClient(clientFirstName, clientLastName, deliveryAddress, clientMetroStation, clientPhoneNumber);
        //жмем далее
        scooterPage.clickOnNextButtonInOrder();
        //заполняем форму заказа о аренде
        scooterPage.fillFormAboutRent(deliveryDate, rentalPeriod, scooterColor, commentForCourier);
        //жмём заказать
        String orderConfirmed = scooterPage.submitOrder();
        assertEquals("Заказ успешно оформлен", "Заказ оформлен", orderConfirmed.substring(0, 14));
    }

    @After
    public void tearDown() {
        //закрываем браузер
        driver.quit();
    }
}
