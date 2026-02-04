package atom.task;

/**
 * Exception thrown when a requested task cannot be found in the service.
 */
public class TaskNotFoundException extends Exception {
    private int taskNumber;

    /**
     * Initializes the exception with a message, task number, and cause.
     * @param message Error message.
     * @param taskNumber The invalid task number provided.
     * @param cause The underlying cause of the exception.
     */
    public TaskNotFoundException(String message, int taskNumber, Throwable cause) {
        super(message, cause);
        this.taskNumber = taskNumber;
    }

    /**
     * Retrieves the task number that caused the exception.
     * @return The invalid task number.
     */
    public int getTaskNumber() {
        return taskNumber;
    }
}
