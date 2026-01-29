public class Deadline extends Task {
    private String by;

    public Deadline(String description, String by) {
        super(description);
        this.by = by;
    }

    public String getDateTime() {
        return this.by;
    }

    public void acceptSerialiser(Serialiser serialiser) {
        serialiser.serialise(this);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + this.by + ")";
    }
}
