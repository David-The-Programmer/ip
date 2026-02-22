package atom.parser;

import java.time.LocalDateTime;
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
            throw new InvalidDeadlineCommandException("'deadline' command is missing /by <datetime>", null);
        }
        String rawDeadlineDateTime = stream.nextRemaining().trim();

        LocalDateTime deadlineDateTime;
        try {
            deadlineDateTime = DateTimeParser.parse(rawDeadlineDateTime);
            return new DeadlineCommand(description.trim(), deadlineDateTime);
        } catch (DateTimeParserException e) {
            throw new InvalidDeadlineCommandException(e.getMessage(), e.getCause());
        }
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

        if (stream.isExhausted()) {
            throw new InvalidEventCommandException("'event' command is missing /from <datetime>", null);
        }
        String rawStartDateTime = stream.nextUntil("/to");
        LocalDateTime startDateTime;
        try {
            startDateTime = DateTimeParser.parse(rawStartDateTime);
        } catch (DateTimeParserException e) {
            throw new InvalidEventCommandException(e.getMessage(), e.getCause());
        }

        if (stream.isExhausted()) {
            throw new InvalidEventCommandException("'event' command is missing /to <datetime>", null);
        }
        String rawEndDateTime = stream.nextRemaining();
        LocalDateTime endDateTime;
        try {
            endDateTime = DateTimeParser.parse(rawEndDateTime);
        } catch (DateTimeParserException e) {
            throw new InvalidEventCommandException(e.getMessage(), e.getCause());
        }

        if (endDateTime.isBefore(startDateTime)) {
            String message = "/from <datetime> cannot be later than /to <datetime>";
            throw new InvalidEventCommandException(message, null);
        }
        return new EventCommand(description.trim(), startDateTime, endDateTime);
    }
}
