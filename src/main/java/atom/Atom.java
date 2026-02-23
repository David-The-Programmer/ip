package atom;

import java.util.Scanner;

import atom.controller.Controller;
import atom.parser.Parser;
import atom.storage.Storage;
import atom.storage.TaskDeserialiser;
import atom.storage.TaskSerialiser;
import atom.task.TaskService;
import atom.ui.cli.CommandLineInterface;
import atom.ui.gui.GraphicalUserInterface;
import javafx.application.Application;

/**
 * Launcher for atom application.
 */
public class Atom {

    /**
     * Initialises and launches the atom application.
     *
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        TaskSerialiser taskSerialiser = new TaskSerialiser();
        TaskDeserialiser taskDeserialiser = new TaskDeserialiser();
        Storage storage = Storage.init("./data/atom.txt", taskSerialiser, taskDeserialiser);
        TaskService taskService = new TaskService(storage.readTasks());
        Scanner scanner = new Scanner(System.in);
        Parser parser = new Parser();
        Controller controller = new Controller(parser, taskService, storage);
        if (args.length > 0 && args[0].equals("--cli")) {
            CommandLineInterface commandLineInterface = new CommandLineInterface(controller, scanner);
            commandLineInterface.run();
        } else {
            GraphicalUserInterface.setAtomController(controller);
            Application.launch(GraphicalUserInterface.class, args);
        }
    }
}
