public class TaskAlreadyMarkedCompleteException extends Exception {
    private String remedy;

    public TaskAlreadyMarkedCompleteException(String message, Throwable cause, String remedy) {
        super(message, cause);
        this.remedy = remedy;
    }

    public String getRemedy() {
        return this.remedy;
    }
}
