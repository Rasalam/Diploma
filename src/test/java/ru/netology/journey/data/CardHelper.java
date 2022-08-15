package ru.netology.journey.data;

import com.github.javafaker.Faker;

import java.util.Locale;
import java.util.Random;

public class CardHelper {

    public CardHelper() {
    }

    public static String getApprovedCardNumber() {
        return "4444444444444441";
    }

    public static String getDeclinedCardNumber() {
        return "4444444444444442";
    }

    public static String getCardStatus(int index) {
        String[] status = new String[2];
        status[0] = "APPROVED";
        status[1] = "DECLINED";
        return status[index];

    }

    public static String getCardNumberOfFifteenDigits(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String result = faker.business().creditCardNumber();
        return result.replaceAll("-", "").substring(0, 15);
    }

    public static String getCardNumberContainsSpecialSymbol(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String result = faker.business().creditCardNumber();
        return result.replaceAll("-", "").substring(0, 15) + SymbolsHelper.getNSpecialSymbols(1);

    }

    public static String getCardNumberContainsLetter(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String result = faker.business().creditCardNumber();
        return result.replaceAll("-", "").substring(0, 15) + SymbolsHelper.getNEnglishLetters(1);
    }

    public static String getRandomCardNumber(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String result = faker.business().creditCardNumber();
        return result.replaceAll("-", "");

    }

    public static String getCardNumberOfSixteenZeros() {
        return "0000000000000000";

    }

    public static String getCardNumberOfOneDigits() {
        Random random = new Random();
        int min = 0;
        int max = 10;
        return String.valueOf(random.nextInt(max - min) + min);
    }

}
