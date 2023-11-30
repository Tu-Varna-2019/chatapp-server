package controller.events;

import java.util.HashMap;
import java.util.Map;

import controller.events.handlers.SignUpEventHandler;

/*
 * This class is used to register all the event handlers
 * and return the appropriate event handler based on the event type
 */
public class EventHandlerRegistry {
    private static final Map<String, EventHandler> eventHandlerMap = new HashMap<>();

    static {
        eventHandlerMap.put("SignUp", new SignUpEventHandler());
    }

    /*
     * This method returns the appropriate event handler based on the event type
     * 
     * @param eventType (SignUp,Login...) coming from the client
     * 
     * @return @Overridden handleEvent method
     */
    public static EventHandler getEventHandler(String eventType) {
        return eventHandlerMap.get(eventType);
    }
}
