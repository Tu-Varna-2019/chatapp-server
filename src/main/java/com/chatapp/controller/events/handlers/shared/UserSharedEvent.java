package com.chatapp.controller.events.handlers.shared;

import java.util.List;

import com.chatapp.model.User;

/**
 * UserSharedEvent
 */
public class UserSharedEvent extends SharedEventValues {

    public List<User> getUserIDByEmail(String email) {
        List<User> dbSender = null;
        try {
            dbSender = chatDBManager.getUsersQuery(
                    getRecord.GET_USER_EQ_EMAIL, email);

            logger.info("Retrieved user: " + dbSender.get(0).toString());
        } catch (Exception e) {
            logger.error("getUserIDByEmail Error: {}", e.getMessage());
        }
        return dbSender;
    }

    public boolean updatePasswordEQID(String password, int userID) {
        try {
            return chatDBManager
                    .executeUpdateQuery(
                            updateRecord.UPDATE_USER_PASSWORD_EQ_ID, password, userID);

        } catch (Exception e) {
            logger.error("updatePasswordEQID: {}", e.getMessage());
        }
        return false;
    }

    public boolean updateEmailEQID(String email, int userID) {
        try {
            return chatDBManager
                    .executeUpdateQuery(
                            updateRecord.UPDATE_USER_EMAIL_EQ_ID, email, userID);

        } catch (Exception e) {
            logger.error("updateEmailEQID: {}", e.getMessage());
        }
        return false;
    }

    public boolean updateUsernameEQID(String username, String email) {
        try {
            return chatDBManager
                    .executeUpdateQuery(
                            updateRecord.UPDATE_USER_USERNAME_EQ_EMAIL, username, email);

        } catch (Exception e) {
            logger.error("updateUsernameEQID: {}", e.getMessage());
        }
        return false;
    }

    public boolean deleteUserEQID(int userID) {
        try {
            return chatDBManager
                    .executeUpdateQuery(
                            deleteRecord.DeleteUserEQID, userID);

        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
        }
        return false;
    }

    public void insertUser(String username, String email, String password) {
        chatDBManager.executeUpdateQuery(insertStatement.INSERT_USER, username, email, password);
    }

}
