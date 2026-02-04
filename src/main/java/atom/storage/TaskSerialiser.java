package atom.storage;

import java.time.format.DateTimeFormatter;

import atom.task.Deadline;
import atom.task.Event;
import atom.task.ToDo;

/**
 * Serialises Task objects into a specific string format for file storage.
 */
public class TaskSerialiser implements Serialiser {
    private String serialisedTask;

    /**
     * Serialises a ToDo task.
     * @param task The ToDo task to serialise.
     */
    @Override
    public void serialise(ToDo task) {
        int descriptionLength = task.getDescription().length();
        this.serialisedTask = "T|";
        this.serialisedTask += descriptionLength + "|";
        this.serialisedTask += task.getDescription() + "|";
        if (task.isCompleted()) {
            this.serialisedTask += "1";
        } else {
            this.serialisedTask += "0";
        }
    }

    /**
     * Serialises a Deadline task.
     * @param task The Deadline task to serialise.
     */
    @Override
    public void serialise(Deadline task) {
        int descriptionLength = task.getDescription().length();
        this.serialisedTask = "D|";
        this.serialisedTask += descriptionLength + "|";
        this.serialisedTask += task.getDescription() + "|";
        if (task.isCompleted()) {
            this.serialisedTask += "1|";
        } else {
            this.serialisedTask += "0|";
        }
        String dateTimeStr = task.getDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.serialisedTask += dateTimeStr;
    }

    /**
     * Serialises an Event task.
     * @param task The Event task to serialise.
     */
    @Override
    public void serialise(Event task) {
        int descriptionLength = task.getDescription().length();
        this.serialisedTask = "E|";
        this.serialisedTask += descriptionLength + "|";
        this.serialisedTask += task.getDescription() + "|";
        if (task.isCompleted()) {
            this.serialisedTask += "1|";
        } else {
            this.serialisedTask += "0|";
        }
        String startDateTimeStr = task.getStartDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String endDateTimeStr = task.getEndDateTime().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        this.serialisedTask += startDateTimeStr + "|";
        this.serialisedTask += endDateTimeStr;
    }

    /**
     * Retrieves the resulting serialised string.
     * @return The serialised task string.
     */
    public String getSerialisedTask() {
        return this.serialisedTask;
    }
}
