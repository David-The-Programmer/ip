package atom.parser;

/**
 * Exception thrown when a 'delete' command is malformed when trying to delete more than 1 task.
 */
public class InvalidMassDeleteCommandException extends Exception {

    /**
     * Constructs a new InvalidMassDeleteCommandException with the specified message and cause.
     *
     * @param message The error message.
     * @param cause   The underlying cause of the exception.
     */
    public InvalidMassDeleteCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
