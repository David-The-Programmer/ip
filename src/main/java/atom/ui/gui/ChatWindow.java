package atom.ui.gui;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

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
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * Contains all logic for the chat window gui.
 */
public class ChatWindow extends AnchorPane implements CommandResponseHandler {
    private Controller atomController;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    /**
     * Sets the atom controller (engine that contains all logic to handle commands).
     *
     * @param atomController Application engine that contains all logic to handle commands.
     */
    public void setAtomController(Controller atomController) {
        this.atomController = atomController;
    }

    /**
     * Initialises the ChatWindow gui.
     */
    @FXML
    public void initialize() {
        dialogContainer.heightProperty().addListener((observable) -> {
            scrollPane.setVvalue(1.0);
        });
        sendButton.disableProperty().bind(
                Bindings.createBooleanBinding(() -> userInput.getText().trim().isEmpty(),
                        userInput.textProperty()
                )
        );
    }

    /**
     * Handles gui interactions when user enters input.
     */
    @FXML
    private void handleUserInput() {
        String userInputStr = userInput.getText();
        if (userInputStr.trim().isEmpty()) {
            return;
        }
        dialogContainer.getChildren().add(DialogBox.getUserDialog(userInputStr));
        CommandResponse response = atomController.getResponse(userInputStr);
        response.acceptResponseHandler(this);
        userInput.clear();
    }

    /**
     * Handles the gui interactions given a response to a todo command from the atom controller.
     *
     * @param response Response object from the atom controller.
     */
    @Override
    public void handleResponse(ToDoCommandResponse response) {
        String responseStr = "Noted. The following task has been added: \n";
        responseStr += response.getToDo() + "\n";
        responseStr += "So, in total, you have " + response.getNumRemainingTasks() + " task(s) remaining";
        dialogContainer.getChildren().add(DialogBox.getAtomDialog(responseStr));
    }

    /**
     * Handles the gui interactions given a response to a deadline command from the atom controller.
     *
     * @param response Response object from the atom controller.
     */
    @Override
    public void handleResponse(DeadlineCommandResponse response) {
        String responseStr = "Noted. The following task has been added: \n";
        responseStr += response.getDeadline() + "\n";
        responseStr += "So, in total, you have " + response.getNumTasksRemaining() + " task(s) remaining";
        dialogContainer.getChildren().add(DialogBox.getAtomDialog(responseStr));
    }

    /**
     * Handles the gui interactions given a response to an event command from the atom controller.
     *
     * @param response Response object from the atom controller.
     */
    @Override
    public void handleResponse(EventCommandResponse response) {
        String responseStr = "Noted. The following task has been added: \n";
        responseStr += response.getEvent() + "\n";
        responseStr += "So, in total, you have " + response.getNumTasksRemaining() + " task(s) remaining";
        dialogContainer.getChildren().add(DialogBox.getAtomDialog(responseStr));
    }

    /**
     * Handles the gui interactions given a response to a list command from the atom controller.
     *
     * @param response Response object from the atom controller.
     */
    @Override
    public void handleResponse(ListCommandResponse response) {
        List<Task> tasks = response.getTasks();
        String responseStr = "";
        for (int i = 1; i <= tasks.size(); i++) {
            responseStr += i + ". " + tasks.get(i - 1) + "\n";
        }
        if (responseStr.isEmpty()) {
            String message = "There are 0 tasks in your list. Add tasks to get started! :)";
            dialogContainer.getChildren().add(DialogBox.getAtomDialog(message));
            return;
        }
        dialogContainer.getChildren().add(DialogBox.getAtomDialog(responseStr));
    }

    /**
     * Handles the gui interactions given a response to a bye command from the atom controller.
     *
     * @param response Response object from the atom controller.
     */
    @Override
    public void handleResponse(ByeCommandResponse response) {
        String responseStr = "Goodbye! Closing in 3 seconds...";
        dialogContainer.getChildren().add(DialogBox.getAtomDialog(responseStr));
        PauseTransition timeout = new PauseTransition(Duration.seconds(3));
        timeout.setOnFinished(action -> {
            Platform.exit();
        });
        timeout.play();
    }

    /**
     * Handles the gui interactions given a response to a delete command from the atom controller.
     *
     * @param response Response object from the atom controller.
     */
    @Override
    public void handleResponse(DeleteCommandResponse response) {
        String responseStr = "Understood. This task will be deleted: \n";
        responseStr += response.getTask();
        dialogContainer.getChildren().add(DialogBox.getAtomDialog(responseStr));
    }

    /**
     * Handles the gui interactions given a response to a mark command from the atom controller.
     *
     * @param response Response object from the atom controller.
     */
    @Override
    public void handleResponse(MarkCommandResponse response) {
        String responseStr = "Great Job! This task is marked as complete: \n";
        responseStr += response.getTask();
        dialogContainer.getChildren().add(DialogBox.getAtomDialog(responseStr));
    }

    /**
     * Handles the gui interactions given a response to an unmark command from the atom controller.
     *
     * @param response Response object from the atom controller.
     */
    @Override
    public void handleResponse(UnmarkCommandResponse response) {
        String responseStr = "Alright. This task is marked as incomplete: \n";
        responseStr += response.getTask();
        dialogContainer.getChildren().add(DialogBox.getAtomDialog(responseStr));
    }

    /**
     * Handles the gui interactions given a response to a find command from the atom controller.
     *
     * @param response Response object from the atom controller.
     */
    @Override
    public void handleResponse(FindCommandResponse response) {
        List<Task> matchingTasks = response.getTasks();
        if (matchingTasks.isEmpty()) {
            String responseStr = "There are no matching tasks in your list, please try again.";
            dialogContainer.getChildren().add(DialogBox.getAtomDialog(responseStr));
            return;
        }
        String responseStr = "The following tasks match the keyword: " + response.getKeyword() + "\n";
        for (int i = 1; i <= matchingTasks.size(); i++) {
            responseStr += i + ". " + matchingTasks.get(i - 1) + "\n";
        }
        dialogContainer.getChildren().add(DialogBox.getAtomDialog(responseStr));

    }

    /**
     * Handles the gui interactions given a user error response from the atom controller.
     *
     * @param response Response object from the atom controller.
     */
    @Override
    public void handleResponse(UserErrorCommandResponse response) {
        String responseStr = "ERROR: " + response.getException().getMessage();
        dialogContainer.getChildren().add(DialogBox.getAtomDialog(responseStr));
    }

    /**
     * Handles the gui interactions given a system error response from the atom controller.
     *
     * @param response Response object from the atom controller.
     */
    @Override
    public void handleResponse(SystemErrorCommandResponse response) {
        Exception exception = response.getException();
        String responseStr = "ERROR: " + exception.getMessage();
        responseStr += "Here's the full error stack trace: \n";
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        exception.printStackTrace(printWriter);
        responseStr += stringWriter.toString();
        dialogContainer.getChildren().add(DialogBox.getAtomDialog(responseStr));
    }

    /**
     * Handles the gui interactions given a response to an unknown command from the atom controller.
     *
     * @param response Response object from the atom controller.
     */
    @Override
    public void handleResponse(UnknownCommandResponse response) {
        String userInput = response.getUserInput();
        String responseStr = "'" + userInput + "' command not found (unknown)\n";
        responseStr += "The following are the only valid commands:\n\n"
                + "   todo <description>\n\n"
                + "   deadline <description> /by <date> <time>\n\n"
                + "   event <description> /from <start date> <start time> /to <end date> <end time>\n\n"
                + "   list\n\n"
                + "   mark <task number>\n\n"
                + "   unmark <task number>\n\n"
                + "   delete <task number a> <optional task number b> <optional task number c> ...\n\n"
                + "   find <keyword in description>\n\n"
                + "   bye";
        dialogContainer.getChildren().add(DialogBox.getAtomDialog(responseStr));
    }

    /**
     * Handles the gui interactions of the response if user tries to delete multiple tasks.
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
        dialogContainer.getChildren().add(DialogBox.getAtomDialog(message));
    }

    /**
     * Handles the gui interactions of the response after system error occurs upon mass delete command execution.
     *
     * @param response The response of internal system error after mass delete command execution.
     */
    @Override
    public void handleResponse(MassDeleteSystemErrorCommandResponse response) {
        Exception exception = response.getException();
        String responseStr = "ERROR: " + exception.getMessage();
        responseStr += "Here's the full error stack trace: \n";
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        exception.printStackTrace(printWriter);
        responseStr += stringWriter.toString();
        responseStr += "\nNo tasks were deleted";
        dialogContainer.getChildren().add(DialogBox.getAtomDialog(responseStr));
    }

    /**
     * Handles the gui interactions of the response after user error occurs upon mass delete command execution.
     *
     * @param response The response of user error after mass delete command execution.
     */
    @Override
    public void handleResponse(MassDeleteUserErrorCommandResponse response) {
        String responseStr = "ERROR: " + response.getException().getMessage();
        responseStr += "\nNo tasks were deleted";
        dialogContainer.getChildren().add(DialogBox.getAtomDialog(responseStr));
    }
}
