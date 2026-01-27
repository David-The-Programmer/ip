public interface Serialiser {
    void serialise(ToDo task);

    void serialise(Deadline task);

    void serialise(Event task);
}
