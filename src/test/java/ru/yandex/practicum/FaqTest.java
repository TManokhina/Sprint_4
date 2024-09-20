package ru.yandex.practicum;

import org.junit.Test;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.Assert.assertTrue;

public class FaqTest extends BaseSetUpTest {
    /**
     * Жмём на стрелку - проверяем, что вывалился текст.
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
}
