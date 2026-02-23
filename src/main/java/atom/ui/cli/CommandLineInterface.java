package atom.ui.cli;

import java.util.List;
import java.util.Scanner;

import atom.command.ByeCommandResponse;
import atom.command.CommandResponse;
import atom.command.DeadlineCommandResponse;
import atom.command.DeleteCommandResponse;
import atom.command.EventCommandResponse;
import atom.command.FindCommandResponse;
import atom.command.ListCommandResponse;
import atom.command.MarkCommandResponse;
import atom.command.MassDeleteCommandResponse;
import atom.command.MassDeleteSystemErrorCommandResponse;
import atom.command.MassDeleteUserErrorCommandResponse;
import atom.command.SystemErrorCommandResponse;
import atom.command.ToDoCommandResponse;
import atom.command.UnknownCommandResponse;
import atom.command.UnmarkCommandResponse;
import atom.command.UserErrorCommandResponse;
import atom.controller.Controller;
import atom.task.Task;
import atom.ui.CommandResponseHandler;

/**
 * Handles user interaction and command execution for the Atom application.
 */
public class CommandLineInterface implements CommandResponseHandler {
    private Controller controller;
    private Scanner scanner;

    /**
     * Constructs the CLI with required dependencies.
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
     * Handles the cli interactions given a response to a bye command from the atom controller.
     *
     * @param response Response object from the atom controller.
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
     * Handles the cli interactions given a response to a todo command from the atom controller.
     *
     * @param response Response object from the atom controller.
     */
    public void handleResponse(ToDoCommandResponse response) {
        System.out.println("Noted. The following task has been added: ");
        System.out.println(response.getToDo());
        System.out.println("So, in total, you have " + response.getNumRemainingTasks() + " task(s) remaining");
    }

    /**
     * Handles the cli interactions given a response to a deadline command from the atom controller.
     *
     * @param response Response object from the atom controller.
     */
    public void handleResponse(DeadlineCommandResponse response) {
        System.out.println("Noted. The following task has been added: ");
        System.out.println(response.getDeadline());
        System.out.println("So, in total, you have " + response.getNumTasksRemaining() + " task(s) remaining");
    }

    /**
     * Handles the cli interactions given a response to a event command from the atom controller.
     *
     * @param response Response object from the atom controller.
     */
    public void handleResponse(EventCommandResponse response) {
        System.out.println("Noted. The following task has been added: ");
        System.out.println(response.getEvent());
        System.out.println("So, in total, you have " + response.getNumTasksRemaining() + " task(s) remaining");
    }

    /**
     * Handles the cli interactions given a response to a mark command from the atom controller.
     *
     * @param response Response object from the atom controller.
     */
    public void handleResponse(MarkCommandResponse response) {
        System.out.println("Great Job! This task is marked as complete:");
        System.out.println(response.getTask());
    }

    /**
     * Handles the cli interactions given a response to a unmark command from the atom controller.
     *
     * @param response Response object from the atom controller.
     */
    public void handleResponse(UnmarkCommandResponse response) {
        System.out.println("Alright. This task is marked as incomplete:");
        System.out.println(response.getTask());
    }

    /**
     * Handles the cli interactions given a response to a delete command from the atom controller.
     *
     * @param response Response object from the atom controller.
     */
    public void handleResponse(DeleteCommandResponse response) {
        System.out.println("Understood. This task is deleted:");
        System.out.println(response.getTask());
    }

    /**
     * Handles the cli interactions given a response to a find command from the atom controller.
     *
     * @param response Response object from the atom controller.
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

    /**
     * Handles the cli interactions given a user error response from the atom controller.
     *
     * @param response Response object from the atom controller.
     */
    public void handleResponse(UserErrorCommandResponse response) {
        System.out.println("ERROR: " + response.getException().getMessage());
    }

    /**
     * Handles the cli interactions given a system error response from the atom controller.
     *
     * @param response Response object from the atom controller.
     */
    public void handleResponse(SystemErrorCommandResponse response) {
        Exception exception = response.getException();
        System.out.println("ERROR: " + exception.getMessage());
        System.out.println("Here's the full error stack trace: ");
        exception.printStackTrace();
    }

    /**
     * Handles the cli interactions given a response to an unknown command from the atom controller.
     *
     * @param response Response object from the atom controller.
     */
    public void handleResponse(UnknownCommandResponse response) {
        String userInput = response.getUserInput();
        System.out.println("'" + userInput + "' command not found (unknown)");
        String message = "The following are the only valid commands:\n\n"
                + "   todo <description>\n\n"
                + "   deadline <description> /by <datetime of deadline>\n\n"
                + "   event <description> /from <datetime that event starts> /to <datetime that event ends>\n\n"
                + "   list\n\n" + "   mark <task number shown after entering the list command>\n\n"
                + "   unmark <task number shown after entering the list command>\n\n"
                + "   find <keyword in description>\n\n"
                + "   bye";
        System.out.println(message);
    }

    /**
     * Handles the cli interactions given a response if user tries to delete multiple tasks.
     *
     * @param response The response given if user tries to delete multiple tasks.
     */
    @Override
    public void handleResponse(MassDeleteCommandResponse response) {
        List<Task> deletedTasks = response.getDeletedTasks();
        String message = "Understood. The following tasks were deleted: \n";
        for (Task task : deletedTasks) {
            message += String.format("    - %s\n", task);
        }
        System.out.println(message);
    }

    /**
     * Handles the cli interactions given a response after system error occurs upon mass delete command execution.
     *
     * @param response The response of internal system error after mass delete command execution.
     */
    @Override
    public void handleResponse(MassDeleteSystemErrorCommandResponse response) {
        Exception exception = response.getException();
        System.out.println("ERROR: " + exception.getMessage());
        System.out.println("Here's the full error stack trace: ");
        exception.printStackTrace();
        System.out.println("No tasks were deleted.");
    }

    /**
     * Handles the cli interactions given a response after user error occurs upon mass delete command execution.
     *
     * @param response The response of user error after mass delete command execution.
     */
    @Override
    public void handleResponse(MassDeleteUserErrorCommandResponse response) {
        Exception exception = response.getException();
        System.out.println("ERROR: " + exception.getMessage());
        System.out.println("No tasks were deleted.");
    }
}
