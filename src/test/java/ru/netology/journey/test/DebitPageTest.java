package ru.netology.journey.test;

import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.*;
import ru.netology.journey.data.*;
import ru.netology.journey.pages.DebitPage;
import ru.netology.journey.pages.MainPage;

import static com.codeborne.selenide.Selenide.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class DebitPageTest {
    private MainPage mainPage;
    private DebitPage debitPage;
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
        debitPage = mainPage.getDebitPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        debitPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
//        debitPage.shouldChangeInscriptionOnButton();
        debitPage.shouldShowApprovedNotice();
        String expected = CardHelper.getCardStatus(0);
        String actual = RequestSQL.getPaymentEntityStatus();
        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("02. PopUp notice 'Банк отказал в проведении операции', статус карты DECLINED")
    void shouldShowDeclinedNoticeIfCardStatusIsDeclined() {
        debitPage = mainPage.getDebitPage();
        String cardNumber = CardHelper.getDeclinedCardNumber();
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        debitPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        debitPage.shouldShowDeclinedNotice();
        String expected = CardHelper.getCardStatus(1);
        String actual = RequestSQL.getPaymentEntityStatus();
        assertEquals(expected, actual);

    }

    @Test
    @DisplayName("03. PopUp notice 'Банк отказал в проведении операции', случайная карта")
    void shouldShowNoticeIfCardNumberIsInvalid() {
        debitPage = mainPage.getDebitPage();
        String cardNumber = CardHelper.getRandomCardNumber("ru");
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        debitPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        debitPage.shouldShowDeclinedNotice();

    }

    @Test
    @DisplayName("04. Notice 'Проверьте номер карты', номер карты из 1 цифры")
    void shouldShowNoticeIfCardNumberOfOneDigit() {
        debitPage = mainPage.getDebitPage();
        String cardNumber = CardHelper.getCardNumberOfOneDigits();
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        debitPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        debitPage.shouldShowNoticeIfCardNumberIsInvalid();
    }

    @Test
    @DisplayName("05. Notice 'Проверьте номер карты', номер карты из 15 цифр")
    void shouldShowNoticeIfCardNumberOfFifteenDigits() {
        debitPage = mainPage.getDebitPage();
        String cardNumber = CardHelper.getCardNumberOfFifteenDigits("ru");
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        debitPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        debitPage.shouldShowNoticeIfCardNumberIsInvalid();

    }

    @Test
    @DisplayName("06. PopUp notice 'Банк отказал в проведении операции', номер карты из 16 нулей")
    void shouldShowNoticeIfCardNumberOfSixteenZeros() {
        debitPage = mainPage.getDebitPage();
        String cardNumber = CardHelper.getCardNumberOfSixteenZeros();
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        debitPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        debitPage.shouldShowDeclinedNotice();

    }

    @Test
    @DisplayName("07. Notice 'Поле обязательно для заполнения', номер карты не заполнен")
    void shouldShowNoticeIfCardNumberIsEmpty() {
        debitPage = mainPage.getDebitPage();
        String cardNumber = "";
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        debitPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        debitPage.shouldShowNoticeIfFiledIsEmpty();
    }

    @Test
    @DisplayName("08. Notice 'Проверьте номер карты', ввод спецсимвола в поле 'номер карты'")
    void shouldShowNoticeIfCardNumberContainSpecialSymbol() {
        debitPage = mainPage.getDebitPage();
        String cardNumber = CardHelper.getCardNumberContainsSpecialSymbol("ru");
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        debitPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        debitPage.shouldShowNoticeIfCardNumberIsInvalid();
    }

    @Test
    @DisplayName("09. Notice 'Проверьте номер карты', ввод буквы в поле 'номер карты'")
    void shouldShowNoticeIfCardNumberContainEnglishLetter() {
        debitPage = mainPage.getDebitPage();
        String cardNumber = CardHelper.getCardNumberContainsLetter("ru");
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        debitPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        debitPage.shouldShowNoticeIfCardNumberIsInvalid();

    }

    @Test
    @DisplayName("10. Notice 'Неверно указан срок действия карты', поле 'месяц' заполнено частично")
    void shouldShowNoticeIfMonthIsNotCompletelyFilled() {
        debitPage = mainPage.getDebitPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = DateHelper.getMonthContainsOneDigit();
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        debitPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        debitPage.shouldShowNoticeIfExpireDateIsInvalid();

    }

    @Test
    @DisplayName("11. Notice 'Неверно указан срок действия карты', поле 'месяц' заполнено нулями")
    void shouldShowNoticeIfMonthContainTwoZeros() {
        debitPage = mainPage.getDebitPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = DateHelper.getMonthContainsZeros();
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        debitPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        debitPage.shouldShowNoticeIfExpireDateIsInvalid();
    }

    @Test
    @DisplayName("12. Notice 'Неверно указан срок действия карты', в поле 'месяц' введено число больше 12")
    void shouldShowNotificationIfMonthContainTwoDigitNumberGreaterThanThirteen() {
        debitPage = mainPage.getDebitPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = DateHelper.getNumberGreaterThanTwelve();
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        debitPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        debitPage.shouldShowNoticeIfExpireDateIsInvalid();

    }

    @Test
    @DisplayName("13. Notice 'Истёк срок действия карты'")
    void shouldShowNotificationIfCardExpired() {
        debitPage = mainPage.getDebitPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = expiredMonthAndYear.substring(3, 5);
        String year = expiredMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        debitPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        debitPage.shouldShowNoticeIfCardExpired();

    }

    @Test
    @DisplayName("14. Notice 'Неверно указан срок действия карты', срок действия карты превышает 6 лет")
    void shouldShowNotificationIfCardValidityExceedsSixYears() {
        debitPage = mainPage.getDebitPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = invalidMonthAndYear.substring(3, 5);
        String year = invalidMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        debitPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        debitPage.shouldShowNoticeIfExpireDateIsInvalid();

    }

    @Test
    @DisplayName("15. Notice 'Неверно указан срок действия карты', ввод спецсимвола в поле 'месяц'")
    void shouldShowNotificationIfMonthContainSpecialSymbol() {
        debitPage = mainPage.getDebitPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = DateHelper.getMonthContainsSpecialSymbol();
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        debitPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        debitPage.shouldShowNoticeIfExpireDateIsInvalid();

    }

    @Test
    @DisplayName("16. Notice 'Неверно указан срок действия карты', ввод буквы в поле 'месяц'")
    void shouldShowNoticeIfMonthContainEnglishLetter() {
        debitPage = mainPage.getDebitPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = DateHelper.getMonthContainsLetter();
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        debitPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        debitPage.shouldShowNoticeIfExpireDateIsInvalid();
    }

    @Test
    @DisplayName("17. Notice 'Поле обязательно для заполнения', поле 'месяц'не заполнено")
    void shouldShowNoticeIfMonthIsEmpty() {
        debitPage = mainPage.getDebitPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = "";
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        debitPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        debitPage.shouldShowNoticeIfFiledIsEmpty();
    }

    @Test
    @DisplayName("18. Notice 'Неверно указан срок действия карты', поле 'год' заполнено частично")
    void shouldShowNotificationIfYearIsNotCompletelyFilled() {
        debitPage = mainPage.getDebitPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = validMonthAndYear.substring(3, 5);
        String year = DateHelper.getYearContainsOneDigit();
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        debitPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        debitPage.shouldShowNoticeIfExpireDateIsInvalid();

    }

    @Test
    @DisplayName("19. Notice 'Неверно указан срок действия карты', ввод буквы в поле 'год'")
    void shouldShowNotificationIfYearContainEnglishLetter() {
        debitPage = mainPage.getDebitPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = validMonthAndYear.substring(3, 5);
        String year = DateHelper.getYearContainsLetter();
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        debitPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        debitPage.shouldShowNoticeIfExpireDateIsInvalid();
    }

    @Test
    @DisplayName("20. Notice 'Неверно указан срок действия карты', ввод спецсимвола в поле 'год'")
    void shouldShowNotificationIfYearContainSpecialSymbol() {
        debitPage = mainPage.getDebitPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = validMonthAndYear.substring(3, 5);
        String year = DateHelper.getYearContainsSpecialSymbol();
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        debitPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        debitPage.shouldShowNoticeIfExpireDateIsInvalid();

    }

    @Test
    @DisplayName("21. Notice 'Неверно указан срок действия карты', поле 'год' не заполнено")
    void shouldShowNotificationIfYearIsEmpty() {
        debitPage = mainPage.getDebitPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = validMonthAndYear.substring(3, 5);
        String year = "";
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getValidCVV();
        debitPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        debitPage.shouldShowNoticeIfExpireDateIsInvalid();

    }

    @Test
    @DisplayName("22. Notice 'Допустимы латинские символы, точка, дефис, апостроф', поле заполнено на кириллице")
    void shouldShowNotificationIfOwnerCompletedInCyrillic() {
        debitPage = mainPage.getDebitPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("ru");
        String cvv = CVVHelper.getValidCVV();
        debitPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        debitPage.shouldShowNoticeIfOwnerContainInvalidSymbols();

    }

    @Test
    @DisplayName("23. Notice 'Допустимы латинские символы, точка, дефис, апостроф', поле 'Владелец' содержит символы кириллицы и латинницы")
    void shouldShowNotificationIfOwnerCompletedInCyrillicAndLatin() {
        debitPage = mainPage.getDebitPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getOwnerContainsCyrillicChars();
        String cvv = CVVHelper.getValidCVV();
        debitPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        debitPage.shouldShowNoticeIfOwnerContainInvalidSymbols();

    }

    @Test
    @DisplayName("24. Notice 'Допустимы латинские символы, точка, дефис, апостроф', ввод цифры в поле 'Владелец'")
    void shouldShowNoticeIfOwnerContainsDigit() {
        debitPage = mainPage.getDebitPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getOwnerContainsDigit("en");
        String cvv = CVVHelper.getValidCVV();
        debitPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        debitPage.shouldShowNoticeIfOwnerContainInvalidSymbols();

    }

    @Test
    @DisplayName("25. Notice 'Допустимы латинские символы, точка, дефис, апостроф', ввод спецсомвола в поле 'Владелец'")
    void shouldShowNoticeIfOwnerContainsSpecialSymbol() {
        debitPage = mainPage.getDebitPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getOwnerContainsSpecialSymbol("en");
        String cvv = CVVHelper.getValidCVV();
        debitPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        debitPage.shouldShowNoticeIfOwnerContainInvalidSymbols();

    }

    @Test
    @DisplayName("26. PopUp notice 'Операция одобрена банком', поле 'Владелец' содержит одно слово")
    void shouldShowNoticeIfOwnerContainsFirstNameOnly() {
        debitPage = mainPage.getDebitPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getOwnerContainsFirstNameOnly("en");
        String cvv = CVVHelper.getValidCVV();
        debitPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
//        debitPage.shouldChangeInscriptionOnButton();
        debitPage.shouldShowApprovedNotice();

    }

    @Test
    @DisplayName("27. Notice 'Длина поля не может превышаеть 26 символов', в поле 'Владелец' введено более 26 символов")
    void shouldShowNoticeIfOwnerContainsOverTwentySixSymbols() {
        debitPage = mainPage.getDebitPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getOwnerVeryLongName("en");
        String cvv = CVVHelper.getValidCVV();
        debitPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        debitPage.shouldShowNoticeIfOwnerContainsOverTwentySixSymbols();

    }

    @Test
    @DisplayName("28. Notice 'Поле должно содержать не менее двух символов', поле 'Владелец' содержит 1 букву")
    void shouldShowNoticeIfOwnerContainsOneLetter() {
        debitPage = mainPage.getDebitPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getOwnerConsistingOfOneLetter("en");
        String cvv = CVVHelper.getValidCVV();
        debitPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        debitPage.shouldShowNoticeIfOwnerContainsOneLetter();

    }

    @Test
    @DisplayName("29. Notice 'Поле обязательно для заполнения', поле 'Владелец' пустое")
    void shouldShowNoticeIfOwnerIsEmpty() {
        debitPage = mainPage.getDebitPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = "";
        String cvv = CVVHelper.getValidCVV();
        debitPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        debitPage.shouldShowNoticeIfFiledIsEmpty();

    }

    @Test
    @DisplayName("30. PopUp notice 'Операция одобрена банком', поле 'Владелец' заполнено строчными буквами")
    void shouldShowNoticeApprovedIfOwnerEnteredInLowerCase() {
        debitPage = mainPage.getDebitPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerLowerCase("en");
        String cvv = CVVHelper.getValidCVV();
        debitPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
//        debitPage.shouldChangeInscriptionOnButton();
        debitPage.shouldShowApprovedNotice();

    }

    @Test
    @DisplayName("31. Notice 'Проверьте защитный код карты', в поле 'CVV' введены 2 цифры")
    void shouldShowNoticeIfCVVContainsTwoDigits() {
        debitPage = mainPage.getDebitPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getTwoDigitCVV();
        debitPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        debitPage.shouldShowNoticeIfCVVIsInvalid();

    }

    @Test
    @DisplayName("32. Notice 'Проверьте защитный код карты', в поле 'CVV' введена 1 цифра")
    void shouldShowNoticeIfCVVContainsOneDigit() {
        debitPage = mainPage.getDebitPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getOneDigitCVV();
        debitPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        debitPage.shouldShowNoticeIfCVVIsInvalid();

    }

    @Test
    @DisplayName("33. Notice 'Проверьте защитный код карты', ввод в поле 'CVV' цифр и буквы")
    void shouldShowNoticeIfCVVContainsOneLetter() {
        debitPage = mainPage.getDebitPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getCVVContainsLetter();
        debitPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        debitPage.shouldShowNoticeIfCVVIsInvalid();

    }


    @Test
    @DisplayName("34. Notice 'Проверьте защитный код карты', ввод в поле 'CVV' цифр и спецсимвола")
    void shouldShowNoticeIfCVVContainsSpecialSymbol() {
        debitPage = mainPage.getDebitPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = CVVHelper.getCVVContainsSpecialSymbol();
        debitPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        debitPage.shouldShowNoticeIfCVVIsInvalid();

    }

    @Test
    @DisplayName("35. Notice 'Проверьте защитный код карты', поле CVV не заполнено")
    void shouldShowNoticeIfCVVIsEmpty() {
        debitPage = mainPage.getDebitPage();
        String cardNumber = CardHelper.getApprovedCardNumber();
        String month = validMonthAndYear.substring(3, 5);
        String year = validMonthAndYear.substring(8, 10);
        String owner = OwnerHelper.getValidOwnerUpperCase("en");
        String cvv = "";
        debitPage.fillFormAndClickContinueButton(cardNumber, month, year, owner, cvv);
        debitPage.shouldShowNoticeIfFiledIsEmpty();
    }
}