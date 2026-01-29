public interface Serialiser {
    public void serialise(ToDo task);

    public void serialise(Deadline task);

    public void serialise(Event task);

    public String getSerialisedTask();
}
