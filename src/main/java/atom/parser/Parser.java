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

import java.time.LocalDateTime;

/**
 * Parses user input into specific Command objects for execution.
 * This class handles the logic for validating input formats and extracting parameters
 * such as descriptions, dates, and task numbers.
 */
public class Parser {

    /**
     * Parses the raw input string from the user into a Command.
     * @param rawCommand The full command string entered by the user.
     * @return A Command object representing the user's intent, or null if unrecognised.
     * @throws InvalidTodoCommandException If a 'todo' command is malformed.
     * @throws InvalidDeleteCommandException If a 'delete' command is missing a valid number.
     * @throws InvalidEventCommandException If an 'event' command is malformed or dates are invalid.
     * @throws InvalidDeadlineCommandException If a 'deadline' command is malformed.
     * @throws InvalidMarkCommandException If a 'mark' command is missing a valid number.
     * @throws InvalidUnmarkCommandException If an 'unmark' command is missing a valid number.
     */
    public Command parse(String rawCommand)
            throws InvalidTodoCommandException, InvalidDeleteCommandException, InvalidEventCommandException,
            InvalidDeadlineCommandException, InvalidMarkCommandException, InvalidUnmarkCommandException,
            InvalidFindCommandException {
        if (rawCommand.equals("bye")) {
            return new ByeCommand();
        }
        if (rawCommand.equals("list")) {
            return new ListCommand();
        }
        String[] subcommands = rawCommand.split(" ", 2);

        if (subcommands[0].equals("todo")) {
            if (subcommands.length < 2) {
                throw new InvalidTodoCommandException("'todo' command is missing a description", null);
            }
            subcommands[1] = subcommands[1].trim();
            if (subcommands[1].equals("")) {
                throw new InvalidTodoCommandException("'todo' command is missing a description", null);
            }
            return new ToDoCommand(subcommands[1]);
        }

        if (subcommands[0].equals("find")) {
            if (subcommands.length < 2) {
                throw new InvalidFindCommandException("'find' command is missing a keyword", null);
            }
            subcommands[1] = subcommands[1].trim();
            if (subcommands[1].equals("")) {
                throw new InvalidFindCommandException("'todo' command is missing a keyword", null);
            }
            return new FindCommand(subcommands[1]);
        }

        if (subcommands[0].equals("deadline")) {
            if (subcommands.length < 2) {
                throw new InvalidDeadlineCommandException(
                        "'deadline' command has an invalid format: missing description", null);
            }
            subcommands[1] = subcommands[1].trim();
            if (subcommands[1].equals("")) {
                throw new InvalidDeadlineCommandException(
                        "'deadline' command has an invalid format: missing description", null);
            }
            int idxOfBy = subcommands[1].indexOf("/by");
            if (idxOfBy == -1) {
                throw new InvalidDeadlineCommandException(
                        "'deadline' command has an invalid format: missing /by", null);
            }
            String description = subcommands[1].substring(0, idxOfBy).trim();
            if (description.equals("")) {
                throw new InvalidDeadlineCommandException(
                        "'deadline' command has an invalid format: missing description", null);
            }
            String byDateTimeStr = subcommands[1].substring(idxOfBy + 3).trim();
            if (byDateTimeStr.equals("")) {
                throw new InvalidEventCommandException(
                        "'deadline' command has an invalid format: missing /by datetime", null);
            }
            LocalDateTime deadlineDateTime = null;
            try {
                deadlineDateTime = DateTimeParser.parse(byDateTimeStr);

            } catch (DateTimeParserException e) {
                throw new InvalidDeadlineCommandException(e.getMessage(), e.getCause());
            }
            return new DeadlineCommand(description, deadlineDateTime);
        }

        if (subcommands[0].equals("event")) {
            if (subcommands.length < 2) {
                throw new InvalidEventCommandException("'event' command has an invalid format", null);
            }
            String description = subcommands[1].trim();
            if (description.equals("")) {
                throw new InvalidEventCommandException(
                        "'event' command has an invalid format: missing description", null);
            }
            int idxOfFrom = subcommands[1].indexOf("/from");
            if (idxOfFrom == -1) {
                throw new InvalidEventCommandException("'event' command has an invalid format: missing /from",
                        null);
            }
            int idxOfTo = subcommands[1].indexOf("/to");
            if (idxOfTo == -1) {
                throw new InvalidEventCommandException("'event' command has an invalid format: missing /to",
                        null);
            }
            description = subcommands[1].substring(0, idxOfFrom).trim();
            if (description.equals("")) {
                throw new InvalidEventCommandException(
                        "'event' command has an invalid format: missing description", null);
            }
            String fromDateTimeStr = subcommands[1].substring(idxOfFrom + 5, idxOfTo).trim();
            if (fromDateTimeStr.equals("")) {
                throw new InvalidEventCommandException(
                        "'event' command has an invalid format: missing /from datetime", null);
            }
            String toDateTimeStr = subcommands[1].substring(idxOfTo + 3).trim();
            if (toDateTimeStr.equals("")) {
                throw new InvalidEventCommandException(
                        "'event' command has an invalid format: missing /to datetime", null);
            }
            LocalDateTime fromDateTime = null;
            LocalDateTime toDateTime = null;
            try {
                fromDateTime = DateTimeParser.parse(fromDateTimeStr);
                toDateTime = DateTimeParser.parse(toDateTimeStr);
            } catch (DateTimeParserException e) {
                throw new InvalidEventCommandException(e.getMessage(), e.getCause());
            }
            if (toDateTime.isBefore(fromDateTime)) {
                String message = "/from datetime cannot be later than /to datetime";
                throw new InvalidEventCommandException(message, null);
            }
            return new EventCommand(description, fromDateTime, toDateTime);
        }

        if (subcommands[0].equals("delete")) {
            if (subcommands.length < 2) {
                throw new InvalidDeleteCommandException("'delete' command is missing a number", null);
            }
            int taskNum = -1;
            try {
                taskNum = Integer.parseInt(subcommands[1]);
            } catch (NumberFormatException e) {
                throw new InvalidDeleteCommandException("'" + subcommands[1] + "'" + " is not a number", e);
            }
            return new DeleteCommand(taskNum);
        }

        if (subcommands[0].equals("mark")) {
            if (subcommands.length < 2) {
                throw new InvalidMarkCommandException("'mark' command is missing a number", null);
            }
            int taskNum = -1;
            try {
                taskNum = Integer.parseInt(subcommands[1]);
            } catch (NumberFormatException e) {
                throw new InvalidMarkCommandException("'" + subcommands[1] + "'" + " is not a number", e);
            }
            return new MarkCommand(taskNum);
        }

        if (subcommands[0].equals("unmark")) {
            if (subcommands.length < 2) {
                throw new InvalidUnmarkCommandException("'unmark' command is missing a number", null);
            }
            int taskNum = -1;
            try {
                taskNum = Integer.parseInt(subcommands[1]);
            } catch (NumberFormatException e) {
                throw new InvalidUnmarkCommandException("'" + subcommands[1] + "'" + " is not a number", e);
            }
            return new UnmarkCommand(taskNum);
        }
        return null;
    }
}
