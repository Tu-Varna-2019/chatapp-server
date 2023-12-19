package controller.events.handlers;

import java.util.List;
import java.util.TreeMap;

import controller.events.SharedDataEventHandler;
import controller.helpers.MaskData;
import model.User;

public class ChangePasswordEventHandler extends SharedDataEventHandler {

        @Override
        public String handleEvent(TreeMap<String, String> payload) {
                String email = payload.get("email");
                String oldPassword = payload.get("password");
                String newPassword = payload.get("username");
                logger.info("\nReceived old Password: {} \n New Password: {}", oldPassword, newPassword);

                List<User> dbRetrievedUser = chatDBManager.getUsersQuery(getRecord.getUserEQEmail(email));

                boolean isPasswordCorrect = MaskData.checkHashedPassword(oldPassword,
                                dbRetrievedUser.get(0).getPassword());

                // Check if Password is correct
                if (isPasswordCorrect) {

                        logger.info("Now changing {} -> {}" + oldPassword, newPassword);

                        String hashedPassword = MaskData.hashPassword(newPassword);

                        boolean isPasswordUpdated = chatDBManager
                                        .updateRecordQuery(updateRecord.UpdatePasswordEQID(
                                                        hashedPassword,
                                                        dbRetrievedUser.get(0).getId()));

                        String status = !isPasswordUpdated ? "Failed" : "Success";
                        String message = !isPasswordUpdated ? "Failed to update password. Please try again!"
                                        : "Successfully updated password! We will log you out now";

                        return String.format(
                                        "{\"response\":{\"status\":\"%s\",\"message\":\"%s\"}}",
                                        status,
                                        message);
                } else
                        return "{\"response\":{\"status\":\"Failed\",\"message\":\"Incorrect password!\"}}";
        }
}
