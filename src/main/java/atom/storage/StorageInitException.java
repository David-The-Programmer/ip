package atom.storage;

/**
 * Exception thrown when the storage system fails to initialize.
 */
public class StorageInitException extends RuntimeException {

    /**
     * Initializes the exception with a detail message and a cause.
     * @param message The error message.
     * @param cause The underlying cause of the failure.
     */
    public StorageInitException(String message, Throwable cause) {
        super(message, cause);
    }
}
