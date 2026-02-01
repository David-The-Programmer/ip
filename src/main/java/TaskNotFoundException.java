public class TaskNotFoundException extends Exception {
    private String remedy;
    private int taskNumber;

    public TaskNotFoundException(String message, int taskNumber, Throwable cause, String remedy) {
        super(message, cause);
        this.remedy = remedy;
        this.taskNumber = taskNumber;
    }

    public String getRemedy() {
        return remedy;
    }

    public int getTaskNumber() {
        return taskNumber;
    }
}
