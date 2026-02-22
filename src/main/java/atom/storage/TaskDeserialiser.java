package atom.storage;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;

import atom.task.Deadline;
import atom.task.Event;
import atom.task.Task;
import atom.task.ToDo;

/**
 * Deserialises strings from storage back into Task objects.
 */
public class TaskDeserialiser implements Deserialiser {
    private Task deserialisedTask;

    /**
     * Parses a serialised string into a Task object.
     *
     * @param serialisedTask The raw string from the data file.
     * @throws DeserialiserException If the string format is invalid or data is corrupted.
     */
    @Override
    public void deserialise(String serialisedTask) throws DeserialiserException {
        SerialisedTaskStream stream = new SerialisedTaskStream(serialisedTask);
        String taskType = deserialiseTaskType(stream);
        String description = deserialiseDescription(stream);
        boolean isCompleted = deserialiseCompletionStatus(stream);
        if (taskType.equals("T")) {
            deserialisedTask = new ToDo(description);
        } else if (taskType.equals("D")) {
            LocalDateTime deadlineDateTime = deserialiseDateTime(stream);
            deserialisedTask = new Deadline(description, deadlineDateTime);
        } else {
            LocalDateTime startDateTime = deserialiseDateTime(stream);
            LocalDateTime endDateTime = deserialiseDateTime(stream);
            deserialisedTask = new Event(description, startDateTime, endDateTime);
        }
        if (isCompleted) {
            deserialisedTask.markAsComplete();
        }
    }

    /**
     * Deserialises the task type from the serialised task stream.
     *
     * @param stream SerialisedTaskStream
     * @throws DeserialiserException If deserialisation fails due to corrupt stream content.
     */
    private String deserialiseTaskType(SerialisedTaskStream stream) throws DeserialiserException {
        if (stream.isExhausted()) {
            throw new DeserialiserException("unable to deserialise task type: stream is empty", null);
        }
        String taskType = stream.nextUntil("|");
        boolean isValidType = "T".equals(taskType) || "D".equals(taskType) || "E".equals(taskType);
        if (!isValidType) {
            String message = String.format("unable to deserialise task type: invalid task type '%s'", taskType);
            throw new DeserialiserException(message, null);
        }
        return taskType;
    }

    /**
     * Deserialises the description from the serialised task stream.
     *
     * @param stream SerialisedTaskStream
     * @throws DeserialiserException If deserialisation fails due to corrupt stream content.
     */
    private String deserialiseDescription(SerialisedTaskStream stream) throws DeserialiserException {
        if (stream.isExhausted()) {
            throw new DeserialiserException("unable to deserialise description: stream is empty", null);
        }
        int numChars;
        String descLength = stream.nextUntil("|");
        try {
            numChars = Integer.parseInt(descLength);
        } catch (NumberFormatException exception) {
            String message =
                String.format("unable to deserialise description: invalid descriptionLength '%s", descLength);
            throw new DeserialiserException(message, exception);
        }
        if (numChars <= 0) {
            String message =
                String.format("unable to deserialise description: invalid descriptionLength '%s", descLength);
            throw new DeserialiserException(message, null);
        }
        String description = stream.nextNSizeSegment(numChars);
        if (stream.isExhausted()) {
            String message = "unable to deserialise description: descriptionLength greater than actual description";
            throw new DeserialiserException(message, null);
        }
        stream.nextUntil("|");
        return description;
    }

    /**
     * Deserialises the completion status from the serialised task stream.
     *
     * @param stream SerialisedTaskStream
     * @throws DeserialiserException If deserialisation fails due to corrupt stream content.
     */
    private boolean deserialiseCompletionStatus(SerialisedTaskStream stream) throws DeserialiserException {
        if (stream.isExhausted()) {
            throw new DeserialiserException("unable to deserialise completion status: stream is empty", null);
        }
        String completionStatus = stream.nextUntil("|");
        if (!("1".equals(completionStatus) || "0".equals(completionStatus))) {
            String message = String.format("unable to deserialise completion status: invalid completion status '%s'",
                completionStatus);
            throw new DeserialiserException(message, null);
        }
        return "1".equals(completionStatus);
    }

    /**
     * Deserialises the datetime from the serialised task stream.
     *
     * @param stream SerialisedTaskStream
     * @throws DeserialiserException If deserialisation fails due to corrupt stream content.
     */
    private LocalDateTime deserialiseDateTime(SerialisedTaskStream stream) throws DeserialiserException {
        if (stream.isExhausted()) {
            throw new DeserialiserException("unable to deserialise datetime: stream is empty", null);
        }
        String rawDateTime = stream.nextUntil("|");
        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(rawDateTime);
        } catch (DateTimeParseException exception) {
            String message = String.format("unable to deserialise datetime: invalid datetime '%s", rawDateTime);
            throw new DeserialiserException(message, exception);
        }
        return dateTime;
    }

    /**
     * Returns the task object generated from deserialisation.
     *
     * @return The resulting Task object.
     */
    public Task getDeserialisedTask() {
        return deserialisedTask;
    }
}
