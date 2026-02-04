import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import atom.task.Deadline;
import atom.task.Event;
import atom.task.ToDo;
import atom.task.Task;
import atom.storage.TaskDeserialiser;
import atom.storage.DeserialiserException;

import java.time.LocalDateTime;

class TaskDeserialiserTest {

    private TaskDeserialiser deserialiser;

    @BeforeEach
    void setUp() {
        deserialiser = new TaskDeserialiser();
    }

    @Test
    @DisplayName("ToDo: Should deserialise valid incomplete task")
    void testDeserialiseToDoIncomplete() throws DeserialiserException {
        deserialiser.deserialise("T|8|buy milk|0");
        Task task = deserialiser.getDeserialisedTask();

        assertTrue(task instanceof ToDo);
        assertEquals("buy milk", task.getDescription());
        assertFalse(task.isCompleted());
    }

    @Test
    @DisplayName("ToDo: Should deserialise valid complete task")
    void testDeserialiseToDoComplete() throws DeserialiserException {
        deserialiser.deserialise("T|8|buy milk|1");
        Task task = deserialiser.getDeserialisedTask();

        assertTrue(task instanceof ToDo);
        assertEquals("buy milk", task.getDescription());
        assertTrue(task.isCompleted());
    }

    @Test
    @DisplayName("Deadline: Should deserialise valid incomplete deadline")
    void testDeserialiseDeadlineIncomplete() throws DeserialiserException {
        String dateTimeStr = "2026-02-04T18:00:00";
        deserialiser.deserialise("D|12|submit essay|0|" + dateTimeStr);
        Task task = deserialiser.getDeserialisedTask();

        assertTrue(task instanceof Deadline);
        assertEquals("submit essay", task.getDescription());
        assertFalse(task.isCompleted());
        assertEquals(LocalDateTime.parse(dateTimeStr), ((Deadline) task).getDateTime());
    }

    @Test
    @DisplayName("Deadline: Should deserialise valid complete deadline")
    void testDeserialiseDeadlineComplete() throws DeserialiserException {
        String dateTimeStr = "2026-02-04T18:00";
        deserialiser.deserialise("D|12|submit essay|1|" + dateTimeStr);
        Task task = deserialiser.getDeserialisedTask();

        assertTrue(task instanceof Deadline);
        assertEquals("submit essay", task.getDescription());
        assertTrue(task.isCompleted());
        assertEquals(LocalDateTime.parse(dateTimeStr), ((Deadline) task).getDateTime());
    }

    @Test
    @DisplayName("Event: Should deserialise valid incomplete event")
    void testDeserialiseEventIncomplete() throws DeserialiserException {
        String startDateTimeStr = "2026-05-20T19:00";
        String endDateTimeStr = "2026-05-20T23:00";
        deserialiser.deserialise("E|7|concert|0|" + startDateTimeStr + "|" + endDateTimeStr);
        Task task = deserialiser.getDeserialisedTask();

        assertTrue(task instanceof Event);
        assertFalse(task.isCompleted());
        Event event = (Event) task;
        assertEquals(startDateTimeStr, event.getStartDateTime().toString());
        assertEquals(endDateTimeStr, event.getEndDateTime().toString());
    }

    @Test
    @DisplayName("Event: Should deserialise valid complete event")
    void testDeserialiseEventComplete() throws DeserialiserException {
        String startDateTimeStr = "2026-05-20T19:00";
        String endDateTimeStr = "2026-05-20T23:00";
        deserialiser.deserialise("E|7|concert|1|" + startDateTimeStr + "|" + endDateTimeStr);
        Task task = deserialiser.getDeserialisedTask();

        assertTrue(task instanceof Event);
        assertTrue(task.isCompleted());
        Event event = (Event) task;
        assertEquals(startDateTimeStr, event.getStartDateTime().toString());
        assertEquals(endDateTimeStr, event.getEndDateTime().toString());
    }

    @Test
    @DisplayName("Edge Case: Description containing pipes")
    void testDeserialiseWithPipesInDescription() throws DeserialiserException {
        deserialiser.deserialise("T|11|Task|with|0|0");
        Task task = deserialiser.getDeserialisedTask();

        assertEquals("Task|with|0", task.getDescription());
    }

    @Test
    @DisplayName("Error: Invalid task type character")
    void testInvalidTaskType() {
        assertThrows(DeserialiserException.class, () -> {
            deserialiser.deserialise("X|4|test|0");
        });
    }

    @Test
    @DisplayName("Error: Non-integer description length")
    void testInvalidLengthFormat() {
        assertThrows(DeserialiserException.class, () -> {
            deserialiser.deserialise("T|NaN|test|0");
        });
    }

    @Test
    @DisplayName("Error: Invalid completion status (not 0 or 1)")
    void testInvalidCompletionStatus() {
        assertThrows(DeserialiserException.class, () -> {
            deserialiser.deserialise("T|4|test|9");
        });
    }

    @Test
    @DisplayName("Error: Malformed Date format")
    void testInvalidDateFormat() {
        assertThrows(DeserialiserException.class, () -> {
            deserialiser.deserialise("D|4|test|0|04-02-2026 18:00");
        });
    }

    @Test
    @DisplayName("Error: Missing completion status")
    void testMissingSegments() {
        assertThrows(DeserialiserException.class, () -> {
            deserialiser.deserialise("T|4|test");
        });
    }
}
