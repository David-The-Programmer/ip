public class TaskNotFoundException extends Exception {
    private int taskNumber;

    public TaskNotFoundException(String message, int taskNumber, Throwable cause) {
        super(message, cause);
        this.taskNumber = taskNumber;
    }

    public int getTaskNumber() {
        return taskNumber;
    }
}
