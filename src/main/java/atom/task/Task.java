package atom.task;

import atom.storage.Serialiser;

/**
 * Abstract base class representing a generic task in the application.
 */
public abstract class Task {
    private String description;
    private Boolean isCompleted;

    /**
     * Initializes a task with a description and sets completion status to false.
     * @param description Task description.
     */
    public Task(String description) {
        this.description = description;
        this.isCompleted = false;
    }

    /**
     * Marks the task as completed.
     */
    public void markAsComplete() {
        this.isCompleted = true;
    }

    /**
     * Marks the task as incomplete.
     */
    public void markAsIncomplete() {
        this.isCompleted = false;
    }

    /**
     * Checks if the task is completed.
     * @return True if completed, false otherwise.
     */
    public boolean isCompleted() {
        return this.isCompleted;
    }

    /**
     * Gets the task description.
     * @return The description string.
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * Abstract method for accepting a serialiser for data persistence.
     * @param serialiser The serialiser instance.
     */
    public abstract void acceptSerialiser(Serialiser serialiser);

    @Override
    public String toString() {
        if (this.isCompleted) {
            return "[X] " + this.description;
        }
        return "[ ] " + this.description;
    }
}
