package atom.command;

import java.time.LocalDateTime;

import atom.ui.CommandHandler;

/**
 * Represents a command to create and add a new Deadline task to the task list.
 * A Deadline task includes a description and a specific date-time by which
 * the task must be completed.
 */
public class DeadlineCommand extends Command {
    private String description;
    private LocalDateTime dateTime;

    /**
     * Initializes a new DeadlineCommand with the specified description and deadline.
     * @param description The text describing the task to be completed.
     * @param dateTime The date and time representing the deadline.
     */
    public DeadlineCommand(String description, LocalDateTime dateTime) {
        super();
        this.description = description;
        this.dateTime = dateTime;
    }

    /**
     * Retrieves the description of the deadline task.
     * @return The task description string.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Retrieves the due date and time of the deadline task.
     * @return The deadline LocalDateTime.
     */
    public LocalDateTime getDateTime() {
        return this.dateTime;
    }

    /**
     * Accepts a command handler to process the addition of the Deadline task.
     * @param handler The command handler instance.
     */
    public void acceptHandler(CommandHandler handler) {
        handler.handle(this);
    }
}
