package atom.storage;

import atom.task.Deadline;
import atom.task.Event;
import atom.task.ToDo;

public interface Serialiser {
    public void serialise(ToDo task);

    public void serialise(Deadline task);

    public void serialise(Event task);

    public String getSerialisedTask();
}
