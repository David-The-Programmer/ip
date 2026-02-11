package atom.command;

import atom.task.Event;
import atom.ui.CommandResponseHandler;

/**
 * Represents the response of a event command execution.
 */
public class EventCommandResponse implements CommandResponse {
    private Event event;
    private int numTasksRemaining;

    public EventCommandResponse(Event event, int numTasksRemaining) {
        this.event = event;
        this.numTasksRemaining = numTasksRemaining;
    }

    public int getNumTasksRemaining() {
        return numTasksRemaining;
    }

    public Event getEvent() {
        return event;
    }

    /**
     * Accepts a command response handler to process the event command response
     * @param handler The command response handler instance.
     */
    @Override
    public void acceptResponseHandler(CommandResponseHandler handler) {
        handler.handleResponse(this);
    }
}
