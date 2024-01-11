package controller.events.handlers.user;

import java.util.List;
import java.util.TreeMap;

import controller.events.handlers.shared.SharedEventHandler;
import controller.helpers.MaskData;
import model.User;

public class SignUpEventHandler extends SharedEventHandler {

    @Override
    public String handleEvent(TreeMap<String, String> payload) {
        String email = payload.get("email");
        String password = MaskData.hashPassword(payload.get("password"));
        String username = payload.get("username");

        logger.info("UserName: {} \nEmail: {} \nPassword: {}", username, email, password);

        List<User> dbCheckExistingEmail = chatDBManager.getUsersQuery(getRecord.getUserEQEmail(email));

        if (!dbCheckExistingEmail.isEmpty())
            return "{\"response\":{\"status\":\"Failed\",\"message\":\"Email already exists\"}}";

        // If email does not exist, insert user
        chatDBManager.insertQuery(insertStatement.INSERT_USER, username, email, password);

        return "{\"response\":{\"status\":\"Success\",\"message\":\"User registered\"}}";

    }
}
