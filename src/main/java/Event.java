public class Event extends Task {
    private String from;
    private String to;

    public Event(String description, String from, String to) {
        super(description);
        this.from = from;
        this.to = to;
    }

    public String getStartDateTime() {
        return this.from;
    }

    public String getEndDateTime() {
        return this.to;
    }

    public void acceptSerialiser(Serialiser serialiser) {
        serialiser.serialise(this);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + this.from + " to: " + this.to + ")";
    }
}
