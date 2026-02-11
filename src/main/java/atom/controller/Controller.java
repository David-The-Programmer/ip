package atom.controller;

import java.util.List;

import atom.command.ByeCommand;
import atom.command.ByeCommandResponse;
import atom.command.Command;
import atom.command.CommandResponse;
import atom.command.DeadlineCommand;
import atom.command.DeadlineCommandResponse;
import atom.command.DeleteCommand;
import atom.command.DeleteCommandResponse;
import atom.command.EventCommand;
import atom.command.EventCommandResponse;
import atom.command.FindCommand;
import atom.command.FindCommandResponse;
import atom.command.ListCommand;
import atom.command.ListCommandResponse;
import atom.command.MarkCommand;
import atom.command.MarkCommandResponse;
import atom.command.ToDoCommand;
import atom.command.ToDoCommandResponse;
import atom.command.UnknownCommandResponse;
import atom.command.UnmarkCommand;
import atom.command.UnmarkCommandResponse;
import atom.command.UserErrorCommandResponse;
import atom.command.SystemErrorCommandResponse;
import atom.parser.Parser;
import atom.storage.Storage;
import atom.storage.StorageAccessDeniedException;
import atom.storage.StorageWriteException;
import atom.task.Deadline;
import atom.task.Event;
import atom.task.Task;
import atom.task.TaskAlreadyMarkedCompleteException;
import atom.task.TaskAlreadyMarkedIncompleteException;
import atom.task.TaskNotFoundException;
import atom.task.TaskService;
import atom.task.ToDo;

public class Controller implements CommandHandler {
    private Storage storage;
    private TaskService taskService;
    private Parser parser;
    private CommandResponse commandResponse;

    /**
     * Initializes the user interface with required dependencies.
     *
     * @param parser      Command parser.
     * @param taskService Task management service.
     * @param storage     Data persistence handler.
     */
    public Controller(Parser parser, TaskService taskService, Storage storage) {
        this.parser = parser;
        this.taskService = taskService;
        this.storage = storage;
    }

    public CommandResponse getResponse(String userInput) {
        Command command = null;
        try {
            command = parser.parse(userInput);
        } catch (Exception e) {
            return new UserErrorCommandResponse(e);
        }
        if (command == null) {
            return new UnknownCommandResponse(userInput);
        }
        command.acceptHandler(this);
        return commandResponse;
    }


    /**
     * Processes a goodbye command.
     *
     * @param command The bye command instance.
     */
    public void handle(ByeCommand command) {
        commandResponse = new ByeCommandResponse();
    }

    /**
     * Processes a list command to show all tasks.
     *
     * @param command The list command instance.
     */
    public void handle(ListCommand command) {
        commandResponse = new ListCommandResponse(taskService.getTasks());
    }

    /**
     * Processes a todo command to add a new todo task.
     *
     * @param command The todo command instance.
     */
    public void handle(ToDoCommand command) {
        ToDo toDo = new ToDo(command.getDescription());
        try {
            taskService.addTask(toDo);
            storage.saveTasks(taskService.getTasks());
            int totalNumTasks = taskService.getTasks().size();
            commandResponse = new ToDoCommandResponse(toDo, totalNumTasks);
        } catch (StorageAccessDeniedException | StorageWriteException e) {
            commandResponse = new SystemErrorCommandResponse(e);
        }
    }

    /**
     * Processes a deadline command to add a new deadline task.
     *
     * @param command The deadline command instance.
     */
    public void handle(DeadlineCommand command) {
        Deadline deadline = new Deadline(command.getDescription(), command.getDateTime());
        try {
            taskService.addTask(deadline);
            storage.saveTasks(taskService.getTasks());
            int totalNumTasks = taskService.getTasks().size();
            commandResponse = new DeadlineCommandResponse(deadline, totalNumTasks);

        } catch (StorageAccessDeniedException | StorageWriteException e) {
            commandResponse = new SystemErrorCommandResponse(e);
        }
    }

    /**
     * Processes an event command to add a new event task.
     *
     * @param command The event command instance.
     */
    public void handle(EventCommand command) {
        Event deadline = new Event(command.getDescription(), command.getStartDateTime(), command.getEndDateTime());
        try {
            taskService.addTask(deadline);
            storage.saveTasks(taskService.getTasks());
            int totalNumTasks = taskService.getTasks().size();
            commandResponse = new EventCommandResponse(deadline, totalNumTasks);

        } catch (StorageAccessDeniedException | StorageWriteException e) {
            commandResponse = new SystemErrorCommandResponse(e);
        }
    }

    /**
     * Processes a mark command to complete a task.
     *
     * @param command The mark command instance.
     */
    public void handle(MarkCommand command) {
        try {
            int taskNumber = command.getTaskNumber();
            taskService.markTaskAsComplete(taskNumber);
            Task task = taskService.getTask(taskNumber);
            storage.saveTasks(taskService.getTasks());
            commandResponse = new MarkCommandResponse(task);
        } catch (TaskAlreadyMarkedCompleteException | TaskNotFoundException e) {
            commandResponse = new UserErrorCommandResponse(e);
        } catch (StorageAccessDeniedException | StorageWriteException e) {
            commandResponse = new SystemErrorCommandResponse(e);
        }
    }

    /**
     * Processes an unmark command to revert a task to incomplete.
     *
     * @param command The unmark command instance.
     */
    public void handle(UnmarkCommand command) {
        try {
            int taskNumber = command.getTaskNumber();
            taskService.markTaskAsIncomplete(taskNumber);
            Task task = taskService.getTask(taskNumber);
            storage.saveTasks(taskService.getTasks());
            commandResponse = new UnmarkCommandResponse(task);
        } catch (TaskAlreadyMarkedIncompleteException | TaskNotFoundException e) {
            commandResponse = new UserErrorCommandResponse(e);
        } catch (StorageAccessDeniedException | StorageWriteException e) {
            commandResponse = new SystemErrorCommandResponse(e);
        }
    }

    /**
     * Processes a delete command to remove a task.
     *
     * @param command The delete command instance.
     */
    public void handle(DeleteCommand command) {
        try {
            int taskNumber = command.getTaskNumber();
            taskService.removeTask(taskNumber);
            Task task = taskService.getTask(taskNumber);
            storage.saveTasks(taskService.getTasks());
            commandResponse = new DeleteCommandResponse(task);
        } catch (TaskNotFoundException e) {
            commandResponse = new UserErrorCommandResponse(e);
        } catch (StorageAccessDeniedException | StorageWriteException e) {
            commandResponse = new SystemErrorCommandResponse(e);
        }
    }

    /**
     * Processes the {@code FindCommand} by searching for tasks that match the specified keyword.
     * If matches are found, they are printed to the console in a numbered list;
     * otherwise, a message is displayed informing the user that no matches were found.
     *
     * @param command The {@code FindCommand} containing the search keyword.
     */
    public void handle(FindCommand command) {
        List<Task> tasksWithKeyword = taskService.findTaskWithKeyword(command.getKeyword());
        commandResponse = new FindCommandResponse(tasksWithKeyword, command.getKeyword());
    }
}
