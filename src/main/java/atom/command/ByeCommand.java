package atom.command;

import atom.ui.CommandHandler;

public class ByeCommand extends Command {

    public ByeCommand() {
        super();
    }

    public void acceptHandler(CommandHandler handler) {
        handler.handle(this);
    }
}
