package atom.task;

import atom.storage.Serialiser;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;

    public Event(String description, LocalDateTime from, LocalDateTime to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public LocalDateTime getStartDateTime() {
        return this.from;
    }

    public LocalDateTime getEndDateTime() {
        return this.to;
    }

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
