package atom.command;

import java.util.List;

import atom.controller.CommandHandler;

/**
 * Represents a command to remove specified tasks from the task list.
 */
public class MassDeleteCommand implements Command {
    private List<Integer> taskNumbers;

    /**
     * Constructs a new MassDeleteCommand.
     *
     * @param taskNumbers List of 1-based indexes of the tasks to be deleted.
     */
    public MassDeleteCommand(List<Integer> taskNumbers) {
        this.taskNumbers = taskNumbers;
    }

    /**
     * Retrieves the list of task numbers of the tasks to be deleted.
     *
     * @return List of task numbers.
     */
    public List<Integer> getTaskNumbers() {
        return this.taskNumbers;
    }

    /**
     * Accepts a command handler to execute the deletion logic.
     *
     * @param handler The command handler responsible for processing this command.
     */
    @Override
    public void acceptHandler(CommandHandler handler) {
        handler.handle(this);
    }
}
