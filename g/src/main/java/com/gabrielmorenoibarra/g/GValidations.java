package com.gabrielmorenoibarra.g;

/**
 * Utilities for making validations.
 * Created by Gabriel Moreno on 2017-05-27.
 */
public class GValidations {

    /**
     * Validate that mandatory data filled out when an account is created are right.
     * @param name User name.
     * @param surname User surname.
     * @param email User email.
     * @param password User password.
     * @return true whether the fields are right or false if not.
     */
    public static boolean isCreateAccountRight(String name, String surname, String email, String password) {
        if (name == null || name.equals("")) return false;
        if (surname == null || surname.equals("")) return false;
        if (!isLoginRight(email, password)) return false;
        return true;
    }

    /**
     * Validate that mandatory data filled out when a login is made are right.
     * @param email User email.
     * @param password User password.
     * @return true whether the fields are right or false if not.
     */
    public static boolean isLoginRight(String email, String password) {
        if (!isEmailRight(email)) return false;
        if (!isPasswordLengthRight(password)) return false;
        return true;
    }

    /**
     * Validate that a String is a possible email.
     * @param email User email.
     * @return true whether the email is valid or false if not.
     */
    public static boolean isEmailRight(String email) {
        if (email == null || !email.contains("@") || !email.contains(".") || email.contains(" ")) return false;
        return true;
    }

    /**
     * Validate that a String has at least 4 characters.
     * @param password User password.
     * @return true whether the password is valid or false if not.
     */
    public static boolean isPasswordLengthRight(String password) {
        if (password == null || password.length() < 4) return false;
        return true;
    }

    /**
     * @param password User password.
     * @param passwordConfirmation User password confirmation.
     * @return true whether the fields are the same and have the correct length or false if not.
     */
    public static boolean isPasswordConfirmationRight(String password, String passwordConfirmation) {
        if (!isPasswordLengthRight(password)) return false;
        if (!password.equals(passwordConfirmation)) return false;
        return true;
    }
}