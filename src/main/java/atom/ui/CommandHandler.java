package atom.ui;

import atom.command.ByeCommand;
import atom.command.DeadlineCommand;
import atom.command.DeleteCommand;
import atom.command.EventCommand;
import atom.command.FindCommand;
import atom.command.ListCommand;
import atom.command.MarkCommand;
import atom.command.ToDoCommand;
import atom.command.UnmarkCommand;

public interface CommandHandler {
    public void handle(ToDoCommand command);

    public void handle(DeadlineCommand command);

    public void handle(EventCommand command);

    public void handle(ListCommand command);

    public void handle(ByeCommand command);

    public void handle(DeleteCommand command);

    public void handle(MarkCommand command);

    public void handle(UnmarkCommand command);

    public void handle(FindCommand command);
}