package ru.netology.journey.pages;

import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.Selectors.byText;

public class MainPage {
    private final SelenideElement heading = $("[class='heading heading_size_l heading_theme_alfa-on-white']");
    private final SelenideElement formHeading = $("[class='heading heading_size_m heading_theme_alfa-on-white']");
    private final SelenideElement paymentButton = $(byText("Купить"));
    private final SelenideElement creditButton = $(byText("Купить в кредит"));


    public MainPage() {
        heading.shouldBe(visible).shouldBe(exactText("Путешествие дня"));
    }

    public CreditPage getCreditPage() {
        creditButton.click();
        formHeading.shouldBe(visible).shouldBe(exactText("Кредит по данным карты"));
        return new CreditPage();
    }

    public DebitPage getDebitPage() {
        paymentButton.click();
        formHeading.shouldBe(visible).shouldBe(exactText("Оплата по карте"));
        return new DebitPage();
    }
}