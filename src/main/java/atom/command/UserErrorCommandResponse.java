package atom.command;

import atom.ui.CommandResponseHandler;

/**
 * Represents the response when user gives invalid commands.
 */
public class UserErrorCommandResponse implements CommandResponse {
    private Exception exception;

    /**
     * Constructs a UserErrorCommandResponse instance.
     *
     * @param exception Exception thrown by the atom controller in response to invalid command given by user.
     */
    public UserErrorCommandResponse(Exception exception) {
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }

    /**
     * Accepts a command response handler to process the UserErrorCommandResponse.
     *
     * @param handler The command response handler instance.
     */
    @Override
    public void acceptResponseHandler(CommandResponseHandler handler) {
        handler.handleResponse(this);
    }
}
