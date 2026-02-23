package atom.parser;

import java.time.format.DateTimeParseException;

/**
 * Exception thrown when the system fails to parse a date-time string.
 */
public class DateTimeParserException extends Exception {
    /**
     * Constructs a new DateTimeParserException with a message, original cause, and remedy.
     *
     * @param message The error message.
     * @param cause   The original DateTimeParseException.
     */
    public DateTimeParserException(String message, DateTimeParseException cause) {
        super(message, cause);
    }
}
