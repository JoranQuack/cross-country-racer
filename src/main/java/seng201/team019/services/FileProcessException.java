package seng201.team019.services;

/**
 * Exception thrown when a file processing error occurs.
 *
 * @author Ethan Elliot
 * @author Joran Le Quellec
 */
public class FileProcessException extends RuntimeException {
    /**
     * Constructs a new FileProcessException with the specified detail message.
     *
     * @param message the detail message
     */
    public FileProcessException(String message) {
        super(message);
    }
}
