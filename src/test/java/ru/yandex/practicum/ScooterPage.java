package ru.yandex.practicum;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ScooterPage {
    private final By byQuestionAndAnswers = By.xpath("//*[@data-accordion-component='AccordionItem']");
    private final By questionInItem = By.xpath("./*[@data-accordion-component='AccordionItemHeading']/*[starts-with(@id, 'accordion__heading-')]");
    private final By answerInItem = By.xpath("./*[@data-accordion-component='AccordionItemPanel']");
    private final By buttonForCookie = By.className("App_CookieButton__3cvqF");

    //подя для заполнения формы заказа для кого самокат
    private final By fieldForAddClientFirstName = byPlaceholder("* Имя");
    private final By fieldForAddClientLastName = byPlaceholder("* Фамилия");
    private final By fieldForAddDeliveryAddress = byPlaceholder("* Адрес: куда привезти заказ");
    private final By fieldForAddClientMetroStation = byPlaceholder("* Станция метро");
    private final By fieldForAddClientPhoneNumber = byPlaceholder("* Телефон: на него позвонит курьер");

    //после далее
    private final By fieldForAddDeliveryDate = byPlaceholder("* Когда привезти самокат");
    private final By fieldForAddCommentForCourier = byPlaceholder("Комментарий для курьера");



    // верхняя кнопка заказать
    private final By topOrderButton = By.xpath("//div/*[@class='Button_Button__ra12g' and text()='Заказать']");
    //кнопка далее(после кнопки заказать)
    private final By nextButtonInOrder = By.xpath("//div/*[@class='Button_Button__ra12g Button_Middle__1CSJM' and text()='Далее']");

    private final By finalButtonInOrder = By.xpath(".//button[text()='Заказать' and @class='Button_Button__ra12g Button_Middle__1CSJM']");
    private final By finalYes = By.xpath(".//button[text()='Да' and @class='Button_Button__ra12g " +
            "Button_Middle__1CSJM']");

    // нижняя кнопка заказать
    private final By bottomOrderButton = By.xpath(".//button[@class='Button_Button__ra12g Button_Middle__1CSJM']");

    private final WebDriver driver;
    public ScooterPage(WebDriver driver) {
        this.driver = driver;
    }

    private static By byPlaceholder(String placeholder) {
        return By.xpath("//*[@placeholder='" + placeholder + "']");
    }

    public List<WebElement> getQuestions() {
        return driver.findElements(byQuestionAndAnswers);
    }

    public void openItem(WebElement questionItem) {
        questionItem.findElement(questionInItem).click();

    }

    public boolean isAnswerDisplayed(WebElement questionItem) {
        return questionItem.findElement(answerInItem).isDisplayed();
    }

    ///////////////////////////////
    //работа с заказом

    //метод кликающий на верхнюю кнопку заказа
    public void clickOnTopOrderButton() {
        driver.findElement(topOrderButton).click();
    }

    //метод кикающий на нижнюю кнопку заказа
    public void clickOnBottomOrderButton() {
        WebElement element = driver.findElement(bottomOrderButton);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", element);
        element.click();
    }

    //метод кликающий на кнопку далее
    public void clickOnNextButtonInOrder() {
        driver.findElement(nextButtonInOrder).click();
    }

    public void clickInButtonForCookie () {
        driver.findElement(buttonForCookie).click();
    }

    public void fillFormAboutClient(String clientFirstName, String clientLastName, String deliveryAddress,
                                    int clientMetroStation, String clientPhoneNumber) {
        driver.findElement(fieldForAddClientFirstName).sendKeys(clientFirstName);
        driver.findElement(fieldForAddClientLastName).sendKeys(clientLastName);
        driver.findElement(fieldForAddDeliveryAddress).sendKeys(deliveryAddress);
        driver.findElement(fieldForAddClientMetroStation).click();
        driver.findElements(By.tagName("li")).get(clientMetroStation).click();
        driver.findElement(fieldForAddClientPhoneNumber).sendKeys(clientPhoneNumber);
    }

    public void fillFormAboutRent(String deliveryDate, String rentalPeriod, String scooterColor, String commentForCourier) {


        driver.findElement(fieldForAddDeliveryDate).sendKeys(deliveryDate);
        //у срока аренды дропдаун- отдельная всплывашка поэтому обрабатываем отдельно

        //Select rent = new Select(driver.findElement(By.className("Dropdown-arrow")));
        //rent.selectByValue(rentalPeriod);
        driver.findElement(By.className("Dropdown-arrow")).click();
        driver.findElement(By.xpath("//*[text()='"+ rentalPeriod +"']")).click();

        driver.findElement(By.id(scooterColor)).click();
        driver.findElement(fieldForAddCommentForCourier).sendKeys(commentForCourier);
    }

    public String submitOrder() {
        driver.findElement(finalButtonInOrder).click();
        driver.findElement(finalYes).click();
        //waiter.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("Order_Modal__YZ-d3")));
        return driver.findElement(By.xpath("//*[text()='Заказ оформлен']")).getText();
    }

    public void scrollToQuestions() {
        WebElement element = driver.findElement(byQuestionAndAnswers);
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView();", element);
    }
}

