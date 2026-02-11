package atom.command;

import atom.controller.CommandHandler;

/**
 * Represents a command to create and add a new ToDo task to the task list.
 */
public class ToDoCommand implements Command {
    private String description;

    /**
     * Initializes a new ToDoCommand with the specified task description.
     * @param description The text describing the task to be done.
     */
    public ToDoCommand(String description) {
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
    @Override
    public void acceptHandler(CommandHandler handler) {
        handler.handle(this);
    }
}
