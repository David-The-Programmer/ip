package atom.parser;

/**
 * Exception thrown when the 'unmark' command provided by the user is invalid or malformed.
 */
public class InvalidUnmarkCommandException extends Exception {

    /**
     * Initializes the exception with a detail message and a cause.
     * @param message The error message explaining why the unmark command is invalid.
     * @param cause The underlying cause of the exception.
     */
    public InvalidUnmarkCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
