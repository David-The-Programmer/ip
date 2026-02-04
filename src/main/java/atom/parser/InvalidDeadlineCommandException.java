package atom.parser;

/**
 * Exception thrown when a 'deadline' command is missing a description or a valid /by date.
 */
public class InvalidDeadlineCommandException extends Exception {

    /**
     * Constructs a new InvalidDeadlineCommandException with the specified message and cause.
     * @param message The error message.
     * @param cause The underlying cause of the exception.
     */
    public InvalidDeadlineCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
