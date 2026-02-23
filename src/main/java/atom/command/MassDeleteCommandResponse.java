package atom.command;

import java.util.List;

import atom.task.Task;
import atom.ui.CommandResponseHandler;

/**
 * Represents the response of a mass delete command execution.
 */
public class MassDeleteCommandResponse implements CommandResponse {
    private List<Task> deletedTasks;

    /**
     * Constructs a MassDeleteCommandResponse instance.
     *
     * @param deletedTasks Tasks that was deleted.
     */
    public MassDeleteCommandResponse(List<Task> deletedTasks) {
        this.deletedTasks = deletedTasks;
    }

    public List<Task> getDeletedTasks() {
        return deletedTasks;
    }

    /**
     * Accepts a command response handler to process the mass delete command response
     *
     * @param handler The command response handler instance.
     */
    @Override
    public void acceptResponseHandler(CommandResponseHandler handler) {
        handler.handleResponse(this);
    }
}
