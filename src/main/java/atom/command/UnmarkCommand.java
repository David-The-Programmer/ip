package atom.command;

import atom.controller.CommandHandler;

/**
 * Represents a command to mark a specific task as incomplete.
 */
public class UnmarkCommand implements Command {
    private int taskNumber;

    /**
     * Initializes a new UnmarkCommand with the target task index.
     * @param taskNumber The 1-based index of the task in the task list.
     */
    public UnmarkCommand(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    /**
     * Gets the task number to be unmarked.
     * @return The task index.
     */
    public int getTaskNumber() {
        return this.taskNumber;
    }

    /**
     * Accepts a command handler to execute the unmarking logic.
     * @param handler The command handler instance.
     */
    @Override
    public void acceptHandler(CommandHandler handler) {
        handler.handle(this);
    }
}
