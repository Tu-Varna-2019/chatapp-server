package controller.events.handlers;

import java.util.List;

import controller.events.SharedDataEventHandler;
import controller.helpers.MaskData;
import model.User;

public class SignUpEventHandler extends SharedDataEventHandler {

    @Override
    public String handleEvent(String... args) {
        // args values: [email, password, username]
        String email = args[0];
        String password = MaskData.hashPassword(args[1]);
        String username = args[2];

        logger.info("UserName: {} \nEmail: {} \nPassword: {}", username, email, password);

        List<User> dbCheckExistingEmail = chatDBManager.getUsersQuery(getRecord.getUserEQEmail(email));

        if (!dbCheckExistingEmail.isEmpty())
            return "{\"response\":{\"status\":\"Failed\",\"message\":\"Email already exists\"}}";

        // If email does not exist, insert user
        chatDBManager.insertQuery(insertStatement.INSERT_USER, username, email, password);

        return "{\"response\":{\"status\":\"Success\",\"message\":\"User registered\"}}";

    }
}
