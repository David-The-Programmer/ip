package atom.command;

import atom.ui.CommandResponseHandler;
import atom.task.Task;

/**
 * Represents the response of a delete command execution.
 */
public class DeleteCommandResponse implements CommandResponse {
    private Task task;

    public DeleteCommandResponse(Task task) {
        this.task = task;
    }

    public Task getTask() {
        return task;
    }

    /**
     * Accepts a command response handler to process the delete command response
     * @param handler The command response handler instance.
     */
    @Override
    public void acceptResponseHandler(CommandResponseHandler handler) {
        handler.handleResponse(this);
    }
}
