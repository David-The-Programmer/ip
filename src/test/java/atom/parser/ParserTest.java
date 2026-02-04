package atom.parser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import atom.command.ByeCommand;
import atom.command.Command;
import atom.command.DeadlineCommand;
import atom.command.DeleteCommand;
import atom.command.EventCommand;
import atom.command.ListCommand;
import atom.command.MarkCommand;
import atom.command.ToDoCommand;

class ParserTest {

    private Parser parser;

    @BeforeEach
    void setUp() {
        parser = new Parser();
    }

    @Test
    @DisplayName("Should parse 'bye' and 'list' commands correctly")
    void testParseByeAndListCommands() throws Exception {
        assertTrue(parser.parse("bye") instanceof ByeCommand);
        assertTrue(parser.parse("list") instanceof ListCommand);
    }

    @Test
    @DisplayName("Should parse valid todo command with description")
    void testParseValidTodoCommand() throws Exception {
        Command result = parser.parse("todo read book");
        assertTrue(result instanceof ToDoCommand);
        assertEquals("read book", ((ToDoCommand) result).getDescription());
    }

    @Test
    @DisplayName("Should throw exception for todo missing a description")
    void testTodoMissingDescription() {
        assertThrows(InvalidTodoCommandException.class, () -> parser.parse("todo"));
        assertThrows(InvalidTodoCommandException.class, () -> parser.parse("todo   "));
    }

    @Test
    @DisplayName("Should parse valid deadline command with description and date")
    void testParseValidDeadlineCommand() throws Exception {
        Command result = parser.parse("deadline return book /by 2026-12-01 1800");
        assertTrue(result instanceof DeadlineCommand);
        DeadlineCommand d = (DeadlineCommand) result;
        assertEquals("return book", d.getDescription());
        assertEquals(LocalDateTime.parse("2026-12-01T18:00:00"), d.getDateTime());
    }

    @Test
    @DisplayName("Should throw exception for deadline missing /by delimiter")
    void testDeadlineMissingByDelimiter() {
        assertThrows(InvalidDeadlineCommandException.class, () -> parser.parse("deadline return book"));
    }

    @Test
    @DisplayName("Should throw exception for deadline missing description")
    void testDeadlineMissingDescription() {
        String input = "deadline /by 2026-12-01 1800";
        assertThrows(InvalidDeadlineCommandException.class, () -> parser.parse(input));
    }

    @Test
    @DisplayName("Should throw exception for deadline missing /by datetime content")
    void testDeadlineMissingByDateTimeContent() {
        assertThrows(InvalidEventCommandException.class, () -> parser.parse("deadline return book /by "));
    }

    @Test
    @DisplayName("Should parse valid event command with start and end times")
    void testParseValidEventCommand() throws Exception {
        Command result = parser.parse("event project meeting /from 2026-02-04 1400 /to 2026-02-04 1600");
        assertTrue(result instanceof EventCommand);
        EventCommand e = (EventCommand) result;
        assertEquals("project meeting", e.getDescription());
        assertEquals(LocalDateTime.parse("2026-02-04T14:00:00"), e.getStartDateTime());
        assertEquals(LocalDateTime.parse("2026-02-04T16:00:00"), e.getEndDateTime());
    }

    @Test
    @DisplayName("Should throw exception for event missing description")
    void testEventMissingDescription() {
        String input = "event /from 2026-02-04 1400 /to 2026-02-04 1600";
        assertThrows(InvalidEventCommandException.class, () -> parser.parse(input));
    }

    @Test
    @DisplayName("Should throw exception for event missing /from delimiter")
    void testEventMissingFromDelimiter() {
        String input = "event meeting /to 2026-02-04 1600";
        assertThrows(InvalidEventCommandException.class, () -> parser.parse(input));
    }

    @Test
    @DisplayName("Should throw exception for event missing /to delimiter")
    void testEventMissingToDelimiter() {
        String input = "event meeting /from 2026-02-04 1400";
        assertThrows(InvalidEventCommandException.class, () -> parser.parse(input));
    }

    @Test
    @DisplayName("Should throw exception for event missing /from datetime content")
    void testEventMissingFromDateTimeContent() {
        String input = "event meeting /from /to 2026-02-04 1600";
        assertThrows(InvalidEventCommandException.class, () -> parser.parse(input));
    }

    @Test
    @DisplayName("Should throw exception for event missing /to datetime content")
    void testEventMissingToDateTimeContent() {
        String input = "event meeting /from 2026-02-04 1400 /to ";
        assertThrows(InvalidEventCommandException.class, () -> parser.parse(input));
    }

    @Test
    @DisplayName("Should throw exception when event end date is before start date")
    void testEventEndDateBeforeStartDate() {
        String input = "event party /from 2026-02-04 2000 /to 2026-02-04 1800";
        assertThrows(InvalidEventCommandException.class, () -> parser.parse(input));
    }

    @Test
    @DisplayName("Should parse valid numeric commands for mark and delete")
    void testParseValidNumericCommands() throws Exception {
        Command mark = parser.parse("mark 1");
        assertTrue(mark instanceof MarkCommand);
        assertEquals(1, ((MarkCommand) mark).getTaskNumber());

        Command delete = parser.parse("delete 5");
        assertTrue(delete instanceof DeleteCommand);
        assertEquals(5, ((DeleteCommand) delete).getTaskNumber());
    }

    @Test
    @DisplayName("Should throw exception when mark or delete receive non-integer input")
    void testNonIntegerNumericInput() {
        assertThrows(InvalidMarkCommandException.class, () -> parser.parse("mark abc"));
        assertThrows(InvalidDeleteCommandException.class, () -> parser.parse("delete !!"));
    }

    @Test
    @DisplayName("Should handle leading and trailing whitespace in descriptions")
    void testHandleDescriptionWhitespace() throws Exception {
        Command result = parser.parse("todo    clean room   ");
        assertEquals("clean room", ((ToDoCommand) result).getDescription());
    }

    @Test
    @DisplayName("Should return null when given an unknown command")
    void testUnknownCommandReturnsNull() throws Exception {
        assertNull(parser.parse("hello world"));
    }
}
