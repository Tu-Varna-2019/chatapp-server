package controller.events.handlers;

import java.util.List;

import controller.events.SharedDataEventHandler;
import controller.helpers.MaskData;
import model.User;

public class DeleteAccountEventHandler extends SharedDataEventHandler {

        @Override
        public String handleEvent(String... args) {
                // args values: [email, password]
                String email = args[0];
                logger.info("\nEmail: {} \nPassword: {}", email, args[1]);

                List<User> dbRetrievedUser = chatDBManager.getUsersQuery(getRecord.getUserEQEmail(email));

                boolean isPasswordCorrect = MaskData.checkHashedPassword(args[1], dbRetrievedUser.get(0).getPassword());

                if (isPasswordCorrect) {

                        boolean isDeleted = chatDBManager
                                        .deleteRecordQuery(deleteRecord.DeleteUserEQID(dbRetrievedUser.get(0).getId()));

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
