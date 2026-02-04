package atom.storage;

import atom.task.Task;

/**
 * Interface for deserialising string representations back into Task objects.
 */
public interface Deserialiser {
    /**
     * Parses a serialised string into a Task object internal state.
     * @param serialisedTask The raw string representing a task.
     * @throws DeserialiserException If the string format is invalid.
     */
    public void deserialise(String serialisedTask) throws DeserialiserException;

    /**
     * Retrieves the Task object produced by the last deserialise operation.
     * @return The resulting Task object.
     */
    public Task getDeserialisedTask();
}
