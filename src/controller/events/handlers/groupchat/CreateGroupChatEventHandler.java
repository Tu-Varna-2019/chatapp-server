package controller.events.handlers.groupchat;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import controller.events.handlers.shared.SharedEventHandler;
import model.GroupChat;
import model.User;
import model.dataclass.ClientRequest;
import view.SocketConnection;

public class CreateGroupChatEventHandler extends SharedEventHandler {
    private static final Logger logger = LogManager
            .getLogger(SocketConnection.class.getName());

    @Override
    public String handleEvent(ClientRequest payload) {

        List<User> users = payload.data.groupchat.getUsers();
        String groupchatName = payload.data.groupchat.getName();

        message = groupchatName + " already exists!";

        String email = users.get(0).getEmail();

        logger.info("Creating new group chat with name: {}\n from user: {}", groupchatName, email);

        List<User> dbUser = sharedUser.getUserIDByEmail(email);

        // Check if group chat name already exists
        List<GroupChat> dbGroupChat = sharedGroupChat.getGroupChatEQName(groupchatName);

        if (dbGroupChat.isEmpty()) {
            // Group chat name doesn't exists
            status = "Success";
            message = groupchatName + " created successfully!";
            // Insert new group chat
            sharedGroupChat.insertGroupChat(groupchatName, dbUser);
        }

        return sendPayloadToClient();
    }
}
