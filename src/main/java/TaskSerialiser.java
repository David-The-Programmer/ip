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
        this.serialisedTask += task.getDateTime();
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
        this.serialisedTask += task.getStartDateTime() + "|";
        this.serialisedTask += task.getEndDateTime();
    }

    public String getSerialisedTask() {
        return this.serialisedTask;
    }
}
