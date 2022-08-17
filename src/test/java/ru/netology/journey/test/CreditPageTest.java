package ru.netology.journey.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.journey.data.*;
import ru.netology.journey.pages.CreditPage;
import ru.netology.journey.pages.MainPage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CreditPageTest {
    private MainPage mainPage;
    private CreditPage creditPage;
    private final String validMonthAndYear = DateHelper.getValidMonthAndYear();
    private final String invalidMonthAndYear = DateHelper.getInvalidMonthAndYear();
    private final String expiredMonthAndYear = DateHelper.getExpiredMonthAndYear();

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("AllureSelenide", new AllureSelenide().screenshots(true).savePageSource(false));
    }

    @BeforeEach
    public void setUp() {
        mainPage = open("http://localhost:8080/", MainPage.class);

    }

    @AfterEach
    public void clearDB() {
        RequestSQL.clearDataBase();
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("AllureSelenide");
    }

    @Test
    @DisplayName("01. PopUp notice 'Операция одобрена банком', статус карты APPROVED")
    void shouldShowApprovedNotice() {
        creditPage = mainPage.getCreditPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        creditPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        String actualStatus = RequestSQL.getCreditRequestEntityStatus();
        String expectStatus = CardHelper.getCardStatus(0);
        assertEquals(expectStatus, actualStatus);

    }

    @Test
    @DisplayName("02. PopUp notice 'Банк отказал в проведении операции', статус карты DECLINED")
    void shouldShowDeclinedNoticeIfCardStatusIsDeclined() {
        creditPage = mainPage.getCreditPage();
        String cardNumber = CardHelper.getDeclinedCardNumber();
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        creditPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        creditPage.shouldChangeInscriptionOnButton();
        creditPage.shouldShowDeclinedNotice();
        String expected = CardHelper.getCardStatus(1);
        String actual = RequestSQL.getCreditRequestEntityStatus();
        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("03. PopUp notice 'Банк отказал в проведении операции', случайная карта")
    void shouldShowNoticeIfCardNumberIsInvalid() {
        creditPage = mainPage.getCreditPage();
        String cardNumber = CardHelper.getRandomCardNumber("ru");
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        creditPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        creditPage.shouldShowDeclinedNotice();

    }

    @Test
    @DisplayName("04. Notice 'Проверьте номер карты', номер карты из 1 цифры")
    void shouldShowNoticeIfCardNumberOfOneDigit() {
        creditPage = mainPage.getCreditPage();
        String cardNumber = CardHelper.getCardNumberOfOneDigits();
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        creditPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        creditPage.shouldShowNoticeIfCardNumberIsInvalid();
    }

    @Test
    @DisplayName("05. Notice 'Проверьте номер карты', номер карты из 15 цифр")
    void shouldShowNoticeIfCardNumberOfFifteenDigits() {
        creditPage = mainPage.getCreditPage();
        String cardNumber = CardHelper.getCardNumberOfFifteenDigits("ru");
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        creditPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        creditPage.shouldShowNoticeIfCardNumberIsInvalid();

    }

    @Test
    @DisplayName("06. PopUp notice 'Банк отказал в проведении операции', номер карты из 16 нулей")
    void shouldShowNoticeIfCardNumberOfSixteenZeros() {
        creditPage = mainPage.getCreditPage();
        String cardNumber = CardHelper.getCardNumberOfSixteenZeros();
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        creditPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        creditPage.shouldShowDeclinedNotice();

    }

    @Test
    @DisplayName("07. Notice 'Поле обязательно для заполнения', номер карты не заполнен")
    void shouldShowNoticeIfCardNumberIsEmpty() {
        creditPage = mainPage.getCreditPage();
        String cardNumber = "";
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        creditPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        creditPage.shouldShowNoticeIfFiledIsEmpty();
    }

    @Test
    @DisplayName("08. Notice 'Проверьте номер карты', ввод спецсимвола в поле 'номер карты'")
    void shouldShowNoticeIfCardNumberContainSpecialSymbol() {
        creditPage = mainPage.getCreditPage();
        String cardNumber = CardHelper.getCardNumberContainsSpecialSymbol("ru");
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        creditPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        creditPage.shouldShowNoticeIfCardNumberIsInvalid();
    }

    @Test
    @DisplayName("09. Notice 'Проверьте номер карты', ввод буквы в поле 'номер карты'")
    void shouldShowNoticeIfCardNumberContainEnglishLetter() {
        creditPage = mainPage.getCreditPage();
        String cardNumber = CardHelper.getCardNumberContainsLetter("ru");
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        creditPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        creditPage.shouldShowNoticeIfCardNumberIsInvalid();

    }

    @Test
    @DisplayName("10. Notice 'Неверно указан срок действия карты', поле 'месяц' заполнено частично")
    void shouldShowNoticeIfMonthIsNotCompletelyFilled() {
        creditPage = mainPage.getCreditPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = DateHelper.getMonthContainsOneDigit();
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        creditPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        creditPage.shouldShowNoticeIfExpireDateIsInvalid();

    }

    @Test
    @DisplayName("11. Notice 'Неверно указан срок действия карты', поле 'месяц' заполнено нулями")
    void shouldShowNoticeIfMonthContainTwoZeros() {
        creditPage = mainPage.getCreditPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = DateHelper.getMonthContainsZeros();
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        creditPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        creditPage.shouldShowNoticeIfExpireDateIsInvalid();
    }

    @Test
    @DisplayName("12. Notice 'Неверно указан срок действия карты', в поле 'месяц' введено число больше 12")
    void shouldShowNotificationIfMonthContainTwoDigitNumberGreaterThanThirteen() {
        creditPage = mainPage.getCreditPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = DateHelper.getNumberGreaterThanTwelve();
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        creditPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        creditPage.shouldShowNoticeIfExpireDateIsInvalid();

    }

    @Test
    @DisplayName("13. Notice 'Истёк срок действия карты'")
    void shouldShowNotificationIfCardExpired() {
        creditPage = mainPage.getCreditPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = expiredMonthAndYear.substring(3, 5);
        String year = expiredMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        creditPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        creditPage.shouldShowNoticeIfCardExpired();

    }

    @Test
    @DisplayName("14. Notice 'Неверно указан срок действия карты', срок действия карты превышает 6 лет")
    void shouldShowNotificationIfCardValidityExceedsSixYears() {
        creditPage = mainPage.getCreditPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = invalidMonthAndYear.substring(3, 5);
        String year = invalidMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        creditPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        creditPage.shouldShowNoticeIfExpireDateIsInvalid();

    }

    @Test
    @DisplayName("15. Notice 'Неверно указан срок действия карты', ввод спецсимвола в поле 'месяц'")
    void shouldShowNotificationIfMonthContainSpecialSymbol() {
        creditPage = mainPage.getCreditPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = DateHelper.getMonthContainsSpecialSymbol();
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        creditPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        creditPage.shouldShowNoticeIfExpireDateIsInvalid();

    }

    @Test
    @DisplayName("16. Notice 'Неверно указан срок действия карты', ввод буквы в поле 'месяц'")
    void shouldShowNoticeIfMonthContainEnglishLetter() {
        creditPage = mainPage.getCreditPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = DateHelper.getMonthContainsLetter();
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        creditPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        creditPage.shouldShowNoticeIfExpireDateIsInvalid();
    }

    @Test
    @DisplayName("17. Notice 'Поле обязательно для заполнения', поле 'месяц'не заполнено")
    void shouldShowNoticeIfMonthIsEmpty() {
        creditPage = mainPage.getCreditPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = "";
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        creditPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        creditPage.shouldShowNoticeIfFiledIsEmpty();
    }

    @Test
    @DisplayName("18. Notice 'Неверно указан срок действия карты', поле 'год' заполнено частично")
    void shouldShowNotificationIfYearIsNotCompletelyFilled() {
        creditPage = mainPage.getCreditPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = validMonthAndYear.substring(3, 5);
        String year = DateHelper.getYearContainsOneDigit();
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        creditPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        creditPage.shouldShowNoticeIfExpireDateIsInvalid();

    }

    @Test
    @DisplayName("19. Notice 'Неверно указан срок действия карты', ввод буквы в поле 'год'")
    void shouldShowNotificationIfYearContainEnglishLetter() {
        creditPage = mainPage.getCreditPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = validMonthAndYear.substring(3, 5);
        String year = DateHelper.getYearContainsLetter();
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        creditPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        creditPage.shouldShowNoticeIfExpireDateIsInvalid();
    }

    @Test
    @DisplayName("20. Notice 'Неверно указан срок действия карты', ввод спецсимвола в поле 'год'")
    void shouldShowNotificationIfYearContainSpecialSymbol() {
        creditPage = mainPage.getCreditPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = validMonthAndYear.substring(3, 5);
        String year = DateHelper.getYearContainsSpecialSymbol();
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        creditPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        creditPage.shouldShowNoticeIfExpireDateIsInvalid();

    }

    @Test
    @DisplayName("21. Notice 'Неверно указан срок действия карты', поле 'год' не заполнено")
    void shouldShowNotificationIfYearIsEmpty() {
        creditPage = mainPage.getCreditPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = validMonthAndYear.substring(3, 5);
        String year = "";
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        creditPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        creditPage.shouldShowNoticeIfExpireDateIsInvalid();

    }

    @Test
    @DisplayName("22. Notice 'Допустимы латинские символы, точка, дефис, апостроф', поле заполнено на кириллице")
    void shouldShowNotificationIfOwnerCompletedInCyrillic() {
        creditPage = mainPage.getCreditPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("ru");
        String cvv = CVVHelper.getValidCVV();
        creditPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        creditPage.shouldShowNoticeIfOwnerContainInvalidSymbols();

    }

    @Test
    @DisplayName("23. Notice 'Допустимы латинские символы, точка, дефис, апостроф', поле 'Владелец' содержит символы кириллицы и латинницы")
    void shouldShowNotificationIfOwnerCompletedInCyrillicAndLatin() {
        creditPage = mainPage.getCreditPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getOwnerContainsCyrillicChars();
        String cvv = CVVHelper.getValidCVV();
        creditPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        creditPage.shouldShowNoticeIfOwnerContainInvalidSymbols();

    }

    @Test
    @DisplayName("24. Notice 'Допустимы латинские символы, точка, дефис, апостроф', ввод цифры в поле 'Владелец'")
    void shouldShowNoticeIfOwnerContainsDigit() {
        creditPage = mainPage.getCreditPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getOwnerContainsDigit("en");
        String cvv = CVVHelper.getValidCVV();
        creditPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        creditPage.shouldShowNoticeIfOwnerContainInvalidSymbols();

    }

    @Test
    @DisplayName("25. Notice 'Допустимы латинские символы, точка, дефис, апостроф', ввод спецсомвола в поле 'Владелец'")
    void shouldShowNoticeIfOwnerContainsSpecialSymbol() {
        creditPage = mainPage.getCreditPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getOwnerContainsSpecialSymbol("en");
        String cvv = CVVHelper.getValidCVV();
        creditPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        creditPage.shouldShowNoticeIfOwnerContainInvalidSymbols();

    }

    @Test
    @DisplayName("26. PopUp notice 'Операция одобрена банком', поле 'Владелец' содержит одно слово")
    void shouldShowNoticeIfOwnerContainsFirstNameOnly() {
        creditPage = mainPage.getCreditPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getOwnerContainsFirstNameOnly("en");
        String cvv = CVVHelper.getValidCVV();
        creditPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
//        creditPage.shouldChangeInscriptionOnButton();
        creditPage.shouldShowApprovedNotice();

    }

    @Test
    @DisplayName("27. Notice 'Длина поля не может превышаеть 26 символов', в поле 'Владелец' введено более 26 символов")
    void shouldShowNoticeIfOwnerContainsOverTwentySixSymbols() {
        creditPage = mainPage.getCreditPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getOwnerVeryLongName("en");
        String cvv = CVVHelper.getValidCVV();
        creditPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        creditPage.shouldShowNoticeIfOwnerContainsOverTwentySixSymbols();

    }

    @Test
    @DisplayName("28. Notice 'Поле должно содержать не менее двух символов', поле 'Владелец' содержит 1 букву")
    void shouldShowNoticeIfOwnerContainsOneLetter() {
        creditPage = mainPage.getCreditPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getOwnerConsistingOfOneLetter("en");
        String cvv = CVVHelper.getValidCVV();
        creditPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        creditPage.shouldShowNoticeIfOwnerContainsOneLetter();

    }

    @Test
    @DisplayName("29. Notice 'Поле обязательно для заполнения', поле 'Владелец' пустое")
    void shouldShowNoticeIfOwnerIsEmpty() {
        creditPage = mainPage.getCreditPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = "";
        String cvv = CVVHelper.getValidCVV();
        creditPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        creditPage.shouldShowNoticeIfFiledIsEmpty();

    }

    @Test
    @DisplayName("30. PopUp notice 'Операция одобрена банком', поле 'Владелец' заполнено строчными буквами")
    void shouldShowNoticeApprovedIfOwnerEnteredInLowerCase() {
        creditPage = mainPage.getCreditPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerLowerCase("en");
        String cvv = CVVHelper.getValidCVV();
        creditPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
//        creditPage.shouldChangeInscriptionOnButton();
        creditPage.shouldShowApprovedNotice();

    }

    @Test
    @DisplayName("31. Notice 'Проверьте защитный код карты', в поле 'CVV' введены 2 цифры")
    void shouldShowNoticeIfCVVContainsTwoDigits() {
        creditPage = mainPage.getCreditPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getTwoDigitCVV();
        creditPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        creditPage.shouldShowNoticeIfCVVIsInvalid();

    }

    @Test
    @DisplayName("32. Notice 'Проверьте защитный код карты', в поле 'CVV' введена 1 цифра")
    void shouldShowNoticeIfCVVContainsOneDigit() {
        creditPage = mainPage.getCreditPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getOneDigitCVV();
        creditPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        creditPage.shouldShowNoticeIfCVVIsInvalid();

    }

    @Test
    @DisplayName("33. Notice 'Проверьте защитный код карты', ввод в поле 'CVV' цифр и буквы")
    void shouldShowNoticeIfCVVContainsOneLetter() {
        creditPage = mainPage.getCreditPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getCVVContainsLetter();
        creditPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        creditPage.shouldShowNoticeIfCVVIsInvalid();

    }


    @Test
    @DisplayName("34. Notice 'Проверьте защитный код карты', ввод в поле 'CVV' цифр и спецсимвол")
    void shouldShowNoticeIfCVVContainsSpecialSymbol() {
        creditPage = mainPage.getCreditPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getCVVContainsSpecialSymbol();
        creditPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        creditPage.shouldShowNoticeIfCVVIsInvalid();

    }

    @Test
    @DisplayName("35. Notice 'Поле обязательно для заполнения', поле CVV не заполнено")
    void shouldShowNoticeIfCVVIsEmpty() {
        creditPage = mainPage.getCreditPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = "";
        creditPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        creditPage.shouldShowNoticeIfFiledIsEmpty();
    }
}