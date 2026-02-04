package atom.task;

/**
 * Exception thrown when attempting to mark a task that is already complete.
 */
public class TaskAlreadyMarkedCompleteException extends Exception {

    /**
     * Initializes the exception with a message and a cause.
     * @param message Error message.
     * @param cause Underlying cause.
     */
    public TaskAlreadyMarkedCompleteException(String message, Throwable cause) {
        super(message, cause);
    }
}
