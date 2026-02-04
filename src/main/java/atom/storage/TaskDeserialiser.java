package atom.storage;

import atom.task.Deadline;
import atom.task.Event;
import atom.task.Task;
import atom.task.ToDo;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.HashSet;

public class TaskDeserialiser implements Deserialiser {
    private Task deserialisedTask;
    private String serialisedTask;

    @Override
    public void deserialise(String serialisedTask) throws DeserialiserException {
        this.serialisedTask = serialisedTask.trim();
        String[] result = this.serialisedTask.split("\\|", 2);
        if (result.length != 2) {
            String message = "unable to deserialise task type";
            message += " from serialisedTask: '" + serialisedTask + "'";
            throw new DeserialiserException(message, null, "");
        }
        String type = result[0];
        String remaining = result[1];
        HashSet<Character> validTaskTypeChars = new HashSet<>();
        validTaskTypeChars.add('T');
        validTaskTypeChars.add('D');
        validTaskTypeChars.add('E');
        if (!validTaskTypeChars.contains(type.toCharArray()[0])) {
            String message = "serialisedTask: ";
            message += "'" + serialisedTask + "'";
            message += " contains invalid task type char '" + type + "':";
            throw new DeserialiserException(message, null, "");
        }

        result = remaining.split("\\|", 2);
        if (result.length != 2) {
            String message = "unable to deserialise description length";
            message += " from serialisedTask: '" + serialisedTask + "'";
            throw new DeserialiserException(message, null, "");
        }
        remaining = result[1];
        int descriptionLength = 0;
        try {
            descriptionLength = Integer.parseInt(result[0]);
        } catch (NumberFormatException exception) {
            String message = "description length " + "'" + result[0] + "'";
            message += " cannot be parsed into integer";
            message += " from serialisedTask: '" + serialisedTask + "'";
            throw new DeserialiserException(message, exception, "");
        }

        if (descriptionLength >= remaining.length()) {
            String message = "description length " + "'" + result[0] + "'";
            message += " cannot exceed actual description";
            message += " from serialisedTask: '" + serialisedTask + "'";
            throw new DeserialiserException(message, null, "");
        }
        String taskDescription = remaining.substring(0, descriptionLength);
        remaining = remaining.substring(descriptionLength + 1);

        result = remaining.split("\\|", 2);
        if (!(result[0].equals("0") || result[0].equals("1"))) {
            String message = "unable to deserialise isCompleted attribute " + "'" + result[0] + "'";
            message += " from serialisedTask: '" + serialisedTask + "'";
            throw new DeserialiserException(message, null, "");
        }
        Boolean isCompleted = result[0].equals("1");

        if (type.equals("T")) {
            deserialisedTask = new ToDo(taskDescription);
        } else if (type.equals("D")) {
            if (result.length != 2) {
                String message = "unable to deserialise deadline datetime ";
                message += " from serialisedTask: '" + serialisedTask + "'";
                throw new DeserialiserException(message, null, "");
            }
            LocalDateTime deadlineDateTime = null;
            try {
                deadlineDateTime = LocalDateTime.parse(result[1]);
            } catch (DateTimeParseException exception) {
                String message = "unable to deserialise deadline datetime ";
                message += " from serialisedTask: '" + serialisedTask + "'";
                throw new DeserialiserException(message, exception, "");
            }
            deserialisedTask = new Deadline(taskDescription, deadlineDateTime);
        } else if (type.equals("E")) {
            if (result.length != 2) {
                String message = "unable to deserialise event datetimes ";
                message += " from serialisedTask: '" + serialisedTask + "'";
                throw new DeserialiserException(message, null, "");
            }
            remaining = result[1];
            result = remaining.split("\\|", 2);
            if (result.length != 2) {
                String message = "unable to deserialise event datetimes ";
                message += " from serialisedTask: '" + serialisedTask + "'";
                throw new DeserialiserException(message, null, "");
            }
            LocalDateTime startDateTime = null;
            LocalDateTime endDateTime = null;
            try {
                startDateTime = LocalDateTime.parse(result[0]);
                endDateTime = LocalDateTime.parse(result[1]);
            } catch (DateTimeParseException exception) {
                String message = "unable to deserialise event datetime ";
                message += " from serialisedTask: '" + serialisedTask + "'";
                throw new DeserialiserException(message, exception, "");
            }
            deserialisedTask = new Event(taskDescription, startDateTime, endDateTime);
        }

        if (isCompleted) {
            deserialisedTask.markAsComplete();
        }
    }

    public Task getDeserialisedTask() {
        return deserialisedTask;
    }
}
