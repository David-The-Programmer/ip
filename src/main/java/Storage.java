import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Storage {
    private Path filePath;
    private TaskSerialiser taskSerialiser;

    public Storage(String saveFilePath) throws StorageException {
        this.taskSerialiser = new TaskSerialiser();
        try {
            this.filePath = Paths.get(saveFilePath);
        } catch (InvalidPathException exception) {
            String message = "'saveFilePath' parameter provided: " + saveFilePath
                    + " is an invalid file path";
            String remedy = "ensure that 'saveFilePath' is a proper path";
            throw new StorageException(message, exception, remedy);
        }
    }

    public void saveTasks(List<Task> tasks) throws StorageException {
        String serialisedTaskList = "";
        for (Task task : tasks) {
            task.acceptSerialiser(taskSerialiser);
            serialisedTaskList += taskSerialiser.getSerialisedTask() + "\n";
        }
        Path saveDirectory = filePath.getParent();
        if (saveDirectory == null) {
            saveDirectory = Paths.get("");
        }
        try {
            if (Files.notExists(saveDirectory)) {
                Files.createDirectories(saveDirectory);
            }
            if (Files.notExists(filePath)) {
                Files.createFile(filePath);
            }
            Files.writeString(filePath, serialisedTaskList);

        } catch (UnsupportedOperationException | IOException | SecurityException exception) {
            String message = "Unable to write tasks to file";
            throw new StorageException(message, exception, "");
        }
    }

    // public
    // List<Task>
    // readTasks()
    // {

    // }

}
