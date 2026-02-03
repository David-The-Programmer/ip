package atom.command;

import atom.ui.CommandHandler;

public class ToDoCommand extends Command {
    private String description;

    public ToDoCommand(String description) {
        super();
        this.description = description;
    }

    public String getDescription() {
        return this.description;
    }

    public void acceptHandler(CommandHandler handler) {
        handler.handle(this);
    }
}
