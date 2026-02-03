package atom.storage;

import atom.task.Task;

public interface Deserialiser {
    public void deserialise(String serialisedTask) throws DeserialiserException;

    public Task getDeserialisedTask();
}
