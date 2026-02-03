package atom.parser;

public class InvalidTodoCommandException extends Exception {

    public InvalidTodoCommandException(String message, Throwable cause) {
        super(message, cause);
    }
}
