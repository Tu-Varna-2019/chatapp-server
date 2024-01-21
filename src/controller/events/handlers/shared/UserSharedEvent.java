package controller.events.handlers.shared;

import java.util.List;

import model.User;

/**
 * UserSharedEvent
 */
public class UserSharedEvent extends SharedEventValues {

    public List<User> getUserIDByEmail(String email) {
        List<User> dbSender = null;
        try {
            dbSender = chatDBManager.getUsersQuery(
                    getRecord.getUserEQEmail, email);

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
                            updateRecord.UpdatePasswordEQID, password, userID);

        } catch (Exception e) {
            logger.error("updatePasswordEQID: {}", e.getMessage());
        }
        return false;
    }

    public boolean updateEmailEQID(String email, int userID) {
        try {
            return chatDBManager
                    .executeUpdateQuery(
                            updateRecord.UpdateEmailEQID, email, userID);

        } catch (Exception e) {
            logger.error("updateEmailEQID: {}", e.getMessage());
        }
        return false;
    }

    public boolean updateUsernameEQID(String username, String email) {
        try {
            return chatDBManager
                    .executeUpdateQuery(
                            updateRecord.UpdateUsernameEQEmail, username, email);

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
