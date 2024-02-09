package com.chatapp.controller.events.handlers.user;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.chatapp.controller.events.handlers.shared.SharedEventHandler;
import com.chatapp.model.User;
import com.chatapp.model.dataclass.ClientRequest;
import com.chatapp.view.SocketConnection;

public class RenameEmailEventHandler extends SharedEventHandler {

        private static final Logger logger = LogManager
                        .getLogger(SocketConnection.class.getName());

        @Override
        public String handleEvent(ClientRequest payload) {
                message = "Failed to update email. Please try again!";

                String newEmail = payload.data.user.getEmail();
                String oldEmail = payload.data.user.getUsername();

                List<User> dbRetrievedUser = sharedUser.getUserIDByEmail(oldEmail);

                List<User> dbCheckUserEQEmail = sharedUser.getUserIDByEmail(newEmail);

                // Check if email already exists
                if (!dbCheckUserEQEmail.isEmpty()) {
                        message = "Email already exists!";
                } else {

                        logger.info("Now changing {} -> {}" + dbRetrievedUser.get(0).getEmail(), newEmail);

                        if (!dbRetrievedUser.isEmpty()) {
                                boolean isEmailUpdated = sharedUser.updateEmailEQID(newEmail,
                                                dbRetrievedUser.get(0).getId());

                                if (isEmailUpdated) {
                                        // Email updated successfully
                                        status = "Success";
                                        message = "Successfully updated email!";
                                }
                        }
                }
                return sendPayloadToClient();
        }
}
