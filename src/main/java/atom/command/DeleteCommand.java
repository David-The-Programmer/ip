package atom.command;

import atom.ui.CommandHandler;

/**
 * Represents a command to remove a specific task from the task list.
 */
public class DeleteCommand extends Command {
    private int taskNumber;

    /**
     * Initializes a new DeleteCommand with the target task index.
     * @param taskNumber The 1-based index of the task to be deleted.
     */
    public DeleteCommand(int taskNumber) {
        super();
        this.taskNumber = taskNumber;
    }

    /**
     * Retrieves the index of the task to be deleted.
     * @return The task number.
     */
    public int getTaskNumber() {
        return this.taskNumber;
    }

    /**
     * Accepts a command handler to execute the deletion logic.
     * @param handler The command handler responsible for processing this command.
     */
    @Override
    public void acceptHandler(CommandHandler handler) {
        handler.handle(this);
    }
}
