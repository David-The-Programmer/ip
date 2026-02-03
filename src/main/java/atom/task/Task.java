package atom.task;

import atom.storage.Serialiser;

abstract public class Task {
    private String description;
    private Boolean isCompleted;

    public Task(String description) {
        this.description = description;
        this.isCompleted = false;
    }

    public void markAsComplete() {
        this.isCompleted = true;
    }

    public void markAsIncomplete() {
        this.isCompleted = false;
    }

    public boolean isCompleted() {
        return this.isCompleted;
    }

    public String getDescription() {
        return this.description;
    }

    public abstract void acceptSerialiser(Serialiser serialiser);

    @Override
    public String toString() {
        if (this.isCompleted) {
            return "[X] " + this.description;
        }
        return "[ ] " + this.description;
    }
}
