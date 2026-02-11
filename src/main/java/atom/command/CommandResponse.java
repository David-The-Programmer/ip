package atom.command;

import atom.ui.CommandResponseHandler;

/**
 * Interface for all command responses after execution of commands.
 */
public interface CommandResponse {

    /**
     * Accepts a command response handler to handle the response after executing a command
     * @param handler The handler that will process the response of the command executed
     */
    public void acceptResponseHandler(CommandResponseHandler handler);
}
