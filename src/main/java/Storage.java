import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.InvalidPathException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private Path saveFilePath;
    private Serialiser taskSerialiser;
    private Deserialiser taskDeserialiser;

    public Storage(String saveFilePath, TaskSerialiser taskSerialiser, TaskDeserialiser taskDeserialiser)
            throws StorageException {
        this.taskSerialiser = taskSerialiser;
        this.taskDeserialiser = taskDeserialiser;
        try {
            this.saveFilePath = Paths.get(saveFilePath);
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
        Path saveDirectory = saveFilePath.getParent();
        if (saveDirectory == null) {
            saveDirectory = Paths.get("");
        }
        try {
            if (Files.notExists(saveDirectory)) {
                Files.createDirectories(saveDirectory);
            }
            if (Files.notExists(saveFilePath)) {
                Files.createFile(saveFilePath);
            }
            Files.writeString(saveFilePath, serialisedTaskList);

        } catch (UnsupportedOperationException | IOException | SecurityException exception) {
            String message = "Unable to write tasks to file";
            throw new StorageException(message, exception, "");
        }
    }

    public List<Task> readTasks() throws StorageException {
        Path saveDirectory = saveFilePath.getParent();
        if (saveDirectory == null) {
            saveDirectory = Paths.get("");
        }
        try {
            if (Files.notExists(saveDirectory)) {
                Files.createDirectories(saveDirectory);
            }
            if (Files.notExists(saveFilePath)) {
                Files.createFile(saveFilePath);
            }
        } catch (UnsupportedOperationException | IOException | SecurityException exception) {
            String message = "Unable to create file at saveFilePath: ";
            message += saveFilePath;
            throw new StorageException(message, exception, "");
        }
        List<Task> tasks = new ArrayList<>();
        List<String> serialisedTasks = null;

        try {
            serialisedTasks = Files.readAllLines(saveFilePath);
        } catch (IOException exception) {
            String message = "Unable to read file from saveFilePath: ";
            message += saveFilePath;
            throw new StorageException(message, exception, "");
        }

        for (String serialisedTask : serialisedTasks) {
            try {
                taskDeserialiser.deserialise(serialisedTask);
                tasks.add(taskDeserialiser.getDeserialisedTask());
            } catch (DeserialiserException exception) {
                String message = "Unable to deserialise task string: ";
                message += "'" + serialisedTask + "'";
                throw new StorageException(message, exception, "");
            }
        }

        return tasks;

    }

}
