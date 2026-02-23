package atom.task;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Service class for managing the list of tasks.
 */
public class TaskService {
    private List<Task> tasks;

    /**
     * Constructs the TaskService with a list of tasks.
     *
     * @param tasks Initial list of tasks.
     */
    public TaskService(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Retrieves the full list of tasks.
     *
     * @return List of tasks.
     */
    public List<Task> getTasks() {
        return this.tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }

    /**
     * Retrieves a specific task by its display number.
     *
     * @param taskNumber The 1-based index of the task.
     * @return The requested task.
     * @throws TaskNotFoundException If the index is out of bounds.
     */
    public Task getTask(int taskNumber) throws TaskNotFoundException {
        int taskIndex = taskNumber - 1;
        if (taskIndex < 0 || taskIndex >= this.tasks.size()) {
            String message = "Task " + taskNumber + " cannot be found.";
            throw new TaskNotFoundException(message, taskNumber, null);
        }
        return this.tasks.get(taskIndex);
    }

    /**
     * Adds a new task to the service.
     *
     * @param task The task to be added.
     */
    public void addTask(Task task) {
        this.tasks.add(task);
    }

    /**
     * Removes a task from the list by its display number.
     *
     * @param taskNumber The 1-based index of the task.
     * @throws TaskNotFoundException If the index is out of bounds.
     * @returns The removed task.
     */
    public Task removeTask(int taskNumber) throws TaskNotFoundException {
        int taskIndex = taskNumber - 1;
        if (taskIndex < 0 || taskIndex >= this.tasks.size()) {
            String message = "Task " + taskNumber + " cannot be found.";
            throw new TaskNotFoundException(message, taskNumber, null);
        }
        Task removedTask = this.tasks.get(taskIndex);
        this.tasks.remove(taskIndex);
        return removedTask;
    }

    /**
     * Marks a task as complete.
     *
     * @param taskNumber The 1-based index of the task.
     * @throws TaskNotFoundException              If the index is out of bounds.
     * @throws TaskAlreadyMarkedCompleteException If the task is already complete.
     */
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

    /**
     * Marks a task as incomplete.
     *
     * @param taskNumber The 1-based index of the task.
     * @throws TaskNotFoundException                If the index is out of bounds.
     * @throws TaskAlreadyMarkedIncompleteException If the task is already incomplete.
     */
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

    /**
     * Searches the task list for tasks whose descriptions contain the specified keyword.
     *
     * @param keyword The string to match against task descriptions.
     * @return A list of tasks that contain the given keyword.
     */
    public List<Task> findTaskWithKeyword(String keyword) {
        List<Task> tasksContainingKeyword = new ArrayList<>();
        for (Task task : tasks) {
            if (task.getDescription().contains(keyword)) {
                tasksContainingKeyword.add(task);
            }
        }
        return tasksContainingKeyword;
    }

    /**
     * Removes multiple tasks specified from the list of tasks.
     *
     * @param taskNumbers List of 1-based index of the tasks to be removed.
     * @return List of tasks removed.
     * @throws TaskNotFoundException If any of the task numbers is out of bounds.
     */
    public List<Task> removeTasks(List<Integer> taskNumbers) throws TaskNotFoundException {
        for (int number : taskNumbers) {
            int taskIndex = number - 1;
            if (taskIndex < 0 || taskIndex >= tasks.size()) {
                String message = "Task " + number + " cannot be found.";
                throw new TaskNotFoundException(message, number, null);
            }
        }
        List<Task> removedTasks = new ArrayList<>();
        for (int number : taskNumbers) {
            removedTasks.add(tasks.get(number - 1));
        }
        Collections.sort(taskNumbers, Collections.reverseOrder());
        for (int number : taskNumbers) {
            tasks.remove(number - 1);
        }
        return removedTasks;
    }
}
