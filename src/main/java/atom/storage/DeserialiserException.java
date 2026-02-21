package atom.storage;

/**
 * Exception thrown when the deserialisation process fails due to corrupted or malformed data.
 */
public class DeserialiserException extends Exception {
    /**
     * Initializes the exception with a message and cause.
     *
     * @param message The detailed error message.
     * @param cause   The underlying cause of the exception.
     */
    public DeserialiserException(String message, Throwable cause) {
        super(message, cause);
    }
}
