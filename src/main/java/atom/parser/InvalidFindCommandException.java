package atom.parser;

/**
 * Exception thrown when a 'find' command is malformed, such as when a search keyword is missing.
 */
public class InvalidFindCommandException extends Exception {

    /**
     * Constructs a new InvalidFindCommandException with a detail message and the underlying cause.
     * @param message The error message explaining why the find command is invalid.
     * @param cause The underlying cause of the exception.
     */
    public InvalidFindCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
