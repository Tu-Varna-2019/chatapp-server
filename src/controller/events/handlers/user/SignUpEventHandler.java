package controller.events.handlers.user;

import java.util.List;

import controller.events.handlers.shared.SharedEventHandler;
import controller.helpers.MaskData;
import model.User;
import model.dataclass.ClientResponse;

public class SignUpEventHandler extends SharedEventHandler {

    @Override
    public String handleEvent(ClientResponse payload) {

        String email = payload.data.user.getEmail();
        String password = MaskData.hashPassword(payload.data.user.getPassword());
        String username = payload.data.user.getUsername();

        logger.info("UserName: {} \nEmail: {} \nPassword: {}", username, email, password);

        List<User> dbCheckExistingEmail = chatDBManager.getUsersQuery(getRecord.getUserEQEmail(email));

        if (!dbCheckExistingEmail.isEmpty())
            return "{\"response\":{\"status\":\"Failed\",\"message\":\"Email already exists\"}}";

        // If email does not exist, insert user
        chatDBManager.insertQuery(insertStatement.INSERT_USER, username, email, password);

        return "{\"response\":{\"status\":\"Success\",\"message\":\"User registered\"}}";

    }
}
