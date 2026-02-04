package atom.storage;

/**
 * Exception thrown when the application lacks the necessary permissions to access the storage file.
 */
public class StorageAccessDeniedException extends Exception {
    private String saveFilePath;

    /**
     * Initializes the exception with a message, cause, and the path to the restricted file.
     * @param message The error message.
     * @param cause The underlying cause of the exception.
     * @param saveFilePath The file path where access was denied.
     */
    public StorageAccessDeniedException(String message, Throwable cause, String saveFilePath) {
        super(message, cause);
        this.saveFilePath = saveFilePath;
    }

    /**
     * Retrieves the file path that could not be accessed.
     * @return The save file path string.
     */
    public String getSaveFilePath() {
        return this.saveFilePath;
    }
}
