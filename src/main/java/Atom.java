import java.util.Scanner;

public class Atom {
    public static void main(String[] args) {
        TaskSerialiser taskSerialiser = new TaskSerialiser();
        TaskDeserialiser taskDeserialiser = new TaskDeserialiser();
        Storage storage = Storage.init("./data/atom.txt", taskSerialiser, taskDeserialiser);
        TaskService taskService = new TaskService(storage.readTasks());
        Scanner scanner = new Scanner(System.in);
        Parser parser = new Parser();
        UserInterface userInterface = new UserInterface(parser, taskService, storage, scanner);
        userInterface.run();
    }
}
