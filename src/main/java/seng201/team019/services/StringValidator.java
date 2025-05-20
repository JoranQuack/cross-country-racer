package seng201.team019.services;

import java.util.regex.Pattern;

/**
 * StringValidator is a utility class for validating strings.
 * It checks if a string is only made up of non-special characters.
 *
 * @author Ethan Elliot
 * @author Joran Le Quellec
 */
public class StringValidator {
    private static final Pattern VALID_STRING_REGEX = Pattern.compile("[a-zA-Z0-9]+$");

    /**
     * Checks if a string is only made up of non-special characters.
     *
     * @param string input
     * @return true if string is valid
     */
    public boolean isInvalid(String string, int minLength, int maxLength) {
        return string.length() < minLength || string.length() > maxLength
                || !VALID_STRING_REGEX.matcher(string.replaceAll("\\s+", "")).matches();
    }
}
