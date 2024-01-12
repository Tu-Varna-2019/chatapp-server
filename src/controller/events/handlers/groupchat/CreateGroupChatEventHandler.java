package controller.events.handlers.groupchat;

import java.util.List;

import controller.events.handlers.shared.SharedEventHandler;
import model.GroupChat;
import model.User;
import model.dataclass.ClientResponse;

public class CreateGroupChatEventHandler extends SharedEventHandler {

    @Override
    public String handleEvent(ClientResponse payload) {

        List<User> users = payload.data.groupchat.getUsers();
        String groupchatName = payload.data.groupchat.getName();

        String email = users.get(0).getEmail();

        String status = "Failed";
        String message = " already exists!";

        logger.info("Creating new group chat with name: {}\n from user: {}", groupchatName, email);

        try {
            List<User> dbRetrievedUser = chatDBManager.getUsersQuery(getRecord.getUserEQEmail(email));

            logger.info("Retrieved user: " + dbRetrievedUser.get(0).toString());

            // Check if group chat name already exists
            List<GroupChat> dbGroupChat = chatDBManager
                    .getGroupChatQuery(
                            getRecord.getGroupChatEQName(groupchatName));

            if (dbGroupChat.isEmpty()) {
                // Group chat name doesn't exists
                status = "Success";
                message = " created successfully!";
                // Insert new group chat
                chatDBManager.insertQuery(insertStatement.INSERT_GROUPCHAT, groupchatName,
                        new Integer[] { dbRetrievedUser.get(0).getId() });
            }

        } catch (Exception e) {
            logger.error("Error in CreateGroupChat: {}", e.getMessage());
        }
        return (String.format("{\"response\":{\"status\":\"%s\",\"message\":\"%s\"}}",
                status, groupchatName + message));
    }
}
