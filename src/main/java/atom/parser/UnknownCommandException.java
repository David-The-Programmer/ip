package atom.parser;

/**
 * Exception thrown when an unknown command is entered
 */
public class UnknownCommandException extends Exception {

    /**
     * Constructs a new InvalidDeleteCommandException with the specified message and cause.
     *
     * @param message The error message.
     */
    public UnknownCommandException(String message) {
        super(message);
    }
}
