public interface CommandHandler {
    public void handle(ToDoCommand command);

    public void handle(DeadlineCommand command);

    public void handle(EventCommand command);

    public void handle(ListCommand command);

    public void handle(ByeCommand command);

    public void handle(DeleteCommand command);

    public void handle(MarkCommand command);

    public void handle(UnmarkCommand command);
}