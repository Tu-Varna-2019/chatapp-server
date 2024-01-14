package controller.events.handlers.user;

import java.util.List;

import controller.events.handlers.shared.SharedEventHandler;
import controller.helpers.MaskData;
import model.User;
import model.dataclass.ClientRequest;

public class SignUpEventHandler extends SharedEventHandler {

    @Override
    public String handleEvent(ClientRequest payload) {

        message = "Error registering user. Please try again!";

        String email = payload.data.user.getEmail();
        String password = MaskData.hashPassword(payload.data.user.getPassword());
        String username = payload.data.user.getUsername();

        List<User> dbCheckExistingEmail = sharedUser.getUserIDByEmail(email);

        if (!dbCheckExistingEmail.isEmpty()) {
            message = "Email already exists!";
        } else {
            // If email does not exist, insert user
            sharedUser.insertUser(username, email, password);
            status = "Success";
            message = "User registered";
        }

        return sendPayloadToClient();

    }
}
