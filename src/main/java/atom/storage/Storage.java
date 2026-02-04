package atom.storage;

import atom.task.Task;
import java.io.IOException;
import java.nio.file.AccessDeniedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Handles the loading and saving of task data to the local file system.
 */
public class Storage {
    private Path saveFilePath;
    private Serialiser taskSerialiser;
    private Deserialiser taskDeserialiser;

    /**
     * Private constructor for Storage.
     * @param saveFilePath Path to the save file.
     * @param taskSerialiser Serialiser for converting tasks to strings.
     * @param taskDeserialiser Deserialiser for converting strings to tasks.
     */
    private Storage(Path saveFilePath, TaskSerialiser taskSerialiser, TaskDeserialiser taskDeserialiser) {
        this.saveFilePath = saveFilePath;
        this.taskSerialiser = taskSerialiser;
        this.taskDeserialiser = taskDeserialiser;
    }

    /**
     * Initializes the storage by creating directories and the save file if they do not exist.
     * @param saveFilePathStr String representation of the file path.
     * @param serialiser The serialiser to be used.
     * @param deserialiser The deserialiser to be used.
     * @return A new instance of Storage.
     * @throws StorageInitException If directory or file creation fails.
     */
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

    /**
     * Persists the list of tasks to the save file.
     * @param tasks The list of tasks to save.
     * @throws StorageAccessDeniedException If the application lacks file permissions.
     * @throws StorageWriteException If an I/O error occurs during writing.
     */
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
            throw new StorageAccessDeniedException(message, e, saveFilePath.toString());
        } catch (IOException e) {
            String message = "Unable to write tasks storage";
            throw new StorageWriteException(message, e);
        }
    }

    /**
     * Reads and parses tasks from the save file.
     * @return A list of tasks retrieved from storage.
     * @throws StorageReadException If the file cannot be read or deserialisation fails.
     */
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
