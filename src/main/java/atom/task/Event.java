package atom.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import atom.storage.Serialiser;
/**
 * Represents a task that occurs within a specific time range.
 */
public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;

    /**
     * Initializes a new event task with start and end times.
     * @param description Task description.
     * @param from Start date and time.
     * @param to End date and time.
     */
    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    /**
     * Gets the starting date and time of the event.
     * @return Start LocalDateTime.
     */
    public LocalDateTime getStartDateTime() {
        return this.from;
    }

    /**
     * Gets the ending date and time of the event.
     * @return End LocalDateTime.
     */
    public LocalDateTime getEndDateTime() {
        return this.to;
    }

    /**
     * Accepts a serialiser to process the event task.
     * @param serialiser The serialiser instance.
     */
    public void acceptSerialiser(Serialiser serialiser) {
        serialiser.serialise(this);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMM yyyy HHmm");
        String fromDateTimeStr = this.from.format(formatter);
        String toDateTimeStr = this.to.format(formatter);
        return "[E]" + super.toString() + " (from: " + fromDateTimeStr + " to: " + toDateTimeStr + ")";
    }
}
