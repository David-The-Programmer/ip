package atom.parser;

/**
 * Exception thrown when a 'todo' command is malformed or missing required information.
 */
public class InvalidToDoCommandException extends Exception {

    /**
     * Constructs a new InvalidToDoCommandException with the specified message and cause.
     *
     * @param message The error message.
     * @param cause   The underlying cause of the exception.
     */
    public InvalidToDoCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
