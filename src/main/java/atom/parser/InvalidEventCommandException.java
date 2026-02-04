package atom.parser;

/**
 * Exception thrown when an 'event' command has an invalid format or logical date errors.
 */
public class InvalidEventCommandException extends Exception {

    /**
     * Constructs a new InvalidEventCommandException with the specified message and cause.
     * @param message The error message.
     * @param cause The underlying cause of the exception.
     */
    public InvalidEventCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
