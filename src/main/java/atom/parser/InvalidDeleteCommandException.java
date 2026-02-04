package atom.parser;

/**
 * Exception thrown when a 'delete' command is missing a number or references an invalid index.
 */
public class InvalidDeleteCommandException extends Exception {

    /**
     * Constructs a new InvalidDeleteCommandException with the specified message and cause.
     * @param message The error message.
     * @param cause The underlying cause of the exception.
     */
    public InvalidDeleteCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
