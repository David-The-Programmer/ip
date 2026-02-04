package atom.storage;

/**
 * Exception thrown when an error occurs while reading task data from storage.
 */
public class StorageReadException extends RuntimeException {

    /**
     * Initializes the exception with a detail message and a cause.
     * @param message The error message.
     * @param cause The underlying cause of the exception.
     */
    public StorageReadException(String message, Throwable cause) {
        super(message, cause);
    }
}
