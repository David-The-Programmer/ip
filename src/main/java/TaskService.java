import java.util.List;

public class TaskService {
    private List<Task> tasks;

    public TaskService(List<Task> tasks) {
        this.tasks = tasks;
    }

    public List<Task> getTasks() {
        return this.tasks;
    }

    public Task getTask(int taskNumber) throws TaskNotFoundException {
        int taskIndex = taskNumber - 1;
        if (taskIndex < 0 || taskIndex >= this.tasks.size()) {
            String message = "Task " + taskNumber + " cannot be found.";
            throw new TaskNotFoundException(message, taskNumber, null);
        }
        return this.tasks.get(taskIndex);
    }

    public void addTask(Task task) {
        this.tasks.add(task);
    }

    public void removeTask(int taskNumber) throws TaskNotFoundException {
        int taskIndex = taskNumber - 1;
        if (taskIndex < 0 || taskIndex >= this.tasks.size()) {
            String message = "Task " + taskNumber + " cannot be found.";
            throw new TaskNotFoundException(message, taskNumber, null);
        }
        this.tasks.remove(taskIndex);
    }

    public void markTaskAsComplete(int taskNumber)
            throws TaskNotFoundException, TaskAlreadyMarkedCompleteException {
        int taskIndex = taskNumber - 1;
        if (taskIndex < 0 || taskIndex >= this.tasks.size()) {
            String message = "Task " + taskNumber + " cannot be found.";
            throw new TaskNotFoundException(message, taskNumber, null);
        }
        if (this.tasks.get(taskIndex).isCompleted()) {
            String message = "Task " + taskNumber + " is already marked complete.";
            throw new TaskAlreadyMarkedCompleteException(message, null);
        }
        this.tasks.get(taskIndex).markAsComplete();
    }

    public void markTaskAsIncomplete(int taskNumber)
            throws TaskNotFoundException, TaskAlreadyMarkedIncompleteException {
        int taskIndex = taskNumber - 1;
        if (taskIndex < 0 || taskIndex >= this.tasks.size()) {
            String message = "Task " + taskNumber + " cannot be found.";
            throw new TaskNotFoundException(message, taskNumber, null);
        }
        if (!this.tasks.get(taskIndex).isCompleted()) {
            String message = "Task " + taskNumber + " is already marked incomplete.";
            throw new TaskAlreadyMarkedIncompleteException(message, null);
        }
        this.tasks.get(taskIndex).markAsIncomplete();
    }
}
