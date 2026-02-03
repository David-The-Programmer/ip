public class ByeCommand extends Command {

    public ByeCommand() {
        super();
    }

    public void acceptHandler(CommandHandler handler) {
        handler.handle(this);
    }
}
