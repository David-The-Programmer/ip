package atom.storage;

import atom.task.Deadline;
import atom.task.Event;
import atom.task.ToDo;

/**
 * Interface for serialising different types of Task objects into string representations.
 * This follows the Visitor design pattern to handle specific task types.
 */
public interface Serialiser {
    /**
     * Serialises a ToDo task.
     * @param task The ToDo task to be serialised.
     */
    public void serialise(ToDo task);

    /**
     * Serialises a Deadline task.
     * @param task The Deadline task to be serialised.
     */
    public void serialise(Deadline task);

    /**
     * Serialises an Event task.
     * @param task The Event task to be serialised.
     */
    public void serialise(Event task);

    /**
     * Retrieves the resulting serialised string after a serialise call.
     * @return The serialised task string.
     */
    public String getSerialisedTask();
}
