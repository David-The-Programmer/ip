import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Storage {
    private Path saveFilePath;
    private Serialiser taskSerialiser;
    private Deserialiser taskDeserialiser;

    private Storage(Path saveFilePath, TaskSerialiser taskSerialiser, TaskDeserialiser taskDeserialiser) {
        this.saveFilePath = saveFilePath;
        this.taskSerialiser = taskSerialiser;
        this.taskDeserialiser = taskDeserialiser;
    }

    // TODO: Need to move all remedy of exceptions into command controller/ui layer
    public static Storage init(String saveFilePathStr, TaskSerialiser serialiser,
            TaskDeserialiser deserialiser) throws StorageInitException {
        Path saveFilePath = Paths.get(saveFilePathStr);
        Path saveDirectory = saveFilePath.getParent();
        try {
            if (saveDirectory != null) {
                Files.createDirectories(saveDirectory);
            }
            if (Files.notExists(saveFilePath)) {
                Files.createFile(saveFilePath);
            }
        } catch (IOException e) {
            String message = "Unable to init storage";
            throw new StorageInitException(message, e);
        }
        return new Storage(saveFilePath, serialiser, deserialiser);
    }

    public void saveTasks(List<Task> tasks) throws StorageAccessDeniedException, StorageWriteException {
        String serialisedTaskList = "";
        for (Task task : tasks) {
            task.acceptSerialiser(taskSerialiser);
            serialisedTaskList += taskSerialiser.getSerialisedTask() + "\n";
        }
        try {
            Files.writeString(saveFilePath, serialisedTaskList);
        } catch (AccessDeniedException e) {
            String message = "Unable to access save file";
            String remedy = "Please check if " + saveFilePath.toString() + " is read-only,";
            remedy += " open in another program, or in a protected folder.";
            throw new StorageAccessDeniedException(message, e, remedy);
        } catch (IOException e) {
            String message = "Unable to write tasks storage";
            throw new StorageWriteException(message, e);
        }
    }

    public List<Task> readTasks() throws StorageReadException {
        List<Task> tasks = new ArrayList<>();
        List<String> serialisedTasks = null;

        try {
            serialisedTasks = Files.readAllLines(saveFilePath);
        } catch (IOException exception) {
            String message = "Unable to read file from saveFilePath: ";
            message += saveFilePath;
            throw new StorageReadException(message, exception);
        }

        for (String serialisedTask : serialisedTasks) {
            try {
                taskDeserialiser.deserialise(serialisedTask);
                tasks.add(taskDeserialiser.getDeserialisedTask());
            } catch (DeserialiserException exception) {
                String message = "Unable to deserialise task string: ";
                message += "'" + serialisedTask + "'";
                throw new StorageReadException(message, exception);
            }
        }

        return tasks;

    }

}
