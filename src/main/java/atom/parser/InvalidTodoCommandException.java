package atom.parser;

/**
 * Exception thrown when a 'todo' command is malformed or missing required information.
 */
public class InvalidTodoCommandException extends Exception {

    /**
     * Constructs a new InvalidTodoCommandException with the specified message and cause.
     * @param message The error message.
     * @param cause The underlying cause of the exception.
     */
    public InvalidTodoCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
