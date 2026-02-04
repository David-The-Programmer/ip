package atom.command;

import java.time.LocalDateTime;

import atom.ui.CommandHandler;

/**
 * Represents a command to create and add a new Event task to the task list.
 * An Event task includes a description and a specific time range defined by
 * a start and end date-time.
 */
public class EventCommand extends Command {
    private String description;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;

    /**
     * Initializes a new EventCommand with the specified description and duration.
     * @param description The text describing the event.
     * @param startDateTime The date and time when the event begins.
     * @param endDateTime The date and time when the event ends.
     */
    public EventCommand(String description, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        super();
        this.description = description;
        this.startDateTime = startDateTime;
        this.endDateTime = endDateTime;
    }

    /**
     * Retrieves the description of the event.
     * @return The event description.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Retrieves the start date and time of the event.
     * @return The starting LocalDateTime.
     */
    public LocalDateTime getStartDateTime() {
        return this.startDateTime;
    }

    /**
     * Retrieves the end date and time of the event.
     * @return The ending LocalDateTime.
     */
    public LocalDateTime getEndDateTime() {
        return this.endDateTime;
    }

    /**
     * Accepts a command handler to process the addition of the Event task.
     * @param handler The command handler instance.
     */
    public void acceptHandler(CommandHandler handler) {
        handler.handle(this);
    }
}
