package com.chatapp.controller.events.handlers.user;

import java.util.List;

import com.chatapp.controller.events.handlers.shared.SharedEventHandler;
import com.chatapp.controller.helpers.MaskData;
import com.chatapp.model.User;
import com.chatapp.model.dataclass.ClientRequest;

public class DeleteAccountEventHandler extends SharedEventHandler {

        @Override
        public String handleEvent(ClientRequest payload) {
                message = "Error deleting account. Please try again!";

                String email = payload.data.user.getEmail();
                String password = payload.data.user.getPassword();

                List<User> dbRetrievedUser = sharedUser.getUserIDByEmail(email);

                boolean isPasswordCorrect = MaskData.checkHashedPassword(password,
                                dbRetrievedUser.get(0).getPassword());

                if (isPasswordCorrect) {

                        boolean isDeleted = sharedUser.deleteUserEQID(dbRetrievedUser.get(0).getId());

                        if (isDeleted) {
                                // Password updated successfully
                                status = "Success";
                                message = "Successfully deleted account! We will log you out now";
                        }

                } else
                        message = "Incorrect password!";

                return sendPayloadToClient();
        }
}
