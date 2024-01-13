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
                    getRecord.getUserEQEmail(email));

            logger.info("Retrieved sender: " + dbSender.get(0).toString());
        } catch (Exception e) {
            logger.error("getUserIDByEmail Error: {}", e.getMessage());
        }
        return dbSender;
    }

    public boolean updatePasswordEQID(String password, int userID) {
        try {
            boolean isUpdated = chatDBManager
                    .updateRecordQuery(
                            updateRecord.UpdatePasswordEQID(password, userID));

            return isUpdated;
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
        }
        return false;
    }

    public boolean updateEmailEQID(String email, int userID) {
        try {
            boolean isUpdated = chatDBManager
                    .updateRecordQuery(
                            updateRecord.UpdateEmailEQID(email, userID));

            return isUpdated;
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
        }
        return false;
    }

    public boolean updateUsernameEQID(String username, String email) {
        try {
            boolean isUpdated = chatDBManager
                    .updateRecordQuery(
                            updateRecord.UpdateUsernameEQEmail(username, email));

            return isUpdated;
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
        }
        return false;
    }

    public boolean deleteUserEQID(int userID) {
        try {
            boolean isDeleted = chatDBManager
                    .updateRecordQuery(
                            deleteRecord.DeleteUserEQID(userID));

            return isDeleted;
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
        }
        return false;
    }

    public void insertUser(String username, String email, String password) {
        chatDBManager.insertQuery(insertStatement.INSERT_USER, username, email, password);
    }

}
