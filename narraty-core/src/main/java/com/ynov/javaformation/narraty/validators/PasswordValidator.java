package com.ynov.javaformation.narraty.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {

    private static final Pattern SPECIAL_CHAR_PATTERN = Pattern.compile("[!@#$%^&*()_+\\-=\\[\\]{}|;:'\"\\\\\\,.<>/?`~]");

    public static boolean IsSecure(String password) {
        if (password == null || password.trim().isEmpty()) {
            return false;
        }

        if (password.length() < 8) {
            return false;
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        Matcher specialCharMatcher = SPECIAL_CHAR_PATTERN.matcher(password);
        boolean hasSpecial = specialCharMatcher.find();

        for (char ch : password.toCharArray()) {
            if (Character.isUpperCase(ch)) {
                hasUpper = true;
            } else if (Character.isLowerCase(ch)) {
                hasLower = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            }
        }

        return hasUpper && hasLower && hasDigit && hasSpecial;
    }
}