package controller.events;

import controller.events.handlers.*;

import java.util.HashMap;
import java.util.Map;

/*
 * This class is used to register all the event handlers
 * and return the appropriate event handler based on the event type
 */
public class EventHandlerRegistry {
    private static final Map<String, EventHandler> eventHandlerMap = new HashMap<>();

    static {
        eventHandlerMap.put("SignUp", new SignUpEventHandler());
        eventHandlerMap.put("Login", new LoginEventHandler());
        eventHandlerMap.put("GetGroupChatsAuthUser", new GetGroupChatsAuthUserEventHandler());
        eventHandlerMap.put("GetFriendRequestsAuthUser", new GetFriendRequestsAuthUserEventHandler());
        eventHandlerMap.put("DeleteAccount", new DeleteAccountEventHandler());
        eventHandlerMap.put("RenameUsername", new RenameUsernameEventHandler());
        eventHandlerMap.put("RenameEmail", new RenameEmailEventHandler());
        eventHandlerMap.put("ChangePassword", new ChangePasswordEventHandler());
        eventHandlerMap.put("FriendRequest", new FriendRequestEventHandler());
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
