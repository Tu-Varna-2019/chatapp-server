package controller.events.handlers.user;

import java.util.List;

import controller.events.handlers.shared.SharedEventHandler;
import controller.helpers.MaskData;
import model.User;
import model.dataclass.ClientRequest;

public class ChangePasswordEventHandler extends SharedEventHandler {

        @Override
        public String handleEvent(ClientRequest payload) {

                String email = payload.data.user.getEmail();
                String oldPassword = payload.data.user.getPassword();
                String newPassword = payload.data.user.getUsername();
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
