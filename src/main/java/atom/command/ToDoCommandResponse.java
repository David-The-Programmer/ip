package atom.command;

import atom.task.ToDo;
import atom.ui.CommandResponseHandler;

/**
 * Represents the response of a todo command execution.
 */
public class ToDoCommandResponse implements CommandResponse {
    private ToDo toDo;
    private int numRemainingTasks;

    public ToDoCommandResponse(ToDo toDo, int numRemainingTasks) {
        this.toDo = toDo;
        this.numRemainingTasks = numRemainingTasks;
    }

    public ToDo getToDo() {
        return toDo;
    }

    public int getNumRemainingTasks() {
        return this.numRemainingTasks;
    }

    /**
     * Accepts a command response handler to process the todo command
     * @param handler The command handler instance.
     */
    @Override
    public void acceptResponseHandler(CommandResponseHandler handler) {
        handler.handleResponse(this);
    }
}
