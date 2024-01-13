package controller.events.handlers.shared;

import java.util.List;

import controller.helpers.Helpers;
import model.GroupChat;
import model.User;

public class GroupChatSharedEvent extends SharedEventValues {

    public List<GroupChat> getGroupChatEQName(String groupchatName) {
        List<GroupChat> dbGroupChat = null;
        try {
            dbGroupChat = chatDBManager
                    .getGroupChatQuery(
                            getRecord.getGroupChatEQName(groupchatName));

            logger.info("Retrieved group chat: " + dbGroupChat.get(0).toString());
        } catch (Exception e) {
            logger.error("getUserIDByEmail Error: {}", e.getMessage());
        }
        return dbGroupChat;
    }

    public List<GroupChat> getGroupChatEQUserID(int userID) {
        List<GroupChat> dbGroupChat = null;
        try {
            dbGroupChat = chatDBManager
                    .getGroupChatQuery(
                            getRecord.getGroupChatEQUserID(userID));

            dbGroupChat.stream()
                    .forEach(groupChat -> {
                        List<User> users = chatDBManager.getUsersQuery(
                                getRecord.getUsersByIDS(
                                        Helpers.convertArrIntToStringComma(
                                                groupChat.getUserids())));
                        groupChat.setUsers(users);
                    });

            logger.info("Retrieved group chat: " + dbGroupChat.get(0).toString());
        } catch (Exception e) {
            logger.error("getUserIDByEmail Error: {}", e.getMessage());
        }
        return dbGroupChat;
    }

    public void insertGroupChat(String groupchatName, List<User> dbRetrievedUser) {
        chatDBManager.insertQuery(insertStatement.INSERT_GROUPCHAT, groupchatName,
                new Integer[] { dbRetrievedUser.get(0).getId() });
    }
}
