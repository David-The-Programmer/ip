package atom.storage;

import atom.task.Deadline;
import atom.task.Event;
import atom.task.ToDo;

import java.time.format.DateTimeFormatter;

public class TaskSerialiser implements Serialiser {
    private String serialisedTask;

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

    public String getSerialisedTask() {
        return this.serialisedTask;
    }
}
