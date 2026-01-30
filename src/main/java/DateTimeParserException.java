import java.time.format.DateTimeParseException;

public class DateTimeParserException extends Exception {
    private final String remedy;

    public DateTimeParserException(String message, DateTimeParseException cause, String remedy) {
        super(message, cause);
        this.remedy = remedy;
    }

    public String getRemedy() {
        return remedy;
    }
}