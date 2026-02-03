package atom.task;

import atom.storage.Serialiser;

public class ToDo extends Task {

    public ToDo(String description) {
        super(description);
    }

    public void acceptSerialiser(Serialiser serialiser) {
        serialiser.serialise(this);
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}
