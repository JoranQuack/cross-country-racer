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
     * Constructs a new {@link StringValidator} instance.
     */
    public StringValidator() {
        // No initialization required
    }

    /**
     * Checks if a string is only made up of non-special characters.
     *
     * @param string    input
     * @param maxLength the max valid length
     * @param minLength the min valid length
     * @return true if string is valid
     */
    public boolean isInvalid(String string, int minLength, int maxLength) {
        return string.length() < minLength || string.length() > maxLength
                || !VALID_STRING_REGEX.matcher(string.replaceAll("\\s+", "")).matches();
    }
}
