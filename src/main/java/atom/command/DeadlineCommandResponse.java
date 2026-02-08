package atom.command;

import atom.ui.CommandResponseHandler;
import atom.task.Deadline;

/**
 * Represents the response of a deadline command execution.
 */
public class DeadlineCommandResponse implements CommandResponse {
    private Deadline deadline;
    private int numTasksRemaining;

    public DeadlineCommandResponse(Deadline deadline, int numTaskRemaining) {
        this.deadline = deadline;
        this.numTasksRemaining = numTaskRemaining;
    }

    public Deadline getDeadline() {
        return deadline;
    }

    public int getNumTasksRemaining() {
        return numTasksRemaining;
    }

    /**
     * Accepts a command response handler to process the deadline command response
     * @param handler The command response handler instance.
     */
    @Override
    public void acceptResponseHandler(CommandResponseHandler handler) {
        handler.handleResponse(this);
    }
}
