package atom.command;

import java.util.List;

import atom.task.Task;
import atom.ui.CommandResponseHandler;

/**
 * Represents the response of a find command execution.
 */
public class FindCommandResponse implements CommandResponse {
    private List<Task> tasks;
    private String keyword;

    /**
     * Constructs a FindCommandResponse instance.
     *
     * @param tasks   List of task objects that are the results of the find command.
     * @param keyword Keyword given by user.
     */
    public FindCommandResponse(List<Task> tasks, String keyword) {
        this.tasks = tasks;
        this.keyword = keyword;
    }

    public List<Task> getTasks() {
        return this.tasks;
    }

    public String getKeyword() {
        return this.keyword;
    }

    /**
     * Accepts a command response handler to process the find command response
     *
     * @param handler The command response handler instance.
     */
    @Override
    public void acceptResponseHandler(CommandResponseHandler handler) {
        handler.handleResponse(this);
    }
}
