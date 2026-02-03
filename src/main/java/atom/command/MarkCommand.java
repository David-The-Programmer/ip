package atom.command;

import atom.ui.CommandHandler;

public class MarkCommand extends Command {
    private int taskNumber;

    public MarkCommand(int taskNumber) {
        super();
        this.taskNumber = taskNumber;
    }

    public int getTaskNumber() {
        return this.taskNumber;
    }

    public void acceptHandler(CommandHandler handler) {
        handler.handle(this);
    }
}