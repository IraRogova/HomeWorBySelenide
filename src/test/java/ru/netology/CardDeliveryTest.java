package ru.netology;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;


import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.*;

public class CardDeliveryTest {



    @BeforeEach
    void setUpAll() {
        open("http://localhost:9999");
    }

    @Test
    void shouldSendIfAllFieldIsCorrect() {
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='city'] input").setValue("Брянск");
        LocalDate dateOfDelivery = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = formatter.format(dateOfDelivery);
        $("[data-test-id='date'] input").setValue(date);
        $("[data-test-id='name'] input").setValue("Ирина Рогова");
        $("[data-test-id='phone'] input").setValue("+77078397070");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofMillis(14000));
        $("[data-test-id=notification] .notification__content").shouldHave
                (text("Встреча успешно забронирована на "+date));
    }

    @Test
    void shouldNotSendIfCityIsNotCorrect() {
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='city'] input").setValue("Алматы");
        LocalDate dateOfDelivery = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = formatter.format(dateOfDelivery);
        $("[data-test-id='date'] input").setValue(date);

        $("[data-test-id='name'] input").setValue("Ирина Рогова");
        $("[data-test-id='phone'] input").setValue("+77078397070");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave
                (exactText("Доставка в выбранный город недоступна"));

    }

    @Test
    void shouldNotSendIfDateIsNotCorrect() {
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='city'] input").setValue("Брянск");
        LocalDate dateOfDelivery = LocalDate.now().plusDays(2);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = formatter.format(dateOfDelivery);
        $("[data-test-id='date'] input").setValue(date);

        $("[data-test-id='name'] input").setValue("Ирина Рогова");
        $("[data-test-id='phone'] input").setValue("+77078397070");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=date] .input_invalid .input__sub").shouldHave
                (exactText("Заказ на выбранную дату невозможен"));

    }

    @Test
    void shouldNotSendIfNameIsNotCorrect() {
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='city'] input").setValue("Брянск");
        LocalDate dateOfDelivery = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = formatter.format(dateOfDelivery);
        $("[data-test-id='date'] input").setValue(date);

        $("[data-test-id='name'] input").setValue("Irina Rogova");
        $("[data-test-id='phone'] input").setValue("+77078397070");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=name].input_invalid .input__sub").shouldHave(exactText
                ("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы."));

    }

    @Test
    void shouldNotSendIfTelIsNotCorrect() {
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='city'] input").setValue("Брянск");
        LocalDate dateOfDelivery = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = formatter.format(dateOfDelivery);
        $("[data-test-id='date'] input").setValue(date);

        $("[data-test-id='name'] input").setValue("Ирина Рогова");
        $("[data-test-id='phone'] input").setValue("+7707");
        $("[data-test-id='agreement']").click();
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=phone].input_invalid .input__sub").shouldHave(exactText
                ("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678."));

    }

    @Test
    void shouldNotSendIfAgreementIsNotCorrect() {
        $("[data-test-id='date'] input").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[data-test-id='city'] input").setValue("Брянск");
        LocalDate dateOfDelivery = LocalDate.now().plusDays(3);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = formatter.format(dateOfDelivery);
        $("[data-test-id='date'] input").setValue(date);

        $("[data-test-id='name'] input").setValue("Ирина Рогова");
        $("[data-test-id='phone'] input").setValue("+77078397070");

        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=agreement].input_invalid .checkbox__text").shouldHave(exactText
                ("Я соглашаюсь с условиями обработки и использования моих персональных данных"));

    }


    @Test
    void shouldNotSendIfTestIsEmpty() {
        $$("button").find(exactText("Забронировать")).click();
        $("[data-test-id=city].input_invalid .input__sub").shouldHave(exactText
                ("Поле обязательно для заполнения"));

    }

}
