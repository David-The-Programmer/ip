package atom.command;

import java.util.List;

import atom.task.Task;
import atom.ui.CommandResponseHandler;

/**
 * Represents the response of a list command execution.
 */
public class ListCommandResponse implements CommandResponse {
    private List<Task> tasks;

    public ListCommandResponse(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return this.tasks;
    }

    /**
     * Accepts a command response handler to process the list command response
     * @param handler The command handler instance.
     */
    @Override
    public void acceptResponseHandler(CommandResponseHandler handler) {
        handler.handleResponse(this);
    }
}
