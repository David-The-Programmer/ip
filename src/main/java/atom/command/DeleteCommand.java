package atom.command;

import atom.ui.CommandHandler;

public class DeleteCommand extends Command {
    private int taskNumber;

    public DeleteCommand(int taskNumber) {
        super();
        this.taskNumber = taskNumber;
    }

    public int getTaskNumber() {
        return this.taskNumber;
    }

    @Override
    public void acceptHandler(CommandHandler handler) {
        handler.handle(this);
    }

}
