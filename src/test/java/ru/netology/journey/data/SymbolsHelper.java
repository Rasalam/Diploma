package ru.netology.journey.data;

import java.util.Random;

public class SymbolsHelper {

    public static String getNSpecialSymbols(int count) {
        Random random = new Random();
        String[] specialSymbols = {"@", "!", "%", "$", "#", "^", "&", "(", ")", "_", "+", "?", "<", "'"};
        StringBuilder resultString = new StringBuilder();
        for (int i = 0; i < count; i++) {
            int indexOfSpecialSymbol = random.nextInt(14);
            resultString.append(specialSymbols[indexOfSpecialSymbol]);
        }
        return resultString.toString();

    }

    public static String getNSpecialSymbolsForOwner(int count) {
        Random random = new Random();
        String[] specialSymbols = {"@", "!", "%", "$", "#", "^", "&", "(", ")", "_", "+", "?", "<"};
        StringBuilder resultString = new StringBuilder();
        for (int i = 0; i < count; i++) {
            int indexOfSpecialSymbol = random.nextInt(13);
            resultString.append(specialSymbols[indexOfSpecialSymbol]);
        }
        return resultString.toString();

    }

    public static String getNEnglishLetters(int count) {
        Random random = new Random();
        String[] englishLetters = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        StringBuilder resultString = new StringBuilder();
        for (int i = 0; i < count; i++) {
            int indexOfEnglishLetter = random.nextInt(26);
            resultString.append(englishLetters[indexOfEnglishLetter]);
        }
        return resultString.toString();

    }

    public static String getRandomDigitGreaterThanTwelve() {
        Random random = new Random();
        return String.valueOf(13 + random.nextInt(100 - 13));

    }

}
