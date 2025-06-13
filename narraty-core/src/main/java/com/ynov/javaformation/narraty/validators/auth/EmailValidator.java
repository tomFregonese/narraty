package com.ynov.javaformation.narraty.validators.auth;

public class EmailValidator {

    public static boolean isValid(String email)  {
        String emailRegex = "^[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$";
        if (email == null || email.isEmpty()) {
            return false;
        }
        return email.matches(emailRegex);
        }

}
