package ru.netology.journey.data;

import com.github.javafaker.Faker;

import java.util.Locale;
import java.util.Random;


public class OwnerHelper {

    public static String getValidOwnerUpperCase(String locale) {
        int maxNameLength = 26;
        Faker faker = new Faker(new Locale(locale));
        String firstName = faker.name().firstName().toUpperCase();
        String lastName = faker.name().lastName().toUpperCase();
        String owner;
        if (((firstName + " " + lastName).length()) > maxNameLength) {
            firstName = firstName.charAt(0) + ". ";
            owner = firstName + lastName;
        } else owner = firstName + " " + lastName;
        return owner;

    }

    public static String getValidOwnerLowerCase(String locale) {
        int maxNameLength = 26;
        Faker faker = new Faker(new Locale(locale));
        String firstName = faker.name().firstName().toLowerCase();
        String lastName = faker.name().lastName().toLowerCase();
        String owner;
        if (((firstName + " " + lastName).length()) > maxNameLength) {
            firstName = firstName.charAt(0) + ". ";
            owner = firstName + lastName;
        } else owner = firstName + " " + lastName;
        return owner;

    }

    public static String getOwnerContainsDigit(String locale) {
        Random random = new Random();
        int maxNameLength = 26;
        Faker faker = new Faker(new Locale(locale));
        String firstName = faker.name().firstName().toUpperCase();
        String lastName = faker.name().lastName().toUpperCase();
        String owner;
        if (((firstName + " " + lastName).length()) > maxNameLength - 1) {
            firstName = firstName.charAt(0) + ". " + random.nextInt(9);
            owner = firstName + lastName;
        } else owner = firstName + " " + random.nextInt(9) + lastName;
        return owner;

    }

    public static String getOwnerContainsSpecialSymbol(String locale) {
        int maxNameLength = 26;
        Faker faker = new Faker(new Locale(locale));
        String firstName = faker.name().firstName().toUpperCase();
        String lastName = faker.name().lastName().toUpperCase();
        String owner;
        if (((firstName + " " + lastName).length()) > maxNameLength - 1) {
            firstName = firstName.charAt(0) + ". " + SymbolsHelper.getNSpecialSymbolsForOwner(1);
            owner = firstName + lastName;
        } else owner = firstName + " " + SymbolsHelper.getNSpecialSymbolsForOwner(1) + lastName;
        return owner;

    }

    public static String getOwnerVeryLongName(String locale) {
        Faker faker = new Faker(new Locale(locale));
        String firstName = faker.name().firstName().toUpperCase();
        String lastName = faker.name().lastName().toUpperCase();
        StringBuilder result = new StringBuilder();
        while (result.length() <= 27) {
            result.append(firstName).append(" ").append(lastName).append(" ");
        }
        return result.toString();

    }

    public static String getOwnerConsistingOfOneLetter(String locale) {
        Faker faker = new Faker(new Locale(locale));
        return String.valueOf(faker.name().firstName().toUpperCase().charAt(1));

    }

    public static String getOwnerContainsCyrillicChars() {
        int maxNameLength = 26;
        Faker fakerRu = new Faker(new Locale("ru"));
        String firstName = fakerRu.name().firstName().toUpperCase();
        Faker fakerEn = new Faker(new Locale("en"));
        String lastName = fakerEn.name().lastName().toUpperCase();
        String owner;
        if (((firstName + " " + lastName).length()) > maxNameLength) {
            firstName = firstName.charAt(0) + ". ";
            owner = firstName + lastName;
        } else owner = firstName + " " + lastName;
        return owner;
    }

    public static String getOwnerContainsFirstNameOnly(String locale) {
        int maxNameLength = 26;
        Faker fakerRu = new Faker(new Locale(locale));
        String firstName = fakerRu.name().firstName().toUpperCase();
        if ((firstName.length()) > maxNameLength) {
            firstName = firstName.charAt(0) + ". ";
        }
        return firstName;

    }
}
