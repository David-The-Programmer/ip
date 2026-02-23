package atom.command;

import atom.task.Deadline;
import atom.ui.CommandResponseHandler;

/**
 * Represents the response of a deadline command execution.
 */
public class DeadlineCommandResponse implements CommandResponse {
    private Deadline deadline;
    private int numTasksRemaining;

    /**
     * Constructs a DeadlineCommandResponse instance.
     *
     * @param deadline          Deadline task object.
     * @param numTasksRemaining Number of tasks the user has remaining.
     */
    public DeadlineCommandResponse(Deadline deadline, int numTasksRemaining) {
        this.deadline = deadline;
        this.numTasksRemaining = numTasksRemaining;
    }

    public Deadline getDeadline() {
        return deadline;
    }

    public int getNumTasksRemaining() {
        return numTasksRemaining;
    }

    /**
     * Accepts a command response handler to process the deadline command response
     *
     * @param handler The command response handler instance.
     */
    @Override
    public void acceptResponseHandler(CommandResponseHandler handler) {
        handler.handleResponse(this);
    }
}
