package ru.netology.journey.data;

import ru.netology.journey.pages.DebitPage;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DateHelper {

    public DateHelper() {
    }

    public static String getValidMonthAndYear() {
        Random random = new Random();
        int min = 0;
        int max = 61;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(("dd.MM.yyyy"));
        return LocalDate.now().plusMonths(random.nextInt(max - min) + min).format(formatter);
    }

    public static String getInvalidMonthAndYear() {
        Random random = new Random();
        int min = 61;
        int max = 194;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(("dd.MM.yyyy"));
        return LocalDate.now()
                .plusMonths(random.nextInt(max - min) + min)
                .format(formatter);
    }

    public static String getExpiredMonthAndYear() {
        Random random = new Random();
        int min = 1;
        int max = 121;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(("dd.MM.yyyy"));
        return LocalDate.now()
                .minusMonths(random.nextInt(max - min) + min)
                .format(formatter);
    }

    public static String getMonthContainsSpecialSymbol() {
        Random random = new Random();
        int min = 0;
        int max = 72;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(("dd.MM.yyyy"));
        return LocalDate.now()
                .plusMonths(random.nextInt(max - min) + min).format(formatter)
                .charAt(3) + SymbolsHelper.getNSpecialSymbols(1);
    }

    public static String getMonthContainsLetter() {
        Random random = new Random();
        int min = 0;
        int max = 72;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(("dd.MM.yyyy"));
        return LocalDate.now()
                .plusMonths(random.nextInt(max - min) + min).format(formatter)
                .charAt(3) + SymbolsHelper.getNEnglishLetters(1);
    }

    public static String getMonthContainsOneDigit() {
        Random random = new Random();
        int min = 0;
        int max = 72;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(("dd.MM.yyyy"));
        return LocalDate.now()
                .plusMonths(random.nextInt(max - min) + min).format(formatter)
                .substring(3, 4);
    }

    public static String getYearContainsLetter() {
        Random random = new Random();
        int min = 0;
        int max = 72;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(("dd.MM.yyyy"));
        return LocalDate.now()
                .plusMonths(random.nextInt(max - min) + min).format(formatter)
                .substring(8, 9) + SymbolsHelper.getNEnglishLetters(1);
    }

    public static String getYearContainsSpecialSymbol() {
        Random random = new Random();
        int min = 0;
        int max = 72;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(("dd.MM.yyyy"));
        return LocalDate.now()
                .plusMonths(random.nextInt(max - min) + min).format(formatter)
                .substring(8, 9) + SymbolsHelper.getNSpecialSymbols(1);
    }

    public static String getYearContainsOneDigit() {
        Random random = new Random();
        int min = 0;
        int max = 72;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(("dd.MM.yyyy"));
        return LocalDate.now()
                .plusMonths(random.nextInt(max - min) + min).format(formatter)
                .substring(8, 9);
    }


    public static String getMonthContainsZeros() {
        return "00";
    }

    public static String getNumberGreaterThanTwelve() {
        return SymbolsHelper.getRandomDigitGreaterThanTwelve();
    }

    public static void shouldAssertAmountAndPrice() {
        long expectedAmount = RequestSQL.getAmountPaymentEntity();
        long actualAmount = DebitPage.getPriceTour();
        assertEquals(expectedAmount, actualAmount);
    }

}
