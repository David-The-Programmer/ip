package atom.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import atom.task.Deadline;
import atom.task.Event;
import atom.task.ToDo;

class TaskSerialiserTest {

    private TaskSerialiser serialiser;
    private final DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HHmm");

    @BeforeEach
    void setUp() {
        serialiser = new TaskSerialiser();
    }

    @Test
    @DisplayName("ToDo Standard Case: Incomplete status")
    void testToDoSerialisationIncompleteStatus() {
        ToDo todo = new ToDo("buy milk");
        serialiser.serialise(todo);

        assertEquals("T|8|buy milk|0", serialiser.getSerialisedTask());
    }

    @Test
    @DisplayName("ToDo Standard Case: Complete status")
    void testToDoSerialisationCompleteStatus() {
        ToDo todo = new ToDo("read book");
        todo.markAsComplete();
        serialiser.serialise(todo);

        assertEquals("T|9|read book|1", serialiser.getSerialisedTask());
    }

    @Test
    @DisplayName("ToDo Edge Case: Description contains pipe delimiter")
    void testToDoSerialisationDescWithPipes() {
        ToDo todo = new ToDo("Clean | drain");
        serialiser.serialise(todo);

        assertEquals("T|13|Clean | drain|0", serialiser.getSerialisedTask());
    }

    @Test
    @DisplayName("Deadline Standard Case: Incomplete status")
    void testDeadlineSerialisationIncompleteStatus() {
        LocalDateTime by = LocalDateTime.parse("2026-10-05 1830", inputFormatter);
        Deadline deadline = new Deadline("submit assignment", by);

        serialiser.serialise(deadline);

        String expected = "D|17|submit assignment|0|2026-10-05T18:30:00";
        assertEquals(expected, serialiser.getSerialisedTask());
    }

    @Test
    @DisplayName("Deadline Standard Case: Complete status")
    void testDeadlineSerialisationCompleteStatus() {
        LocalDateTime by = LocalDateTime.parse("2026-01-01 1200", inputFormatter);
        Deadline deadline = new Deadline("new year resolution", by);
        deadline.markAsComplete();

        serialiser.serialise(deadline);

        assertEquals("D|19|new year resolution|1|2026-01-01T12:00:00", serialiser.getSerialisedTask());
    }

    @Test
    @DisplayName("Deadline Edge Case: Description contains pipe delimiter")
    void testDeadlineSerialisationDescWithPipes() {
        LocalDateTime by = LocalDateTime.parse("2026-01-01 1200", inputFormatter);
        Deadline deadline = new Deadline("Clean | drain", by);
        serialiser.serialise(deadline);

        assertEquals("D|13|Clean | drain|0|2026-01-01T12:00:00", serialiser.getSerialisedTask());
    }

    @Test
    @DisplayName("Deadline Edge Case: Leap year date (Feb 29)")
    void testDeadlineSerialisationLeapYear() {
        LocalDateTime leapDay = LocalDateTime.parse("2024-02-29 2359", inputFormatter);
        Deadline deadline = new Deadline("Leap task", leapDay);

        serialiser.serialise(deadline);

        assertTrue(serialiser.getSerialisedTask().endsWith("2024-02-29T23:59:00"));
    }

    @Test
    @DisplayName("Event Standard Case: Incomplete status")
    void testEventSerialisationIncompleteStatus() {
        LocalDateTime start = LocalDateTime.parse("2026-05-20 1400", inputFormatter);
        LocalDateTime end = LocalDateTime.parse("2026-05-20 1600", inputFormatter);
        Event event = new Event("Team Meeting", start, end);

        serialiser.serialise(event);

        String expected = "E|12|Team Meeting|0|2026-05-20T14:00:00|2026-05-20T16:00:00";
        assertEquals(expected, serialiser.getSerialisedTask());
    }

    @Test
    @DisplayName("Event Standard Case: Complete status")
    void testEventSerialisationCompleteStatus() {
        LocalDateTime start = LocalDateTime.parse("2026-05-20 1400", inputFormatter);
        LocalDateTime end = LocalDateTime.parse("2026-05-20 1600", inputFormatter);
        Event event = new Event("Team Meeting", start, end);
        event.markAsComplete();

        serialiser.serialise(event);

        String expected = "E|12|Team Meeting|1|2026-05-20T14:00:00|2026-05-20T16:00:00";
        assertEquals(expected, serialiser.getSerialisedTask());
    }

    @Test
    @DisplayName("Event Edge Case: Event spanning across years")
    void testEventSerialisationAcrossYears() {
        LocalDateTime start = LocalDateTime.parse("2025-12-31 2300", inputFormatter);
        LocalDateTime end = LocalDateTime.parse("2026-01-01 0100", inputFormatter);
        Event event = new Event("NYE Party", start, end);

        serialiser.serialise(event);

        String expected = "E|9|NYE Party|0|2025-12-31T23:00:00|2026-01-01T01:00:00";
        assertEquals(expected, serialiser.getSerialisedTask());
    }

    @Test
    @DisplayName("Robustness: Reusing the same serialiser instance updates the state")
    void testReuseSerialiser() {
        ToDo todo = new ToDo("Task 1");
        serialiser.serialise(todo);
        assertEquals("T|6|Task 1|0", serialiser.getSerialisedTask());

        LocalDateTime by = LocalDateTime.parse("2026-01-01 1000", inputFormatter);
        Deadline deadline = new Deadline("Task 2", by);
        serialiser.serialise(deadline);
        assertEquals("D|6|Task 2|0|2026-01-01T10:00:00", serialiser.getSerialisedTask());
    }
}
