package atom.command;

import atom.ui.CommandHandler;

/**
 * Represents a command to display all tasks currently in the task list.
 */
public class ListCommand extends Command {

    /**
     * Initializes a new ListCommand.
     */
    public ListCommand() {
        super();
    }

    /**
     * Accepts a command handler to trigger the display of the task list.
     * @param handler The command handler instance.
     */
    public void acceptHandler(CommandHandler handler) {
        handler.handle(this);
    }
}
