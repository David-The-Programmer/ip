package atom.parser;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.format.ResolverStyle;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Utility class for parsing date-time strings into {@code LocalDateTime} objects.
 * Supports multiple date formats and a strict 24-hour time format (HHmm).
 */
public class DateTimeParser {

    private static final Map<String, String> DATE_PATTERNS = createDatePatternMap();
    private static final String TIME_REGEX = "^\\d{4}$";

    /**
     * Creates and returns the map of supported date patterns and their regex equivalents.
     *
     * @return An unmodifiable map of date format patterns to regex strings.
     */
    private static Map<String, String> createDatePatternMap() {
        Map<String, String> dateFormats = new LinkedHashMap<>();
        dateFormats.put("yyyy-MM-dd", "^\\d{4}-\\d{2}-\\d{2}$");
        dateFormats.put("yyyy/MM/dd", "^\\d{4}/\\d{2}/\\d{2}$");
        dateFormats.put("dd/MM/yyyy", "^\\d{2}/\\d{2}/\\d{4}$");
        dateFormats.put("dd-MM-yyyy", "^\\d{2}-\\d{2}-\\d{4}$");
        return Collections.unmodifiableMap(dateFormats);
    }

    /**
     * Parses a raw input string into a {@code LocalDate} object.
     *
     * @param rawDate The raw input string containing a date
     * @return {@code LocalDate} representation of the input.
     * @throws DateTimeParserException If the input is empty, malformed, or contains an invalid calendar date.
     */
    public static LocalDate parseDate(String rawDate) throws DateTimeParserException {
        if (rawDate.isBlank()) {
            throw new DateTimeParserException("date is empty.", null);
        }

        String matchingFormat = "";
        for (Map.Entry<String, String> entry : DATE_PATTERNS.entrySet()) {
            if (rawDate.matches(entry.getValue())) {
                matchingFormat = entry.getKey();
                break;
            }
        }
        if (matchingFormat.isEmpty()) {
            String message = "invalid date format, only the following formats are allowed: \n";
            for (String format : DATE_PATTERNS.keySet()) {
                message += String.format("  - %s\n", format);
            }
            throw new DateTimeParserException(message, null);
        }

        String matchingDatePattern = matchingFormat.replace('y', 'u');
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern(matchingDatePattern)
            .withResolverStyle(ResolverStyle.STRICT);
        try {
            return LocalDate.parse(rawDate, dateFormat);
        } catch (DateTimeParseException e) {
            String message = e.getMessage();
            if (message.contains(": ")) {
                message = message.split(": ")[1];
            }
            message = message.replace("MonthOfYear", "month").replace("DayOfMonth", "day");
            throw new DateTimeParserException(message, e);
        }
    }

    /**
     * Parses a raw input string into a {@code LocalTime} object.
     *
     * @param rawTime The raw input string containing a time
     * @return {@code LocalTime} representation of the input.
     * @throws DateTimeParserException If the input is empty, malformed, or contains an invalid time
     */
    public static LocalTime parseTime(String rawTime) throws DateTimeParserException {
        if (!rawTime.matches(TIME_REGEX)) {
            String message = String.format("Invalid Time Syntax: '%s'. Expected HHmm (e.g., 1430).", rawTime);
            throw new DateTimeParserException(message, null);
        }
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HHmm")
            .withResolverStyle(ResolverStyle.STRICT);
        try {
            return LocalTime.parse(rawTime, timeFormat);
        } catch (DateTimeParseException e) {
            String message = e.getMessage();
            if (message.contains(": ")) {
                message = message.split(": ")[1];
            }
            message = message.replace("HourOfDay", "hour").replace("MinuteOfHour", "minute");
            throw new DateTimeParserException(message, e);
        }
    }
}
