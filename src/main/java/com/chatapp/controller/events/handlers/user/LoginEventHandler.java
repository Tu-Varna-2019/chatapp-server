package com.chatapp.controller.events.handlers.user;

import java.util.List;

import com.chatapp.controller.events.handlers.shared.SharedEventHandler;
import com.chatapp.controller.helpers.MaskData;
import com.chatapp.model.User;
import com.chatapp.model.dataclass.ClientRequest;

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
        } else {
            status = "Error";
            dataResponse.user = null;
        }

        return sendPayloadToClient();

    }
}
