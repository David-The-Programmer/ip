public class Task {
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

    @Override
    public String toString() {
        if(this.isCompleted) {
            return "[X] " + this.description;
        }
        return "[ ] " + this.description;
    }
}
