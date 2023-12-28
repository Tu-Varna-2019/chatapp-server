package controller.events.handlers;

import java.util.List;
import java.util.TreeMap;

import controller.events.SharedDataEventHandler;
import model.GroupChat;
import model.User;

public class CreateGroupChatEventHandler extends SharedDataEventHandler {

    @Override
    public String handleEvent(TreeMap<String, String> payload) {

        String status = "Failed";
        String message = " already exists!";

        String groupchatName = payload.get("name");
        String email = payload.get("email");
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
