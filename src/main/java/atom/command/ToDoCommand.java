package atom.command;

import atom.ui.CommandHandler;

/**
 * Represents a command to create and add a new ToDo task to the task list.
 */
public class ToDoCommand extends Command {
    private String description;

    /**
     * Initializes a new ToDoCommand with the specified task description.
     * @param description The text describing the task to be done.
     */
    public ToDoCommand(String description) {
        super();
        this.description = description;
    }

    /**
     * Retrieves the description of the ToDo task.
     * @return The task description string.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Accepts a command handler to process the addition of the ToDo task.
     * @param handler The command handler instance.
     */
    public void acceptHandler(CommandHandler handler) {
        handler.handle(this);
    }
}
