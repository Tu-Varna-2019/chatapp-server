package controller.events.handlers.user;

import java.util.List;

import controller.events.handlers.shared.SharedEventHandler;
import controller.helpers.MaskData;
import model.User;
import model.dataclass.ClientRequest;

public class DeleteAccountEventHandler extends SharedEventHandler {

        @Override
        public String handleEvent(ClientRequest payload) {

                String email = payload.data.user.getEmail();
                String password = payload.data.user.getPassword();

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
