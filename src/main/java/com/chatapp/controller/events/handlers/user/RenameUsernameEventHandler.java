package com.chatapp.controller.events.handlers.user;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.chatapp.controller.events.handlers.shared.SharedEventHandler;
import com.chatapp.model.User;
import com.chatapp.model.dataclass.ClientRequest;
import com.chatapp.view.SocketConnection;

public class RenameUsernameEventHandler extends SharedEventHandler {

        private static final Logger logger = LogManager
                        .getLogger(SocketConnection.class.getName());

        @Override
        public String handleEvent(ClientRequest payload) {
                message = "Failed to update username. Please try again!";

                String email = payload.data.user.getEmail();
                String username = payload.data.user.getUsername();

                List<User> dbRetrievedUser = sharedUser.getUserIDByEmail(email);

                logger.info("Now changing {} -> {}" + dbRetrievedUser.get(0).toString(), username);

                if (!dbRetrievedUser.isEmpty()) {
                        boolean isUsernameUpdated = sharedUser.updateUsernameEQID(
                                        username,
                                        dbRetrievedUser.get(0).getEmail());

                        if (isUsernameUpdated) {
                                // Username updated successfully
                                status = "Success";
                                message = "Successfully updated username!";
                        }

                }
                return sendPayloadToClient();
        }
}
