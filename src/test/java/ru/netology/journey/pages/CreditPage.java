package ru.netology.journey.pages;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;


public class CreditPage {
    private final SelenideElement cardNumberElement = $("[placeholder='0000 0000 0000 0000']");
    private final SelenideElement monthElement = $("[placeholder='08']");
    private final SelenideElement yearElement = $("[placeholder='22']");
    private final SelenideElement ownerElement = $$("[class='input__control']").get(3);
    private final SelenideElement cvvElement = $("[placeholder='999']");
    private final SelenideElement continueButton = $(byText("Продолжить"));
    private final SelenideElement noticeApproved = $(byText("Операция одобрена Банком."));
    private final SelenideElement noticeDeclined = $(byText("Ошибка! Банк отказал в проведении операции."));
    private final SelenideElement noticeErrorFiled = $("[class='input__sub']");


    public CreditPage() {
        SelenideElement heading = $(byText("Кредит по данным карты"));
        heading.shouldBe(visible);

    }

    public void fillFormAndClickContinueButton(String cardNumber, String month, String year, String cardOwner, String cvv) {
        cardNumberElement.sendKeys(cardNumber);
        monthElement.sendKeys(month);
        yearElement.sendKeys(year);
        ownerElement.sendKeys(cardOwner);
        cvvElement.sendKeys(cvv);
        continueButton.click();

    }

    public void shouldShowApprovedNotice() {
        noticeApproved.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void shouldShowDeclinedNotice() {
        noticeDeclined.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void shouldShowNoticeIfFiledIsEmpty() {
        noticeErrorFiled.shouldBe(visible).shouldHave(text("Поле обязательно для заполнения"));
    }

    public void shouldShowNoticeIfCardNumberIsInvalid() {
        noticeErrorFiled.shouldBe(visible).shouldHave(text("Проверьте номер карты"));
    }

    public void shouldShowNoticeIfExpireDateIsInvalid() {
        noticeErrorFiled.shouldBe(visible).shouldHave(text("Неверно указан срок действия карты"));
    }


    public void shouldShowNoticeIfCardExpired() {
        noticeErrorFiled.shouldBe(visible).shouldHave(text("Истёк срок действия карты"));
    }

    public void shouldShowNoticeIfOwnerContainInvalidSymbols() {
        noticeErrorFiled.shouldBe(visible).shouldHave(text("Допустимы латинские символы, знаки \"-\", \".\", \"'\""));
    }

    public void shouldShowNoticeIfOwnerContainsOneLetter() {
        noticeErrorFiled.shouldBe(visible).shouldHave(text("Поле должно содержать не менее двух символов"));
    }

    public void shouldShowNoticeIfOwnerContainsOverTwentySixSymbols() {
        noticeErrorFiled.shouldBe(visible).shouldHave(text("Длина поля не может превышать 26 символов"));
    }

    public void shouldShowNoticeIfCVVIsInvalid() {
        noticeErrorFiled.shouldBe(visible).shouldHave(text("Проверьте защитный код карты"));
    }

}