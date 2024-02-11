package com.chatapp.controller.events.handlers.user;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.chatapp.controller.events.handlers.shared.SharedEventHandler;
import com.chatapp.controller.helpers.MaskData;
import com.chatapp.model.User;
import com.chatapp.model.dataclass.ClientRequest;
import com.chatapp.view.WebSocketConnection;

public class ChangePasswordEventHandler extends SharedEventHandler {
        private static final Logger logger = LogManager
                        .getLogger(WebSocketConnection.class.getName());

        @Override
        public String handleEvent(ClientRequest payload) {
                message = "Failed to update password. Please try again!";

                String email = payload.data.user.getEmail();
                String oldPassword = payload.data.user.getPassword();
                String newPassword = payload.data.user.getUsername();

                List<User> dbUser = sharedUser.getUserIDByEmail(email);

                boolean isPasswordCorrect = MaskData.checkHashedPassword(oldPassword,
                                dbUser.get(0).getPassword());

                // Check if Password is correct
                if (isPasswordCorrect) {

                        logger.info("Now changing {} -> {}" + oldPassword, newPassword);

                        String hashedPassword = MaskData.hashPassword(newPassword);

                        boolean isPasswordUpdated = sharedUser.updatePasswordEQID(hashedPassword,
                                        dbUser.get(0).getId());

                        if (isPasswordUpdated) {
                                // Password updated successfully
                                status = "Success";
                                message = "Successfully updated password! We will log you out now";
                        }

                } else {
                        status = "Failed";
                        message = "Incorrect password!";
                }

                return sendPayloadToClient();
        }
}
