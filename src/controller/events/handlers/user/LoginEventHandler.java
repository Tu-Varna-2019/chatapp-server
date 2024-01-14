package controller.events.handlers.user;

import java.util.List;

import controller.events.handlers.shared.SharedEventHandler;
import controller.helpers.MaskData;
import model.User;
import model.dataclass.ClientRequest;

public class LoginEventHandler extends SharedEventHandler {

    @Override
    public String handleEvent(ClientRequest payload) {
        message = "Incorrect email/password!";

        String email = payload.data.user.getEmail();
        String password = payload.data.user.getPassword();
        boolean isPasswordCorrect = false;

        List<User> dbRetrievedUser = sharedUser.getUserIDByEmail(email);

        if (!dbRetrievedUser.isEmpty())
            isPasswordCorrect = MaskData.checkHashedPassword(password, dbRetrievedUser.get(0).getPassword());

        if (isPasswordCorrect) {
            status = "Success";
            message = "Successfully logged in!";
            dataResponse.user = dbRetrievedUser.get(0);
        }

        return sendPayloadToClient();

    }
}
