package atom.command;

import atom.ui.CommandResponseHandler;

/**
 * Represents the response when specifically a mass delete command error occurs.
 */
public class MassDeleteSystemErrorCommandResponse implements CommandResponse {
    private Exception exception;

    /**
     * Constructs a MassDeleteSystemErrorCommandResponse instance.
     *
     * @param exception Exception thrown by the atom controller when executing mass delete command.
     */
    public MassDeleteSystemErrorCommandResponse(Exception exception) {
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }

    /**
     * Accepts a command response handler to process the MassDeleteSystemErrorCommandResponse.
     *
     * @param handler The command response handler instance.
     */
    @Override
    public void acceptResponseHandler(CommandResponseHandler handler) {
        handler.handleResponse(this);
    }
}
