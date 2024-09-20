package ru.yandex.practicum;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.openqa.selenium.WebElement;

import java.util.List;

import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class FaqTest extends BaseSetUpTest {
    private final int fagNumber;

    public FaqTest(int fagNumber) {
        this.fagNumber = fagNumber;
    }

    @Parameterized.Parameters
    public static Object[][] getNumberFaq() {
        return new Object[][]{
                {0},
                {1},
                {2},
                {3},
                {4},
                {5},
                {6},
                {7},
        };
    }

    /**
     * Жмём на стрелку - проверяем, что вывалился текст.
     */
    @Test
    public void whenArrowClickedTextShouldBeShown() {
        //вызвать скролл
        scooterPage.scrollToQuestions();
        // находим стрелку
        List<WebElement> questions = scooterPage.getQuestions();
        WebElement item = questions.get(fagNumber);
        //жмем на стрелочку
        scooterPage.openItem(item);
        // проверяем что появился текст
        assertTrue("Ответ отобразился", scooterPage.isAnswerDisplayed(item));
    }
}
