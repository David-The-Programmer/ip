package atom.ui.gui;

import java.util.List;
import java.io.PrintWriter;
import java.io.StringWriter;
import atom.command.ByeCommandResponse;
import atom.command.CommandResponse;
import atom.command.DeadlineCommandResponse;
import atom.command.DeleteCommandResponse;
import atom.command.EventCommandResponse;
import atom.command.FindCommandResponse;
import atom.command.ListCommandResponse;
import atom.command.MarkCommandResponse;
import atom.command.SystemErrorCommandResponse;
import atom.command.ToDoCommandResponse;
import atom.command.UnknownCommandResponse;
import atom.command.UnmarkCommandResponse;
import atom.command.UserErrorCommandResponse;
import atom.controller.Controller;
import atom.task.Task;
import atom.ui.CommandResponseHandler;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GraphicalUserInterface extends Application implements CommandResponseHandler {
    private static Controller controller;
    private ScrollPane scrollPane;
    private VBox dialogContainer;
    private TextField userInput;
    private Button sendButton;
    private Scene scene;

    public static void setController(Controller controller) {
        GraphicalUserInterface.controller = controller;
    }

    @Override
    public void start(Stage stage) throws Exception {
        scrollPane = new ScrollPane();
        dialogContainer = new VBox();
        scrollPane.setContent(dialogContainer);

        userInput = new TextField();
        sendButton = new Button("Send");

        AnchorPane mainLayout = new AnchorPane();
        mainLayout.getChildren().addAll(scrollPane, userInput, sendButton);

        scene = new Scene(mainLayout);


        stage.setTitle("Duke");
        stage.setResizable(false);
        stage.setMinHeight(600.0);
        stage.setMinWidth(400.0);

        mainLayout.setPrefSize(400.0, 600.0);

        scrollPane.setPrefSize(385, 535);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.ALWAYS);

        scrollPane.setVvalue(1.0);
        scrollPane.setFitToWidth(true);

        dialogContainer.setPrefHeight(Region.USE_COMPUTED_SIZE);

        userInput.setPrefWidth(325.0);

        sendButton.setPrefWidth(55.0);

        AnchorPane.setTopAnchor(scrollPane, 1.0);

        AnchorPane.setBottomAnchor(sendButton, 1.0);
        AnchorPane.setRightAnchor(sendButton, 1.0);

        AnchorPane.setLeftAnchor(userInput, 1.0);
        AnchorPane.setBottomAnchor(userInput, 1.0);

        stage.setScene(scene);

        sendButton.setOnMouseClicked((event) -> {
            handleUserInput();
        });
        userInput.setOnAction((event) -> {
            handleUserInput();
        });

        stage.show();
    }

    private void handleUserInput() {
        String userInputStr = userInput.getText();
        dialogContainer.getChildren().add(new DialogBox(userInput.getText()));
        CommandResponse response = controller.getResponse(userInputStr);
        response.acceptResponseHandler(this);
        userInput.clear();
    }

    @Override
    public void handleResponse(ToDoCommandResponse response) {
        String responseStr = "Noted. The following task has been added: \n";
        responseStr += response.getToDo() + "\n";
        responseStr += "So, in total, you have " + response.getNumRemainingTasks() + " task(s) remaining";
        dialogContainer.getChildren().add(new DialogBox(responseStr));
    }

    @Override
    public void handleResponse(DeadlineCommandResponse response) {
        String responseStr = "Noted. The following task has been added: \n";
        responseStr += response.getDeadline() + "\n";
        responseStr += "So, in total, you have " + response.getNumTasksRemaining() + " task(s) remaining";
        dialogContainer.getChildren().add(new DialogBox(responseStr));
    }

    @Override
    public void handleResponse(EventCommandResponse response) {
        String responseStr = "Noted. The following task has been added: \n";
        responseStr += response.getEvent() + "\n";
        responseStr += "So, in total, you have " + response.getNumTasksRemaining() + " task(s) remaining";
        dialogContainer.getChildren().add(new DialogBox(responseStr));
    }

    @Override
    public void handleResponse(ListCommandResponse response) {
        List<Task> tasks = response.getTasks();
        String responseStr = "";
        for (int i = 1; i <= tasks.size(); i++) {
            responseStr += i + ". " + tasks.get(i - 1) + "\n";
        }
        dialogContainer.getChildren().add(new DialogBox(responseStr));
    }

    @Override
    public void handleResponse(ByeCommandResponse response) {
        String responseStr = "Goodbye! Exiting...";
        dialogContainer.getChildren().add(new DialogBox(responseStr));
    }

    @Override
    public void handleResponse(DeleteCommandResponse response) {
        String responseStr = "Understood. This task will be deleted: \n";
        responseStr += response.getTask();
        dialogContainer.getChildren().add(new DialogBox(responseStr));
    }

    @Override
    public void handleResponse(MarkCommandResponse response) {
        String responseStr = "Great Job! This task is marked as complete: \n";
        responseStr += response.getTask();
        dialogContainer.getChildren().add(new DialogBox(responseStr));
    }

    @Override
    public void handleResponse(UnmarkCommandResponse response) {
        String responseStr = "Alright. This task is marked as incomplete: \n";
        responseStr += response.getTask();
        dialogContainer.getChildren().add(new DialogBox(responseStr));
    }

    @Override
    public void handleResponse(FindCommandResponse response) {
        List<Task> matchingTasks = response.getTasks();
        if (matchingTasks.isEmpty()) {
            String responseStr = "There are no matching tasks in your list, please try again.";
            dialogContainer.getChildren().add(new DialogBox(responseStr));
            return;
        }
        String responseStr = "The following tasks match the keyword: " + response.getKeyword() + "\n";
        for (int i = 1; i <= matchingTasks.size(); i++) {
            responseStr += i + "." + matchingTasks.get(i - 1) + "\n";
        }
        dialogContainer.getChildren().add(new DialogBox(responseStr));

    }

    @Override
    public void handleResponse(UserErrorCommandResponse response) {
        String responseStr = "ERROR: " + response.getException().getMessage();
        dialogContainer.getChildren().add(new DialogBox(responseStr));
    }

    @Override
    public void handleResponse(SystemErrorCommandResponse response) {
        Exception exception = response.getException();
        String responseStr = "ERROR: " + exception.getMessage();
        responseStr += "Here's the full error stack trace: \n";
        StringWriter stringWriter = new StringWriter();
        PrintWriter printWriter = new PrintWriter(stringWriter);
        exception.printStackTrace(printWriter);
        responseStr += stringWriter.toString();
        dialogContainer.getChildren().add(new DialogBox(responseStr));
    }

    @Override
    public void handleResponse(UnknownCommandResponse response) {
        String userInput = response.getUserInput();
        String responseStr = "'" + userInput + "' command not found (unknown)\n";
        responseStr += "The following are the only valid commands:\n\n" + "   todo <description>\n\n"
            + "   deadline <description> /by <datetime of deadline>\n\n"
            + "   event <description> /from <datetime that event starts> /to <datetime that event ends>\n\n"
            + "   list\n\n" + "   mark <task number shown after entering the list command>\n\n"
            + "   unmark <task number shown after entering the list command>\n\n"
            + "   find <keyword in description>\n\n"
            + "   bye";
        dialogContainer.getChildren().add(new DialogBox(responseStr));
    }

}
