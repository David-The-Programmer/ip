package atom.ui;

import atom.command.ByeCommandResponse;
import atom.command.DeadlineCommandResponse;
import atom.command.DeleteCommandResponse;
import atom.command.EventCommandResponse;
import atom.command.FindCommandResponse;
import atom.command.ListCommandResponse;
import atom.command.MarkCommandResponse;
import atom.command.SystemErrorCommandResponse;
import atom.command.ToDoCommandResponse;
import atom.command.UnmarkCommandResponse;
import atom.command.UserErrorCommandResponse;
import atom.controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Control;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class GraphicalUserInterface extends Application implements CommandResponseHandler {
    private static Controller controller;

    public static void setController(Controller controller) {
        GraphicalUserInterface.controller = controller;
    }

    @Override
    public void start(Stage stage) throws Exception {
        Label helloWorld = new Label("Hello World!");
        Scene scene = new Scene(helloWorld);

        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void handleResponse(ToDoCommandResponse response) {

    }

    @Override
    public void handleResponse(DeadlineCommandResponse response) {

    }

    @Override
    public void handleResponse(EventCommandResponse response) {

    }

    @Override
    public void handleResponse(ListCommandResponse response) {

    }

    @Override
    public void handleResponse(ByeCommandResponse response) {

    }

    @Override
    public void handleResponse(DeleteCommandResponse response) {

    }

    @Override
    public void handleResponse(MarkCommandResponse response) {

    }

    @Override
    public void handleResponse(UnmarkCommandResponse response) {

    }

    @Override
    public void handleResponse(FindCommandResponse response) {

    }

    @Override
    public void handleResponse(UserErrorCommandResponse response) {

    }

    @Override
    public void handleResponse(SystemErrorCommandResponse response) {

    }

}
