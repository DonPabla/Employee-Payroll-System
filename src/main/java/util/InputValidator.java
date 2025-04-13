package util;

import java.util.regex.Pattern;

public class InputValidator {
    public static boolean isValidEmail(String email) {
        return Pattern.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$", email);
    }

    public static boolean isEmpty(String input) {
        if (input == null || input.trim().isEmpty()) {
            System.out.println("Input cannot be empty!");
            return true;
        }
        return false;
    }
}

