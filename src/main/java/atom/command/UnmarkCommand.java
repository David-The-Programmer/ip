package atom.command;

import atom.ui.CommandHandler;

public class UnmarkCommand extends Command {
    private int taskNumber;

    public UnmarkCommand(int taskNumber) {
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