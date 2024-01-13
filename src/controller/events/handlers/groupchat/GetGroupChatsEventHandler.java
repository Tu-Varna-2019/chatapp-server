package controller.events.handlers.groupchat;

import java.util.List;

import controller.events.handlers.shared.SharedEventHandler;
import controller.helpers.Helpers;
import model.GroupChat;
import model.User;
import model.dataclass.ClientRequest;

public class GetGroupChatsEventHandler extends SharedEventHandler {

        @Override
        public String handleEvent(ClientRequest payload) {

                String email = payload.data.user.getEmail();

                StringBuilder groupChatsJSON = new StringBuilder();
                List<GroupChat> dbRetrievedGroupChat = null;
                logger.info("\nEmail: {}", email);

                try {
                        List<User> dbRetrievedUser = chatDBManager.getUsersQuery(getRecord.getUserEQEmail(email));

                        logger.info("Retrieved user: " + dbRetrievedUser.get(0).toString());

                        // Get all group chats, that the authenticated user is currently in
                        dbRetrievedGroupChat = chatDBManager
                                        .getGroupChatQuery(
                                                        getRecord.getGroupChatEQUserID(dbRetrievedUser.get(0).getId()));

                        for (GroupChat groupChat : dbRetrievedGroupChat) {
                                // Get all users, that are in the current group chat
                                List<User> users = chatDBManager.getUsersQuery(
                                                getRecord.getUsersByIDS(
                                                                Helpers.convertArrIntToStringComma(
                                                                                groupChat.getUserids())));

                                // Add the users object to the current group chat
                                groupChat.setUsers(users);
                                // Add the current group chat to the JSON string
                                groupChatsJSON.append(groupChat.toString() + ",");
                        }

                        // Remove the last comma from the JSON string
                        groupChatsJSON.delete(groupChatsJSON.length() - 1, groupChatsJSON.length());
                } catch (Exception e) {
                        logger.error("Error: {}", e.getMessage());
                }
                String status = dbRetrievedGroupChat == null ? "Failed" : "Success";
                String message = dbRetrievedGroupChat == null ? "Empty group chat!"
                                : "Found group chat with the authenticated user!";

                return (String.format(
                                "{\"response\":{\"status\":\"%s\",\"message\":\"%s\", \"groupchats\":[%s]}}",
                                status, message, groupChatsJSON));
        }
}
