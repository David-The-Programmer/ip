package atom.command;

import atom.ui.CommandHandler;

/**
 * Represents a command to terminate the application.
 * When executed, this command signals the system to perform cleanup and exit.
 */
public class ByeCommand extends Command {

    /**
     * Initializes a new ByeCommand.
     */
    public ByeCommand() {
        super();
    }

    /**
     * Accepts a command handler to process the bye command
     * @param handler The command handler instance.
     */
    public void acceptHandler(CommandHandler handler) {
        handler.handle(this);
    }
}
