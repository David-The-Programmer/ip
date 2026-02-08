package atom.command;

import atom.ui.CommandResponseHandler;

/**
 * Represents the response of a bye command execution.
 */
public class ByeCommandResponse implements CommandResponse {

    /**
     * Accepts a command response handler to process the bye command response
     * @param handler The command response handler instance.
     */
    @Override
    public void acceptResponseHandler(CommandResponseHandler handler) {
        handler.handleResponse(this);
    }
}
