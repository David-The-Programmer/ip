package atom.task;

import atom.storage.Serialiser;

/**
 * Represents a simple todo task without a specific date or time.
 */
public class ToDo extends Task {

    /**
     * Initializes a new todo task.
     * @param description Task description.
     */
    public ToDo(String description) {
        super(description);
    }

    /**
     * Accepts a serialiser to process the todo task.
     * @param serialiser The serialiser instance.
     */
    public void acceptSerialiser(Serialiser serialiser) {
        serialiser.serialise(this);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
