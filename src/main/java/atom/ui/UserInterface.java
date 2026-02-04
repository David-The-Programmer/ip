package atom.ui;

import atom.command.ByeCommand;
import atom.command.Command;
import atom.command.DeadlineCommand;
import atom.command.DeleteCommand;
import atom.command.EventCommand;
import atom.command.ListCommand;
import atom.command.MarkCommand;
import atom.command.ToDoCommand;
import atom.command.UnmarkCommand;
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
import java.util.Scanner;

/**
 * Handles user interaction and command execution for the Atom application.
 */
public class UserInterface implements CommandHandler {
    private Storage storage;
    private TaskService taskService;
    private Parser parser;
    private Scanner scanner;

    /**
     * Initializes the user interface with required dependencies.
     * @param parser Command parser.
     * @param taskService Task management service.
     * @param storage Data persistence handler.
     * @param scanner Input scanner.
     */
    public UserInterface(Parser parser, TaskService taskService, Storage storage, Scanner scanner) {
        this.storage = storage;
        this.taskService = taskService;
        this.parser = parser;
        this.scanner = scanner;
    }

    /**
     * Runs the main application loop to process user commands.
     */
    public void run() {
        showWelcomeMessage();
        while (true) {
            String input = getInput();
            Command cmd = null;
            try {
                cmd = parser.parse(input);
            } catch (Exception e) {
                System.out.println("ERROR: " + e.getMessage());
                continue;
            }
            if (cmd == null) {
                showUnknownCommandRemedy(input);
                continue;
            }
            cmd.acceptHandler(this);
            if (cmd instanceof ByeCommand) {
                break;
            }
        }
    }

    /**
     * Displays the welcome message and application logo.
     */
    public void showWelcomeMessage() {
        String logo = "          :::         :::::::::::    ::::::::          :::    ::: \n"
                + "       :+: :+:          :+:       :+:    :+:        :+:+: :+:+: \n"
                + "     +:+   +:+         +:+       +:+    +:+       +:+ +:+:+ +:+ \n"
                + "   +#++:++#++:         +#+       +#+    +:+       +#+  +:+  +#+  \n"
                + "  +#+     +#+         +#+       +#+    +#+       +#+       +#+   \n"
                + " #+#     #+# #+#    #+# #+#   #+#    #+# #+#   #+#       #+# #+\n"
                + "###     ### ###    ### ###    ########  ###   ###       ###  ###\n";
        System.out.println(logo);
        System.out.println("Hello, I am an Assistive Task Organisation Manager, or A.T.O.M.");
        System.out.println("How can I help you?");
    }

    /**
     * Retrieves user input from the console.
     * @return The raw input string.
     */
    public String getInput() {
        System.out.print("\n> ");
        String userInput = scanner.nextLine();
        System.out.println();
        return userInput;
    }

    /**
     * Processes a goodbye command.
     * @param command The bye command instance.
     */
    public void handle(ByeCommand command) {
        System.out.println("Goodbye! Exiting...");
    }

    /**
     * Processes a list command to show all tasks.
     * @param command The list command instance.
     */
    public void handle(ListCommand command) {
        int taskSize = taskService.getTasks().size();
        try {
            for (int i = 1; i <= taskSize; i++) {
                System.out.println(i + ". " + taskService.getTask(i));
            }

        } catch (TaskNotFoundException e) {
            System.out.println("ERROR: " + e.getMessage());
            System.out.println("Here's the full error stack trace: ");
            e.printStackTrace();
        }
    }

    /**
     * Processes a todo command to add a new todo task.
     * @param command The todo command instance.
     */
    public void handle(ToDoCommand command) {
        try {
            taskService.addTask(new ToDo(command.getDescription()));
            storage.saveTasks(taskService.getTasks());
            System.out.println("Noted. The following task has been added: ");
            int totalNumTasks = taskService.getTasks().size();
            System.out.println(taskService.getTask(totalNumTasks));
            System.out.println("So, in total, you have " + totalNumTasks + " task(s) remaining");

        } catch (StorageAccessDeniedException e) {
            System.out.println("ERROR: " + e.getMessage());
            String remedy = "Please check if " + e.getSaveFilePath() + " is read-only,";
            remedy += " open in another program, or in a protected folder.\n";
            remedy += "If read-only or in a protected folder,";
            remedy += "ensure to change file and/or folder permissions to allow for writes.\n";
            remedy += "If open in another program, ensure to close that program and try again.\n";
            System.out.println(remedy);
            System.out.println("Here's the full error stack trace: ");
            e.printStackTrace();
            return;

        } catch (StorageWriteException | TaskNotFoundException e) {
            System.out.println("ERROR: " + e.getMessage());
            System.out.println("Here's the full error stack trace: ");
            e.printStackTrace();
            return;
        }
    }

    /**
     * Processes a deadline command to add a new deadline task.
     * @param command The deadline command instance.
     */
    public void handle(DeadlineCommand command) {
        try {
            taskService.addTask(new Deadline(command.getDescription(), command.getDateTime()));
            storage.saveTasks(taskService.getTasks());
            System.out.println("Noted. The following task has been added: ");
            int totalNumTasks = taskService.getTasks().size();
            System.out.println(taskService.getTask(totalNumTasks));
            System.out.println("So, in total, you have " + totalNumTasks + " task(s) remaining");

        } catch (StorageAccessDeniedException e) {
            System.out.println("ERROR: " + e.getMessage());
            String remedy = "Please check if " + e.getSaveFilePath() + " is read-only,";
            remedy += " open in another program, or in a protected folder.\n";
            remedy += "If read-only or in a protected folder,";
            remedy += "ensure to change file and/or folder permissions to allow for writes.\n";
            remedy += "If open in another program, ensure to close that program and try again.\n";
            System.out.println(remedy);
            System.out.println("Here's the full error stack trace: ");
            e.printStackTrace();
            return;

        } catch (StorageWriteException | TaskNotFoundException e) {
            System.out.println("ERROR: " + e.getMessage());
            System.out.println("Here's the full error stack trace: ");
            e.printStackTrace();
            return;
        }
    }

    /**
     * Processes an event command to add a new event task.
     * @param command The event command instance.
     */
    public void handle(EventCommand command) {
        try {
            taskService.addTask(new Event(command.getDescription(), command.getStartDateTime(),
                    command.getEndDateTime()));
            storage.saveTasks(taskService.getTasks());
            System.out.println("Noted. The following task has been added: ");
            int totalNumTasks = taskService.getTasks().size();
            System.out.println(taskService.getTask(totalNumTasks));
            System.out.println("So, in total, you have " + totalNumTasks + " task(s) remaining");

        } catch (StorageAccessDeniedException e) {
            System.out.println("ERROR: " + e.getMessage());
            String remedy = "Please check if " + e.getSaveFilePath() + " is read-only,";
            remedy += " open in another program, or in a protected folder.\n";
            remedy += "If read-only or in a protected folder,";
            remedy += "ensure to change file and/or folder permissions to allow for writes.\n";
            remedy += "If open in another program, ensure to close that program and try again.\n";
            System.out.println(remedy);
            System.out.println("Here's the full error stack trace: ");
            e.printStackTrace();
            return;

        } catch (StorageWriteException | TaskNotFoundException e) {
            System.out.println("ERROR: " + e.getMessage());
            System.out.println("Here's the full error stack trace: ");
            e.printStackTrace();
            return;
        }
    }

    /**
     * Processes a mark command to complete a task.
     * @param command The mark command instance.
     */
    public void handle(MarkCommand command) {
        try {
            taskService.markTaskAsComplete(command.getTaskNumber());
            System.out.println("Great Job! This task is marked as complete:");
            System.out.println(taskService.getTask(command.getTaskNumber()));
            storage.saveTasks(taskService.getTasks());
        } catch (TaskAlreadyMarkedCompleteException e) {
            System.out.println("ERROR: " + e.getMessage());
            System.out.println("No further action required");
        } catch (TaskNotFoundException e) {
            System.out.println("ERROR: " + e.getMessage());
            System.out.println("Please try again.");
        } catch (StorageWriteException e) {
            System.out.println("ERROR: " + e.getMessage());
            System.out.println("Here's the full error stack trace: ");
            e.printStackTrace();
        } catch (StorageAccessDeniedException e) {
            System.out.println("ERROR: " + e.getMessage());
            String remedy = "Please check if " + e.getSaveFilePath() + " is read-only,";
            remedy += " open in another program, or in a protected folder.\n";
            remedy += "If read-only or in a protected folder,";
            remedy += "ensure to change file and/or folder permissions to allow for writes.\n";
            remedy += "If open in another program, ensure to close that program and try again.\n";
            System.out.println(remedy);
            System.out.println("Here's the full error stack trace: ");
            e.printStackTrace();
        }
    }

    /**
     * Processes an unmark command to revert a task to incomplete.
     * @param command The unmark command instance.
     */
    public void handle(UnmarkCommand command) {
        try {
            taskService.markTaskAsIncomplete(command.getTaskNumber());
            System.out.println("Great Job! This task is marked as complete:");
            System.out.println(taskService.getTask(command.getTaskNumber()));
            storage.saveTasks(taskService.getTasks());
        } catch (TaskAlreadyMarkedIncompleteException e) {
            System.out.println("ERROR: " + e.getMessage());
            System.out.println("No further action required");
        } catch (TaskNotFoundException e) {
            System.out.println("ERROR: " + e.getMessage());
            System.out.println("Please try again.");
        } catch (StorageWriteException e) {
            System.out.println("ERROR: " + e.getMessage());
            System.out.println("Here's the full error stack trace: ");
            e.printStackTrace();
        } catch (StorageAccessDeniedException e) {
            System.out.println("ERROR: " + e.getMessage());
            String remedy = "Please check if " + e.getSaveFilePath() + " is read-only,";
            remedy += " open in another program, or in a protected folder.\n";
            remedy += "If read-only or in a protected folder,";
            remedy += "ensure to change file and/or folder permissions to allow for writes.\n";
            remedy += "If open in another program, ensure to close that program and try again.\n";
            System.out.println(remedy);
            System.out.println("Here's the full error stack trace: ");
            e.printStackTrace();
        }
    }

    /**
     * Processes a delete command to remove a task.
     * @param command The delete command instance.
     */
    public void handle(DeleteCommand command) {
        try {
            Task taskToDelete = taskService.getTask(command.getTaskNumber());
            System.out.println("Understood. This task will be deleted:");
            System.out.println(taskToDelete);
            taskService.removeTask(command.getTaskNumber());
            storage.saveTasks(taskService.getTasks());
        } catch (TaskNotFoundException e) {
            System.out.println("ERROR: " + e.getMessage());
            System.out.println("Please try again.");
        } catch (StorageWriteException e) {
            System.out.println("ERROR: " + e.getMessage());
            System.out.println("Here's the full error stack trace: ");
            e.printStackTrace();
        } catch (StorageAccessDeniedException e) {
            System.out.println("ERROR: " + e.getMessage());
            String remedy = "Please check if " + e.getSaveFilePath() + " is read-only,";
            remedy += " open in another program, or in a protected folder.\n";
            remedy += "If read-only or in a protected folder,";
            remedy += "ensure to change file and/or folder permissions to allow for writes.\n";
            remedy += "If open in another program, ensure to close that program and try again.\n";
            System.out.println(remedy);
            System.out.println("Here's the full error stack trace: ");
            e.printStackTrace();
        }
    }

    /**
     * Displays all valid commands and their formats.
     */
    public void showAllCommands() {
        String message = "The following are the only valid commands:\n\n" + "   todo <description>\n\n"
                + "   deadline <description> /by <datetime of deadline>\n\n"
                + "   event <description> /from <datetime that event starts> /to <datetime that event ends>\n\n"
                + "   list\n\n" + "   mark <task number shown after entering the list command>\n\n"
                + "   unmark <task number shown after entering the list command>\n\n" + "   bye";
        System.out.println(message);
    }

    /**
     * Informs the user of an unknown command and suggests valid ones.
     * @param userInput The invalid input string.
     */
    public void showUnknownCommandRemedy(String userInput) {
        System.out.println("'" + userInput + "' command not found (unknown)");
        showAllCommands();
    }
}
