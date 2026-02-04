package atom.command;

import atom.ui.CommandHandler;

/**
 * Represents a command to mark a specific task as completed in the task list.
 */
public class MarkCommand extends Command {
    private int taskNumber;

    /**
     * Initializes a new MarkCommand with the target task index.
     * @param taskNumber The 1-based index of the task to be marked.
     */
    public MarkCommand(int taskNumber) {
        super();
        this.taskNumber = taskNumber;
    }

    /**
     * Retrieves the index of the task to be marked as complete.
     * @return The task number.
     */
    public int getTaskNumber() {
        return this.taskNumber;
    }

    /**
     * Accepts a command handler to execute the marking logic.
     * @param handler The command handler responsible for processing this command.
     */
    public void acceptHandler(CommandHandler handler) {
        handler.handle(this);
    }
}