package atom.task;

public class TaskAlreadyMarkedCompleteException extends Exception {

    public TaskAlreadyMarkedCompleteException(String message, Throwable cause) {
        super(message, cause);
    }
}
