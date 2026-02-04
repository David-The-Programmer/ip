package atom.storage;

/**
 * Exception thrown when the deserialisation process fails due to corrupted or malformed data.
 */
public class DeserialiserException extends Exception {
    private String remedy;

    /**
     * Initializes the exception with a message, cause, and a suggested remedy.
     * @param message The detailed error message.
     * @param cause The underlying cause of the exception.
     * @param remedy A string suggesting how the user or system might fix the issue.
     */
    public DeserialiserException(String message, Throwable cause, String remedy) {
        super(message, cause);
        this.remedy = remedy;
    }

    /**
     * Retrieves the suggested remedy for this exception.
     * @return The remedy string.
     */
    public String getRemedy() {
        return this.remedy;
    }
}
