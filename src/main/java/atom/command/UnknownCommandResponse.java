package atom.command;

import atom.ui.CommandResponseHandler;

/**
 * Represents the response of a unknown command execution.
 */
public class UnknownCommandResponse implements CommandResponse {
    private String userInput;

    /**
     * Constructs a UnknownCommandResponse instance.
     *
     * @param userInput Unknown command given by user.
     */
    public UnknownCommandResponse(String userInput) {
        this.userInput = userInput;
    }

    public String getUserInput() {
        return userInput;
    }

    /**
     * Accepts a command response handler to process the unknown command response
     *
     * @param handler The command response handler instance.
     */
    @Override
    public void acceptResponseHandler(CommandResponseHandler handler) {
        handler.handleResponse(this);
    }
}
