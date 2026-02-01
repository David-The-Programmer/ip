public class TaskAlreadyMarkedIncompleteException extends Exception {
    private String remedy;

    public TaskAlreadyMarkedIncompleteException(String message, Throwable cause, String remedy) {
        super(message, cause);
        this.remedy = remedy;
    }

    public String getRemedy() {
        return this.remedy;
    }
}
