package ru.yandex.practicum;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class OrderTest extends BaseSetUpTest {
    private final String clientFirstName;
    private final String clientLastName;
    private final String deliveryAddress;
    private final int clientMetroStation;
    private final String clientPhoneNumber;
    private final String deliveryDate;
    private final String rentalPeriod;
    private final String scooterColor;
    private final String commentForCourier;


    public OrderTest(String clientFirstName, String clientLastName, String deliveryAddress, int clientMetroStation,
                     String clientPhoneNumber, String deliveryDate, String rentalPeriod, String scooterColor, String commentForCourier) {
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


    @Test
    public void checkOrderByUsingTopOrderButton() {
        //кликаем на верхнюю кнопку заказа
        scooterPage.clickOnTopOrderButton();
        checkOrderProcess();
    }

    @Test
    public void checkOrderByUsingBottomOrderButton() {
        //кликаем на нижнюю кнопку заказа
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


}
