package atom.ui;

import java.util.List;
import java.util.Scanner;

import atom.command.CommandResponse;
import atom.command.ByeCommandResponse;
import atom.command.DeadlineCommandResponse;
import atom.command.DeleteCommandResponse;
import atom.command.EventCommandResponse;
import atom.command.FindCommandResponse;
import atom.command.ListCommandResponse;
import atom.command.MarkCommandResponse;
import atom.command.ToDoCommandResponse;
import atom.command.UnmarkCommandResponse;
import atom.command.UserErrorCommandResponse;
import atom.command.SystemErrorCommandResponse;
import atom.controller.Controller;
import atom.task.Task;

/**
 * Handles user interaction and command execution for the Atom application.
 */
public class CommandLineInterface implements CommandResponseHandler {
    private Controller controller;
    private Scanner scanner;

    /**
     * Initializes the CLI with required dependencies.
     *
     * @param controller main controller
     * @param scanner    Input scanner.
     */
    public CommandLineInterface(Controller controller, Scanner scanner) {
        this.controller = controller;
        this.scanner = scanner;
    }

    /**
     * Runs the main application loop to process user commands.
     */
    public void run() {
        showWelcomeMessage();
        while (true) {
            String userInput = getInput();
            CommandResponse response = controller.getResponse(userInput);
            if (response == null) {
                showUnknownCommandRemedy(userInput);
                continue;
            }
            response.acceptResponseHandler(this);
            if (response instanceof ByeCommandResponse) {
                break;
            }
        }
    }

    /**
     * Displays the welcome message and application logo.
     */
    public void showWelcomeMessage() {
        String logo = "         :::         :::::::::::    ::::::::          :::    ::: \n"
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
     *
     * @return The raw input string.
     */
    public String getInput() {
        System.out.print("\n> ");
        String userInput = scanner.nextLine();
        System.out.println();
        return userInput;
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
     *
     * @param userInput The invalid input string.
     */
    public void showUnknownCommandRemedy(String userInput) {
        System.out.println("'" + userInput + "' command not found (unknown)");
        showAllCommands();
    }

    /**
     * Handles a goodbye command response
     *
     * @param response The bye command response instance.
     */
    public void handleResponse(ByeCommandResponse response) {
        System.out.println("Goodbye! Exiting...");
    }

    /**
     * Handles a list command response to show all tasks.
     *
     * @param response The list command response instance.
     */
    public void handleResponse(ListCommandResponse response) {
        List<Task> tasks = response.getTasks();
        for (int i = 1; i <= tasks.size(); i++) {
            System.out.println(i + ". " + tasks.get(i - 1));
        }
    }

    /**
     * Processes a todo command response to add a new todo task.
     *
     * @param response The todo command response instance.
     */
    public void handleResponse(ToDoCommandResponse response) {
        System.out.println("Noted. The following task has been added: ");
        System.out.println(response.getToDo());
        System.out.println("So, in total, you have " + response.getNumRemainingTasks() + " task(s) remaining");
    }

    /**
     * Processes a deadline command response to add a new deadline task.
     *
     * @param response The deadline command response instance.
     */
    public void handleResponse(DeadlineCommandResponse response) {
        System.out.println("Noted. The following task has been added: ");
        System.out.println(response.getDeadline());
        System.out.println("So, in total, you have " + response.getNumTasksRemaining() + " task(s) remaining");
    }

    /**
     * Processes a event command response to add a new event task.
     *
     * @param response The event command response instance.
     */
    public void handleResponse(EventCommandResponse response) {
        System.out.println("Noted. The following task has been added: ");
        System.out.println(response.getEvent());
        System.out.println("So, in total, you have " + response.getNumTasksRemaining() + " task(s) remaining");
    }

    /**
     * Processes a mark command response to complete a task
     *
     * @param response The mark command response instance.
     */
    public void handleResponse(MarkCommandResponse response) {
        System.out.println("Great Job! This task is marked as complete:");
        System.out.println(response.getTask());
    }

    /**
     * Processes a unmark command response to mark a task as incomplete
     *
     * @param response The unmark command response instance.
     */
    public void handleResponse(UnmarkCommandResponse response) {
        System.out.println("Great Job! This task is marked as incomplete:");
        System.out.println(response.getTask());
    }

    /**
     * Processes a delete command response to remove a task
     *
     * @param response The delete command response instance.
     */
    public void handleResponse(DeleteCommandResponse response) {
        System.out.println("Understood. This task will be deleted:");
        System.out.println(response.getTask());
    }

    /**
     * Processes a find command response to find a task
     *
     * @param response The find command response instance.
     */
    public void handleResponse(FindCommandResponse response) {
        List<Task> matchingTasks = response.getTasks();
        if (matchingTasks.size() == 0) {
            System.out.println("There are no matching tasks in your list, please try again.");
            return;
        }
        System.out.println("The following tasks match the keyword: " + response.getKeyword() + "\n");
        for (int i = 1; i <= matchingTasks.size(); i++) {
            System.out.println(i + "." + matchingTasks.get(i - 1));
        }
    }

    public void handleResponse(UserErrorCommandResponse response) {
        System.out.println("ERROR: " + response.getException().getMessage());
    }

    public void handleResponse(SystemErrorCommandResponse response) {
        Exception exception = response.getException();
        System.out.println("ERROR: " + exception.getMessage());
        System.out.println("Here's the full error stack trace: ");
        exception.printStackTrace();
    }
}
