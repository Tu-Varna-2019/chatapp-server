package controller.events;

import java.util.HashMap;
import java.util.Map;

import controller.events.handlers.groupchat.*;
import controller.events.handlers.friendrequest.*;
import controller.events.handlers.message.*;
import controller.events.handlers.user.*;

/*
 * This class is used to register all the event handlers
 * and return the appropriate event handler based on the event type
 */
public class EventHandlerRegistry {

    private static EventHandlerRegistry instance = null;
    private final Map<String, EventHandler> eventHandlerMap;

    private EventHandlerRegistry() {
        eventHandlerMap = new HashMap<>();
        // User
        eventHandlerMap.put("SignUp", new SignUpEventHandler());
        eventHandlerMap.put("Login", new LoginEventHandler());
        eventHandlerMap.put("DeleteAccount", new DeleteAccountEventHandler());
        eventHandlerMap.put("RenameUsername", new RenameUsernameEventHandler());
        eventHandlerMap.put("RenameEmail", new RenameEmailEventHandler());
        eventHandlerMap.put("ChangePassword", new ChangePasswordEventHandler());

        // GroupChat
        eventHandlerMap.put("GetGroupChats", new GetGroupChatsEventHandler());
        eventHandlerMap.put("CreateGroupChat", new CreateGroupChatEventHandler());
        eventHandlerMap.put("RemoveUserFromGroupChat", new RemoveUserFromGroupChatEventHandler());
        eventHandlerMap.put("AddUserToGroupChat", new AddUserToGroupChatEventHandler());
        eventHandlerMap.put("DeleteGroupChat", new DeleteGroupChatEventHandler());

        // Friend requests
        eventHandlerMap.put("GetFriendRequests", new GetFriendRequestsEventHandler());
        eventHandlerMap.put("SendFriendRequest", new SendFriendRequestEventHandler());
        eventHandlerMap.put("FriendRequestOperation", new FriendRequestOperation());
        eventHandlerMap.put("DeleteFriendRequest", new DeleteFriendRequestEventHandler());

        // Messages
        eventHandlerMap.put("GetMessages", new GetMessagesEventHandler());
        eventHandlerMap.put("SendMessage", new SendMessageEventHandler());
        eventHandlerMap.put("DeleteMessage", new DeleteMessageEventHandler());
    }

    public static EventHandlerRegistry getInstance() {
        if (instance == null) {
            instance = new EventHandlerRegistry();
        }
        return instance;
    }

    public EventHandler getEventHandler(String eventType) {
        return this.eventHandlerMap.get(eventType);
    }
}
