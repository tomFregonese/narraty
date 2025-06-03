package com.ynov.javaformation.narraty.interfaces.services;

public interface IPasswordService {

    /**
     * Generates a secure random salt.
     * Hashes the password using SHA512 hashing algorithm and the generated salt.
     * Combine Salt and Hashed password to create a secure password hash.
     *
     * @param password the plain text password to hash
     * @return the hashed password
     */
    String hashPassword(String password);

    /**
     * Verifies a plain text password against a hashed password.
     *
     * @param plainPassword the plain text password to verify
     * @param hashedPassword the hashed password to compare against
     * @return true if the passwords match, false otherwise
     */
    boolean verifyPassword(String plainPassword, String hashedPassword);

}
