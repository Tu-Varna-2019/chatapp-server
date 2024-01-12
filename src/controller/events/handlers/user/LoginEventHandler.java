package controller.events.handlers.user;

import java.util.List;

import controller.events.handlers.shared.SharedEventHandler;
import controller.helpers.MaskData;
import model.User;
import model.dataclass.ClientResponse;

public class LoginEventHandler extends SharedEventHandler {

    @Override
    public String handleEvent(ClientResponse payload) {

        String email = payload.data.user.getEmail();
        String password = payload.data.user.getPassword();
        boolean isPasswordCorrect = false;
        logger.info("\nEmail: {} \nPassword: {}", email, password);

        List<User> dbRetrievedUser = chatDBManager.getUsersQuery(getRecord.getUserEQEmail(email));

        if (!dbRetrievedUser.isEmpty())
            isPasswordCorrect = MaskData.checkHashedPassword(password, dbRetrievedUser.get(0).getPassword());

        String status = !isPasswordCorrect ? "Failed" : "Success";
        String message = !isPasswordCorrect ? "Incorrect email/password!" : "Successfully logged in!";

        if (isPasswordCorrect)
            return String.format(
                    "{\"response\":{\"status\":\"%s\",\"message\":\"%s\",\"user\":%s}}",
                    status,
                    message,
                    dbRetrievedUser.get(0).toString());

        else
            return String.format(
                    "{\"response\":{\"status\":\"%s\",\"message\":\"%s\"}}",
                    status,
                    message);

    }
}
