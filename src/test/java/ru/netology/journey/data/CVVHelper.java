package ru.netology.journey.data;

import java.util.Random;

public class CVVHelper {

    public static String getValidCVV() {
        Random random = new Random();
        String[] CVV = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        StringBuilder resultString = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            int indexOfCVV = random.nextInt(3);
            resultString.append(CVV[indexOfCVV]);
        }
        return resultString.toString();

    }

    public static String getTwoDigitCVV() {
        return getValidCVV().substring(0, 2);
    }

    public static String getOneDigitCVV() {
        return getValidCVV().substring(0, 1);
    }

    public static String getCVVContainsSpecialSymbol() {
        String CVV = getValidCVV().substring(0, 2);
        return CVV + SymbolsHelper.getNSpecialSymbols(1);

    }

    public static String getCVVContainsLetter() {
        String CVV = getValidCVV().substring(0, 2);
        return CVV + SymbolsHelper.getNEnglishLetters(1);

    }

}