package atom.command;

import atom.ui.CommandResponseHandler;

/**
 * Represents the response when an internal error occurs.
 */
public class SystemErrorCommandResponse implements CommandResponse {
    private Exception exception;

    /**
     * Constructs a SystemErrorCommandResponse instance.
     *
     * @param exception Exception thrown by the atom controller if internal error occurs.
     */
    public SystemErrorCommandResponse(Exception exception) {
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }

    /**
     * Accepts a command response handler to process the SystemErrorCommandResponse.
     *
     * @param handler The command response handler instance.
     */
    @Override
    public void acceptResponseHandler(CommandResponseHandler handler) {
        handler.handleResponse(this);
    }
}
