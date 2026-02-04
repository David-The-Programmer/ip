package atom.command;

import atom.ui.CommandHandler;

/**
 * Abstract base class for all executable commands in the application.
 * This class serves as the 'Element' in the Visitor design pattern,
 * requiring all concrete commands to implement double-dispatch logic
 * for a {@code CommandHandler}.
 */
public abstract class Command {

    /**
     * Accepts a command handler to perform the execution logic specific to this command.
     * @param handler The handler that will process the execution of the command
     */
    public abstract void acceptHandler(CommandHandler handler);
}
