package atom.command;

import atom.task.Task;
import atom.ui.CommandResponseHandler;

/**
 * Represents the response of a unmark command execution.
 */
public class UnmarkCommandResponse implements CommandResponse {
    private Task task;

    public UnmarkCommandResponse(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return task;
    }

    /**
     * Accepts a command response handler to process the unmark command response
     * @param handler The command handler instance.
     */
    @Override
    public void acceptResponseHandler(CommandResponseHandler handler) {
        handler.handleResponse(this);
    }
}
