package atom.parser;

import java.time.LocalDateTime;
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
public final class DateTimeParser {

    private static final Map<String, String> DATE_PATTERNS = createPatternMap();
    private static final String TIME_REGEX = "^\\d{4}$";

    /**
     * Creates and populates the map of supported date patterns and their regex equivalents.
     * @return An unmodifiable map of date format patterns to regex strings.
     */
    private static Map<String, String> createPatternMap() {
        Map<String, String> patterns = new LinkedHashMap<>();
        patterns.put("yyyy-MM-dd", "^\\d{4}-\\d{2}-\\d{2}$");
        patterns.put("yyyy/MM/dd", "^\\d{4}/\\d{2}/\\d{2}$");
        patterns.put("dd/MM/yyyy", "^\\d{2}/\\d{2}/\\d{4}$");
        patterns.put("dd-MM-yyyy", "^\\d{2}-\\d{2}-\\d{4}$");
        return Collections.unmodifiableMap(patterns);
    }

    /**
     * Parses a raw input string into a {@code LocalDateTime} object.
     * Validates both the date format and the time format strictly.
     * @param input The raw input string containing date and time.
     * @return A {@code LocalDateTime} representation of the input.
     * @throws DateTimeParserException If the input is empty, malformed, or contains an invalid calendar date.
     */
    public static LocalDateTime parse(String input) throws DateTimeParserException {
        if (input == null || input.isBlank()) {
            throw new DateTimeParserException("Input is empty.", null, "Please provide a valid date.");
        }

        String[] parts = input.trim().split(" ", 2);

        if (parts.length < 2) {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid Datetime Syntax: '").append(parts[0]).append("'.\n\n");
            sb.append("Supported datetime formats:\n");
            for (String pattern : DATE_PATTERNS.keySet()) {
                sb.append("  - ").append(pattern).append(" HHmm\n");
            }
            throw new DateTimeParserException(sb.toString(), null,
                    "Please retry using one of the formats listed above.");
        }

        String datePart = parts[0];
        String timePart = parts[1].trim();

        String matchingFormat = null;
        for (Map.Entry<String, String> entry : DATE_PATTERNS.entrySet()) {
            if (datePart.matches(entry.getValue())) {
                matchingFormat = entry.getKey();
                break;
            }
        }

        if (matchingFormat == null) {
            StringBuilder sb = new StringBuilder();
            sb.append("Invalid Datetime Syntax: '").append(datePart).append("'.\n\n");
            sb.append("Supported datetime formats:\n");
            for (String pattern : DATE_PATTERNS.keySet()) {
                sb.append("  - ").append(pattern).append(" HHmm\n");
            }
            throw new DateTimeParserException(sb.toString(), null,
                    "Please retry using one of the formats listed above.");
        }

        if (!timePart.matches(TIME_REGEX)) {
            throw new DateTimeParserException(
                    "Invalid Time Syntax: '" + timePart + "'. Expected HHmm (e.g., 1430).", null,
                    "Please correct the time and try again based on the HHmm requirement.");
        }

        String fullPattern = matchingFormat.replace('y', 'u') + " HHmm";
        DateTimeFormatter strictFormatter = DateTimeFormatter.ofPattern(fullPattern)
                .withResolverStyle(ResolverStyle.STRICT);

        try {
            return LocalDateTime.parse(datePart + " " + timePart, strictFormatter);
        } catch (DateTimeParseException e) {
            String reason = e.getMessage();

            if (reason.contains(": ")) {
                reason = reason.split(": ")[1];
            }
            reason = reason.replace("MonthOfYear", "month").replace("DayOfMonth", "day")
                    .replace("HourOfDay", "hour").replace("MinuteOfHour", "minute");

            throw new DateTimeParserException(reason, e, "Please try again with a valid calendar date.");
        }
    }
}