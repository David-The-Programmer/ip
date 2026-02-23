package atom.parser;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import atom.command.ByeCommand;
import atom.command.Command;
import atom.command.DeadlineCommand;
import atom.command.DeleteCommand;
import atom.command.EventCommand;
import atom.command.FindCommand;
import atom.command.ListCommand;
import atom.command.MarkCommand;
import atom.command.ToDoCommand;
import atom.command.UnmarkCommand;

/**
 * Parses user input into specific Command objects for execution.
 * This class handles the logic for validating input formats and extracting parameters
 * such as descriptions, dates, and task numbers.
 */
public class Parser {
    /**
     * Parses the raw input string from the user into a Command.
     *
     * @param rawCommand The full command string entered by the user.
     * @return A Command object representing the user's intended command.
     * @throws InvalidToDoCommandException     If a 'todo' command is malformed.
     * @throws InvalidDeleteCommandException   If a 'delete' command is missing a valid number.
     * @throws InvalidEventCommandException    If an 'event' command is malformed or dates are invalid.
     * @throws InvalidDeadlineCommandException If a 'deadline' command is malformed.
     * @throws InvalidMarkCommandException     If a 'mark' command is missing a valid number.
     * @throws InvalidUnmarkCommandException   If an 'unmark' command is missing a valid number.
     * @throws InvalidFindCommandException     If a 'find' command is missing a keyword.
     * @throws UnknownCommandException         If command string entered by user does not match any known commands.
     */
    public Command parse(String rawCommand)
        throws InvalidToDoCommandException, InvalidDeleteCommandException, InvalidEventCommandException,
        InvalidDeadlineCommandException, InvalidMarkCommandException, InvalidUnmarkCommandException,
        InvalidFindCommandException, UnknownCommandException {
        RawCommandStream stream = new RawCommandStream(rawCommand.trim());
        if (stream.isExhausted()) {
            throw new UnknownCommandException("rawCommand is empty");
        }
        String commandKeyword = stream.nextWord();
        if ("bye".equals(commandKeyword)) {
            return new ByeCommand();
        }
        if ("list".equals(commandKeyword)) {
            return new ListCommand();
        }
        if ("find".equals(commandKeyword)) {
            return parseFindCommand(stream);
        }
        if ("delete".equals(commandKeyword)) {
            return parseDeleteCommand(stream);
        }
        if ("mark".equals(commandKeyword)) {
            return parseMarkCommand(stream);
        }
        if ("unmark".equals(commandKeyword)) {
            return parseUnmarkCommand(stream);
        }
        if ("todo".equals(commandKeyword)) {
            return parseToDoCommand(stream);
        }
        if ("deadline".equals(commandKeyword)) {
            return parseDeadlineCommand(stream);
        }
        if ("event".equals(commandKeyword)) {
            return parseEventCommand(stream);
        }
        throw new UnknownCommandException("rawCommand is unknown");
    }

    /**
     * Parses RawCommandStream into a FindCommand.
     *
     * @param stream RawCommandStream object.
     * @return FindCommand object.
     * @throws InvalidFindCommandException If a 'find' command is missing a keyword.
     */
    private FindCommand parseFindCommand(RawCommandStream stream) throws InvalidFindCommandException {
        if (stream.isExhausted()) {
            throw new InvalidFindCommandException("'find' command is missing a description", null);
        }
        String description = stream.nextRemaining();
        return new FindCommand(description);
    }

    /**
     * Parses RawCommandStream into a DeleteCommand.
     *
     * @param stream RawCommandStream object.
     * @return DeleteCommand object.
     * @throws InvalidDeleteCommandException If a 'delete' command is missing a valid number.
     */
    private DeleteCommand parseDeleteCommand(RawCommandStream stream) throws InvalidDeleteCommandException {
        if (stream.isExhausted()) {
            throw new InvalidDeleteCommandException("'delete' command is missing a number", null);
        }
        String rawTaskNum = stream.nextRemaining();
        try {
            int taskNumber = Integer.parseInt(rawTaskNum);
            return new DeleteCommand(taskNumber);
        } catch (NumberFormatException e) {
            String message = String.format("'delete' command given invalid task number '%s'", rawTaskNum);
            throw new InvalidDeleteCommandException(message, e);
        }
    }

    /**
     * Parses RawCommandStream into a MarkCommand.
     *
     * @param stream RawCommandStream object.
     * @return MarkCommand object.
     * @throws InvalidMarkCommandException If a 'mark' command is missing a valid number.
     */
    private MarkCommand parseMarkCommand(RawCommandStream stream) throws InvalidMarkCommandException {
        if (stream.isExhausted()) {
            throw new InvalidMarkCommandException("'mark' command is missing a number", null);
        }
        String rawTaskNum = stream.nextRemaining();
        try {
            int taskNumber = Integer.parseInt(rawTaskNum);
            return new MarkCommand(taskNumber);
        } catch (NumberFormatException e) {
            String message = String.format("'mark' command given invalid task number '%s'", rawTaskNum);
            throw new InvalidMarkCommandException(message, e);
        }
    }

    /**
     * Parses RawCommandStream into a UnmarkCommand.
     *
     * @param stream RawCommandStream object.
     * @return UnmarkCommand object.
     * @throws InvalidUnmarkCommandException If an 'unmark' command is missing a valid number.
     */
    private UnmarkCommand parseUnmarkCommand(RawCommandStream stream) throws InvalidUnmarkCommandException {
        if (stream.isExhausted()) {
            throw new InvalidUnmarkCommandException("'unmark' command is missing a number", null);
        }
        String rawTaskNum = stream.nextRemaining();
        try {
            int taskNumber = Integer.parseInt(rawTaskNum);
            return new UnmarkCommand(taskNumber);
        } catch (NumberFormatException e) {
            String message = String.format("'unmark' command given invalid task number '%s'", rawTaskNum);
            throw new InvalidUnmarkCommandException(message, e);
        }
    }

    /**
     * Parses RawCommandStream into a ToDoCommand.
     *
     * @param stream RawCommandStream object.
     * @return ToDoCommand object.
     * @throws InvalidToDoCommandException If a 'todo' command is malformed.
     */
    private ToDoCommand parseToDoCommand(RawCommandStream stream) throws InvalidToDoCommandException {
        if (stream.isExhausted()) {
            throw new InvalidToDoCommandException("'todo' command is missing a description", null);
        }
        String description = stream.nextRemaining();
        if (description.isBlank()) {
            throw new InvalidToDoCommandException("'todo' command cannot have a blank description", null);
        }
        return new ToDoCommand(description.trim());
    }

    /**
     * Parses RawCommandStream into a DeadlineCommand.
     *
     * @param stream RawCommandStream object.
     * @return DeadlineCommand object.
     * @throws InvalidDeadlineCommandException If a 'deadline' command is malformed.
     */
    private DeadlineCommand parseDeadlineCommand(RawCommandStream stream) throws InvalidDeadlineCommandException {
        if (stream.isExhausted()) {
            throw new InvalidDeadlineCommandException("'deadline' command is missing a description", null);
        }
        String description = stream.nextUntil("/by");
        if (description.isBlank()) {
            throw new InvalidDeadlineCommandException("'deadline' command cannot have a blank description", null);
        }

        if (stream.isExhausted()) {
            throw new InvalidDeadlineCommandException("'deadline' command is missing /by <date> <time>", null);
        }
        String rawDeadlineDate = stream.nextWord().trim();
        if (rawDeadlineDate.isBlank()) {
            throw new InvalidDeadlineCommandException("'deadline' command is missing a date after /by", null);
        }
        LocalDate deadlineDate;
        try {
            deadlineDate = DateTimeParser.parseDate(rawDeadlineDate);
        } catch (DateTimeParserException e) {
            throw new InvalidDeadlineCommandException(e.getMessage(), e.getCause());
        }

        if (stream.isExhausted()) {
            throw new InvalidDeadlineCommandException("'deadline' command is missing a time after /by <date>", null);
        }
        String rawDeadlineTime = stream.nextRemaining().trim();
        if (rawDeadlineTime.isBlank()) {
            throw new InvalidDeadlineCommandException("'deadline' command is missing a time after /by <date>", null);
        }
        LocalTime deadlineTime;
        try {
            deadlineTime = DateTimeParser.parseTime(rawDeadlineTime);
        } catch (DateTimeParserException e) {
            throw new InvalidDeadlineCommandException(e.getMessage(), e.getCause());
        }

        LocalDateTime deadlineDateTime = LocalDateTime.of(deadlineDate, deadlineTime);
        return new DeadlineCommand(description.trim(), deadlineDateTime);
    }

    /**
     * Parses RawCommandStream into a EventCommand.
     *
     * @param stream RawCommandStream object.
     * @return EventCommand object.
     * @throws InvalidEventCommandException If an 'event' command is malformed.
     */
    private EventCommand parseEventCommand(RawCommandStream stream) throws InvalidEventCommandException {
        if (stream.isExhausted()) {
            throw new InvalidEventCommandException("'event' command is missing a description", null);
        }
        String description = stream.nextUntil("/from");
        if (description.isBlank()) {
            throw new InvalidEventCommandException("'event' command cannot have a blank description", null);
        }
        LocalDateTime startDateTime = parseEventCommandStartDateTime(stream);
        LocalDateTime endDateTime = parseEventCommandEndDateTime(stream);

        if (endDateTime.isBefore(startDateTime)) {
            String message = "/from <date> <time> cannot be later than /to <date> <time>";
            throw new InvalidEventCommandException(message, null);
        }
        return new EventCommand(description.trim(), startDateTime, endDateTime);
    }

    private LocalDateTime parseEventCommandStartDateTime(RawCommandStream stream) throws InvalidEventCommandException {
        if (stream.isExhausted()) {
            throw new InvalidEventCommandException("'event' command is missing /from <date> <time> /to <date> <time>",
                null);
        }
        String rawStartDate = stream.nextWord().trim();
        if (rawStartDate.isBlank()) {
            throw new InvalidEventCommandException("'event' command is missing a date after /from", null);
        }
        LocalDate startDate;
        try {
            startDate = DateTimeParser.parseDate(rawStartDate);
        } catch (DateTimeParserException e) {
            throw new InvalidEventCommandException(e.getMessage(), e.getCause());
        }

        if (stream.isExhausted()) {
            throw new InvalidEventCommandException("'event' command is missing a time after /from <date>", null);
        }
        String rawStartTime = stream.nextUntil("/to").trim();
        if (rawStartTime.isBlank()) {
            throw new InvalidEventCommandException("'event' command is missing a time after /from <date>", null);
        }
        LocalTime startTime;
        try {
            startTime = DateTimeParser.parseTime(rawStartTime);
        } catch (DateTimeParserException e) {
            throw new InvalidEventCommandException(e.getMessage(), e.getCause());
        }

        return LocalDateTime.of(startDate, startTime);
    }

    private LocalDateTime parseEventCommandEndDateTime(RawCommandStream stream) throws InvalidEventCommandException {
        if (stream.isExhausted()) {
            throw new InvalidEventCommandException("'event' command is missing /to <date> <time>", null);
        }
        String rawEndDate = stream.nextWord().trim();
        if (rawEndDate.isBlank()) {
            throw new InvalidEventCommandException("'event' command is missing a date after /to", null);
        }
        LocalDate endDate;
        try {
            endDate = DateTimeParser.parseDate(rawEndDate);
        } catch (DateTimeParserException e) {
            throw new InvalidEventCommandException(e.getMessage(), e.getCause());
        }

        if (stream.isExhausted()) {
            throw new InvalidEventCommandException("'event' command is missing a time after /to <date>", null);
        }
        String rawEndTime = stream.nextRemaining().trim();
        if (rawEndTime.isBlank()) {
            throw new InvalidEventCommandException("'event' command is missing a time after /to <date>", null);
        }
        LocalTime endTime;
        try {
            endTime = DateTimeParser.parseTime(rawEndTime);
        } catch (DateTimeParserException e) {
            throw new InvalidEventCommandException(e.getMessage(), e.getCause());
        }

        return LocalDateTime.of(endDate, endTime);
    }
}
