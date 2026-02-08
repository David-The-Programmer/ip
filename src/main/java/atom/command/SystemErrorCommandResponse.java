package atom.command;

import atom.ui.CommandResponseHandler;

public class SystemErrorCommandResponse implements CommandResponse {
    private Exception exception;

    public SystemErrorCommandResponse(Exception exception) {
        this.exception = exception;
    }

    public Exception getException() {
        return exception;
    }

    @Override
    public void acceptResponseHandler(CommandResponseHandler handler) {
        handler.handleResponse(this);
    }
}
