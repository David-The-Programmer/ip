package atom.command;

import atom.ui.CommandHandler;

public class ListCommand extends Command {

    public ListCommand() {
        super();
    }

    public void acceptHandler(CommandHandler handler) {
        handler.handle(this);
    }
}
