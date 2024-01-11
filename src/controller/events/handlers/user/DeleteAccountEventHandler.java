package controller.events.handlers.user;

import java.util.List;
import java.util.TreeMap;

import controller.events.handlers.shared.SharedEventHandler;
import controller.helpers.MaskData;
import model.User;

public class DeleteAccountEventHandler extends SharedEventHandler {

        @Override
        public String handleEvent(TreeMap<String, String> payload) {
                String email = payload.get("email");
                String password = payload.get("password");

                logger.info("\nEmail: {} \nPassword: {}", email, password);

                List<User> dbRetrievedUser = chatDBManager.getUsersQuery(getRecord.getUserEQEmail(email));

                boolean isPasswordCorrect = MaskData.checkHashedPassword(password,
                                dbRetrievedUser.get(0).getPassword());

                if (isPasswordCorrect) {

                        boolean isDeleted = chatDBManager
                                        .updateRecordQuery(deleteRecord.DeleteUserEQID(dbRetrievedUser.get(0).getId()));

                        String status = !isDeleted ? "Failed" : "Success";
                        String message = !isDeleted ? "Incorrect email/password!" : "Successfully logged in!";

                        return String.format(
                                        "{\"response\":{\"status\":\"%s\",\"message\":\"%s\"}}",
                                        status,
                                        message);
                } else
                        return String.format(
                                        "{\"response\":{\"status\":\"%s\",\"message\":\"%s\"}}",
                                        "Failed",
                                        "Incorrect password!");
        }
}
