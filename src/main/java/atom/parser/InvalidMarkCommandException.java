package atom.parser;

/**
 * Exception thrown when a 'mark' command is provided with an invalid task index or format.
 */
public class InvalidMarkCommandException extends Exception {

    /**
     * Constructs a new InvalidMarkCommandException with the specified message and cause.
     * @param message The error message.
     * @param cause The underlying cause of the exception.
     */
    public InvalidMarkCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
