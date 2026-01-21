public class InvalidTodoCommandException extends AtomException {

    public InvalidTodoCommandException(String message, Throwable cause, String remedy) {
        super(message, cause, remedy);
    }
}
