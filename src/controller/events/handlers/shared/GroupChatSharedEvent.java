package controller.events.handlers.shared;

import java.util.Collections;
import java.util.List;

import model.GroupChat;
import model.User;

public class GroupChatSharedEvent extends SharedEventValues {

    public List<GroupChat> getGroupChatEQName(String groupchatName) {
        List<GroupChat> dbGroupChat = null;
        try {
            dbGroupChat = chatDBManager
                    .getGroupChatQuery(
                            getRecord.GET_GROUPCHAT_EQ_NAME, groupchatName);

            logger.info("Retrieved group chat: " + dbGroupChat.get(0).toString());
        } catch (Exception e) {
            logger.error("getUserIDByEmail Error: {}", e.getMessage());
        }
        return dbGroupChat;
    }

    public boolean updateUserFromGroupChatEQGPID(int groupchatid, int userid, String mode) {
        String updateQuery = "";
        switch (mode) {
            case "Add":
                updateQuery = updateRecord.UPDATE_GROUPCHAT_USERIDS_ADD;
                break;
            case "Remove":
                updateQuery = updateRecord.UPDATE_GROUPCHAT_USERIDS_REMOVE;
                break;
            default:
                throw new IllegalArgumentException("Invalid mode: " + mode);
        }

        try {
            return chatDBManager
                    .executeUpdateQuery(
                            updateQuery, groupchatid, userid);

        } catch (Exception e) {
            logger.error("updateUserFromGroupChatEQGPID Error: {}", e.getMessage());
        }

        return false;
    }

    public boolean checkIfUserAlreadyInGroupChatEQGPID(int groupchatid, int userid) {

        return chatDBManager
                .getRecordExists(
                        getRecord.GET_FRIENDREQUEST_EQ_ID_AND_IN_USERIDS, groupchatid, userid);
    }

    public List<GroupChat> getGroupChatEQUserID(int userID) {
        List<GroupChat> dbGroupChat = null;
        try {
            dbGroupChat = chatDBManager
                    .getGroupChatQuery(
                            getRecord.GET_GROUPCHAT_EQ_USER_ID, userID);

            dbGroupChat.stream()
                    .forEach(groupChat -> {
                        List<User> users = chatDBManager.getUsersQuery(
                                getRecord.GET_USER_EQ_IN_IDS(groupChat.getUserids().length),
                                (Object[]) groupChat.getUserids());
                        groupChat.setUsers(users);
                    });
            logger.info("Retrieved group chat: " + dbGroupChat.get(0).toString());

            return dbGroupChat;
        } catch (Exception e) {
            logger.error("Group chats not found!");
            return Collections.emptyList();
        }
    }

    public void insertGroupChat(String groupchatName, List<User> dbRetrievedUser) {
        chatDBManager.executeUpdateQuery(insertStatement.INSERT_GROUPCHAT, groupchatName,
                new Integer[] { dbRetrievedUser.get(0).getId() });
    }

    public boolean deleteGroupChatEQID(int groupchatid) {
        try {
            return chatDBManager
                    .executeUpdateQuery(
                            deleteRecord.DeleteGroupChatEQID, groupchatid);

        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
        }
        return false;
    }
}
