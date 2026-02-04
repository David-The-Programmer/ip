package atom.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import atom.storage.Serialiser;

/**
 * Represents a task with a specific deadline.
 */

public class Deadline extends Task {
    private LocalDateTime by;

    /**
     * Initializes a new deadline task.
     * @param description Task description.
     * @param by Date and time of the deadline.
     */
    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    /**
     * Gets the deadline date and time.
     * @return The deadline LocalDateTime.
     */
    public LocalDateTime getDateTime() {
        return this.by;
    }

    /**
     * Accepts a serialiser to process the deadline task.
     * @param serialiser The serialiser instance.
     */
    public void acceptSerialiser(Serialiser serialiser) {
        serialiser.serialise(this);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMM yyyy HHmm");
        return "[D]" + super.toString() + " (by: " + this.by.format(formatter) + ")";
    }
}
