package atom.command;

import atom.controller.CommandHandler;

/**
 * Represents a command to terminate the application.
 * When executed, this command signals the system to perform cleanup and exit.
 */
public class ByeCommand implements Command {

    /**
     * Accepts a command handler to process the bye command
     * @param handler The command handler instance.
     */
    @Override
    public void acceptHandler(CommandHandler handler) {
        handler.handle(this);
    }
}
