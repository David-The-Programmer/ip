package atom.storage;

/**
 * Exception thrown when an error occurs while writing task data to storage.
 */
public class StorageWriteException extends Exception {

    /**
     * Initializes the exception with a detail message and a cause.
     * @param message The error message.
     * @param cause The underlying cause of the exception.
     */
    public StorageWriteException(String message, Throwable cause) {
        super(message, cause);
    }
}
