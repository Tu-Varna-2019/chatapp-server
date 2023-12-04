package controller.events.handlers;

import java.util.List;

import controller.events.SharedDataEventHandler;
import controller.helpers.MaskData;
import model.User;

public class LoginEventHandler extends SharedDataEventHandler {

    @Override
    public String handleEvent(String... args) {
        // args values: [email, password]
        String email = args[0];
        logger.info("\nEmail: {} \nPassword: {}", email, args[1]);

        List<User> dbRetrievedUser = chatDBManager.getUsersQuery(getRecord.getUserEQEmail(email));

        boolean isPasswordCorrect = MaskData.checkHashedPassword(args[1], dbRetrievedUser.get(0).getPassword());

        String status = !isPasswordCorrect ? "Failed" : "Success";
        String message = !isPasswordCorrect ? "Incorrect email/password!" : "Successfully logged in!";

        if (isPasswordCorrect)
            return String.format(
                    "{\"response\":{\"status\":\"%s\",\"message\":\"%s\", \"user\": {\"username\":\"%s\",\"email\":\"%s\",\"password\":\"%s\"}}}",
                    status,
                    message,
                    dbRetrievedUser.get(0).getUsername(),
                    dbRetrievedUser.get(0).getEmail(),
                    dbRetrievedUser.get(0).getPassword());

        else
            return String.format(
                    "{\"response\":{\"status\":\"%s\",\"message\":\"%s\"}}",
                    status,
                    message);

    }
}
