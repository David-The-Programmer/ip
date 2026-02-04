package atom.task;

/**
 * Exception thrown when attempting to unmark a task that is already incomplete.
 */
public class TaskAlreadyMarkedIncompleteException extends Exception {

    /**
     * Initializes the exception with a message and a cause.
     * @param message Error message.
     * @param cause Underlying cause.
     */
    public TaskAlreadyMarkedIncompleteException(String message, Throwable cause) {
        super(message, cause);
    }

}
