package controller.events;

import java.util.HashMap;
import java.util.Map;

import controller.events.handlers.*;
import controller.events.handlers.friendrequest.GetFriendRequestsEventHandler;
import controller.events.handlers.friendrequest.SendFriendRequestEventHandler;
import controller.events.handlers.groupchat.CreateGroupChatEventHandler;
import controller.events.handlers.groupchat.GetGroupChatsEventHandler;
import controller.events.handlers.message.DeleteMessageEventHandler;
import controller.events.handlers.message.GetMessagesEventHandler;
import controller.events.handlers.message.SendMessageEventHandler;
import controller.events.handlers.user.ChangePasswordEventHandler;
import controller.events.handlers.user.DeleteAccountEventHandler;
import controller.events.handlers.user.LoginEventHandler;
import controller.events.handlers.user.RenameEmailEventHandler;
import controller.events.handlers.user.RenameUsernameEventHandler;
import controller.events.handlers.user.SignUpEventHandler;

/*
 * This class is used to register all the event handlers
 * and return the appropriate event handler based on the event type
 */
public class EventHandlerRegistry {
    private static final Map<String, EventHandler> eventHandlerMap = new HashMap<>();

    static {

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

        // Friend requests
        eventHandlerMap.put("GetFriendRequests", new GetFriendRequestsEventHandler());
        eventHandlerMap.put("SendFriendRequest", new SendFriendRequestEventHandler());
        eventHandlerMap.put("GetIncomingFriendRequests", new GetIncomingFriendRequestsEventHandler());
        eventHandlerMap.put("GetFriendsAuthUser", new GetFriendsAuthUserEventHandler());

        // Messages
        eventHandlerMap.put("GetMessages", new GetMessagesEventHandler());
        eventHandlerMap.put("SendMessage", new SendMessageEventHandler());
        eventHandlerMap.put("DeleteMessage", new DeleteMessageEventHandler());

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
