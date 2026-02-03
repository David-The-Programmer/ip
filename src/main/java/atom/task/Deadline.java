package atom.task;

import atom.storage.Serialiser;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Deadline extends Task {
    private LocalDateTime by;

    public Deadline(String description, LocalDateTime by) {
        super(description);
        this.by = by;
    }

    public LocalDateTime getDateTime() {
        return this.by;
    }

    public void acceptSerialiser(Serialiser serialiser) {
        serialiser.serialise(this);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMM yyyy HHmm");
        return "[D]" + super.toString() + " (by: " + this.by.format(formatter) + ")";
    }
}
