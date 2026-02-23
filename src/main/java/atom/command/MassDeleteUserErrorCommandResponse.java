package atom.command;

import atom.ui.CommandResponseHandler;

/**
 * Represents the response when specifically a mass delete command error occurs due to user fault.
 */
public class MassDeleteUserErrorCommandResponse implements CommandResponse {
    private Exception exception;

    /**
     * Constructs a MassDeleteUserErrorCommandResponse instance.
     *
     * @param exception Exception thrown by the atom controller.
     */
    public MassDeleteUserErrorCommandResponse(Exception exception) {
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }

    /**
     * Accepts a command response handler to process the MassDeleteUserErrorCommandResponse.
     *
     * @param handler The command response handler instance.
     */
    @Override
    public void acceptResponseHandler(CommandResponseHandler handler) {
        handler.handleResponse(this);
    }
}
