package atom.parser;

import java.time.format.DateTimeParseException;

/**
 * Exception thrown when the system fails to parse a date-time string.
 * Provides a specific remedy to help the user correct the input format.
 */
public class DateTimeParserException extends Exception {
    private final String remedy;

    /**
     * Constructs a new DateTimeParserException with a message, original cause, and remedy.
     * @param message The error message.
     * @param cause The original DateTimeParseException.
     * @param remedy A string suggesting how to fix the input format.
     */
    public DateTimeParserException(String message, DateTimeParseException cause, String remedy) {
        super(message, cause);
        this.remedy = remedy;
    }

    /**
     * Retrieves the suggested fix for the date-time format.
     * @return The remedy string.
     */
    public String getRemedy() {
        return remedy;
    }
}