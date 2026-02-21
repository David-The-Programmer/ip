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
    private String serialisedTask;
    private int index = 0;

    /**
     * Parses a serialised string into a Task object.
     *
     * @param serialisedTask The raw string from the data file.
     * @throws DeserialiserException If the string format is invalid or data is corrupted.
     */
    @Override
    public void deserialise(String serialisedTask) throws DeserialiserException {
        init(serialisedTask);
        String taskType = nextTaskType();
        String description = nextDescription();
        boolean isCompleted = nextCompletionStatus();
        if (taskType.equals("T")) {
            deserialisedTask = new ToDo(description);
        } else if (taskType.equals("D")) {
            LocalDateTime deadlineDateTime = nextDateTime();
            deserialisedTask = new Deadline(description, deadlineDateTime);
        } else {
            LocalDateTime startDateTime = nextDateTime();
            LocalDateTime endDateTime = nextDateTime();
            deserialisedTask = new Event(description, startDateTime, endDateTime);
        }
        if (isCompleted) {
            deserialisedTask.markAsComplete();
        }
    }


    private String nextSegment() {
        int nextDelimitterIndex = serialisedTask.indexOf("|", index);
        if (index == serialisedTask.length()) {
            return null;
        }
        String nextSegment;
        if (nextDelimitterIndex == -1) {
            nextSegment = serialisedTask.substring(index);
            index = serialisedTask.length();
            return nextSegment;
        }
        nextSegment = serialisedTask.substring(index, nextDelimitterIndex);
        index = nextDelimitterIndex + 1;
        return nextSegment;
    }

    private void init(String serialisedTask) {
        this.serialisedTask = serialisedTask;
        this.index = 0;
    }

    private String nextTaskType() throws DeserialiserException {
        String taskType = nextSegment();
        boolean isValidType = "T".equals(taskType) || "D".equals(taskType) || "E".equals(taskType);
        if (!isValidType) {
            String message = String.format("unable to deserialise: invalid taskType '%s'", taskType);
            throw new DeserialiserException(message, null);
        }
        return taskType;
    }

    private String nextDescription() throws DeserialiserException {
        int numChars;
        String descLength = nextSegment();
        try {
            numChars = Integer.parseInt(descLength);
        } catch (NumberFormatException exception) {
            String message = String.format("unable to deserialise: invalid descriptionLength '%s", descLength);
            throw new DeserialiserException(message, exception);
        }
        if (numChars <= 0) {
            String message = String.format("unable to deserialise: invalid descriptionLength '%s", descLength);
            throw new DeserialiserException(message, null);
        }
        if (index + numChars > serialisedTask.length() - 1) {
            int remainingNumChars = serialisedTask.length() - (index + 1);
            String message =
                String.format("unable to get next %d chars, only %d chars left to get", numChars, remainingNumChars);
            throw new DeserialiserException(message, null);
        }
        String description = serialisedTask.substring(index, index + numChars);
        index += numChars + 1;
        return description;
    }

    private boolean nextCompletionStatus() throws DeserialiserException {
        String completionStatus = nextSegment();
        if (!("1".equals(completionStatus) || "0".equals(completionStatus))) {
            String message = String.format("unable to deserialise: invalid completionStatus '%s'", completionStatus);
            throw new DeserialiserException(message, null);
        }
        return "1".equals(completionStatus);
    }

    private LocalDateTime nextDateTime() throws DeserialiserException {
        String rawDateTime = nextSegment();
        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(rawDateTime);
        } catch (DateTimeParseException exception) {
            String message = String.format("unable to deserialise: invalid datetime '%s", rawDateTime);
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
