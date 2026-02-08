package atom.controller;

import atom.command.ByeCommand;
import atom.command.DeadlineCommand;
import atom.command.DeleteCommand;
import atom.command.EventCommand;
import atom.command.FindCommand;
import atom.command.ListCommand;
import atom.command.MarkCommand;
import atom.command.ToDoCommand;
import atom.command.UnmarkCommand;

/**
 * Interface for handling various types of application commands.
 */
public interface CommandHandler {
    /**
     * Handles the execution of a todo command.
     * 
     * @param command The todo command.
     */
    public void handle(ToDoCommand command);

    /**
     * Handles the execution of a deadline command.
     * 
     * @param command The deadline command.
     */
    public void handle(DeadlineCommand command);

    /**
     * Handles the execution of an event command.
     * 
     * @param command The event command.
     */
    public void handle(EventCommand command);

    /**
     * Handles the execution of a list command.
     * 
     * @param command The list command.
     */
    public void handle(ListCommand command);

    /**
     * Handles the execution of a bye command.
     * 
     * @param command The bye command.
     */
    public void handle(ByeCommand command);

    /**
     * Handles the execution of a delete command.
     * 
     * @param command The delete command.
     */
    public void handle(DeleteCommand command);

    /**
     * Handles the execution of a mark command.
     * 
     * @param command The mark command.
     */
    public void handle(MarkCommand command);

    /**
     * Handles the execution of an unmark command.
     * 
     * @param command The unmark command.
     */
    public void handle(UnmarkCommand command);

    /**
     * Handles the execution of an find command.
     * 
     * @param command The find command.
     */
    public void handle(FindCommand command);
}
