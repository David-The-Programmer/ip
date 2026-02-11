package atom.command;

import atom.controller.CommandHandler;

/**
 * Represents a command to display all tasks currently in the task list.
 */
public class ListCommand implements Command {

    /**
     * Accepts a command handler to trigger the display of the task list.
     * @param handler The command handler instance.
     */
    @Override
    public void acceptHandler(CommandHandler handler) {
        handler.handle(this);
    }
}
