package atom.command;

import atom.ui.CommandHandler;

/**
 * Represents a command to search for tasks that contain a specific keyword in their description.
 */
public class FindCommand extends Command {
    private String keyword;

    /**
     * Initializes a new FindCommand with the specified search keyword.
     * @param keyword The string to search for within task descriptions.
     */
    public FindCommand(String keyword) {
        super();
        this.keyword = keyword;
    }

    /**
     * Retrieves the keyword used for the search.
     * @return The search keyword string.
     */
    public String getKeyword() {
        return this.keyword;
    }

    /**
     * Accepts a command handler to execute the search logic.
     * @param handler The command handler responsible for processing this command.
     */
    public void acceptHandler(CommandHandler handler) {
        handler.handle(this);
    }
}
