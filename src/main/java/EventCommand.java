import java.time.LocalDateTime;

public class EventCommand extends Command {
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    public EventCommand(String description, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        super();
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    public String getDescription() {
        return this.description;
    }

    public LocalDateTime getStartDateTime() {
        return this.startDateTime;
    }

    public LocalDateTime getEndDateTime() {
        return this.endDateTime;
    }

    public void acceptHandler(CommandHandler handler) {
        handler.handle(this);
    }

}
