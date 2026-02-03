package atom.command;

import java.time.LocalDateTime;

import atom.ui.CommandHandler;

public class DeadlineCommand extends Command {
    private String description;
    private LocalDateTime dateTime;

    public DeadlineCommand(String description, LocalDateTime dateTime) {
        super();
        this.description = description;
        this.dateTime = dateTime;
    }

    public String getDescription() {
        return this.description;
    }

    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    public void acceptHandler(CommandHandler handler) {
        handler.handle(this);
    }

}
