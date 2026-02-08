package atom.ui;

import atom.command.ByeCommandResponse;
import atom.command.DeadlineCommandResponse;
import atom.command.DeleteCommandResponse;
import atom.command.EventCommandResponse;
import atom.command.FindCommandResponse;
import atom.command.ListCommandResponse;
import atom.command.MarkCommandResponse;
import atom.command.ToDoCommandResponse;
import atom.command.UnmarkCommandResponse;
import atom.command.UserErrorCommandResponse;
import atom.command.SystemErrorCommandResponse;

/**
 * Interface for handling various types of application commands.
 */
public interface CommandResponseHandler {
    /**
     * Handles the response after execution of a todo command.
     * 
     * @param response The response of a todo command.
     */
    public void handleResponse(ToDoCommandResponse response);

    /**
     * Handles the response after execution of a deadline command.
     * 
     * @param response The response of a Deadline command.
     */
    public void handleResponse(DeadlineCommandResponse response);

    /**
     * Handles the response after execution of a event command.
     * 
     * @param response The response of a Event command.
     */
    public void handleResponse(EventCommandResponse response);

    /**
     * Handles the response after execution of a list command.
     * 
     * @param response The response of a List command.
     */
    public void handleResponse(ListCommandResponse response);

    /**
     * Handles the response after execution of a bye command.
     * 
     * @param response The response of a Bye command.
     */
    public void handleResponse(ByeCommandResponse response);

    /**
     * Handles the response after execution of a delete command.
     * 
     * @param response The response of a delete command.
     */
    public void handleResponse(DeleteCommandResponse response);

    /**
     * Handles the response after execution of a mark command.
     * 
     * @param response The response of a mark command.
     */
    public void handleResponse(MarkCommandResponse response);
    
    /**
     * Handles the response after execution of a unmark command.
     * 
     * @param response The response of a unmark command.
     */
    public void handleResponse(UnmarkCommandResponse response);
    
    /**
     * Handles the response after execution of a find command.
     * 
     * @param response The response of a find command.
     */
    public void handleResponse(FindCommandResponse response);

    /**
     * Handles the response after user caused error occurs upon execution of any command
     * 
     * @param response The response of user error after command execution
     */
    public void handleResponse(UserErrorCommandResponse response);

    /**
     * Handles the response after internal system error occurs upon execution of any command
     * 
     * @param response The response of internal system error after command execution
     */
    public void handleResponse(SystemErrorCommandResponse response);
}
