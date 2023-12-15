package controller.events.handlers;

import java.util.List;

import controller.events.SharedDataEventHandler;
import model.User;

public class RenameUsernameEventHandler extends SharedDataEventHandler {

        @Override
        public String handleEvent(String... args) {
                // args values: [email, SKIP - password,username]
                String email = args[0];
                String username = args[2];
                logger.info("\nEmail: {}\n Username: {}", email, username);

                List<User> dbRetrievedUser = chatDBManager.getUsersQuery(getRecord.getUserEQEmail(email));

                logger.info("Now changing {} -> {}" + dbRetrievedUser.get(0).toString(), username);

                if (!dbRetrievedUser.isEmpty()) {
                        boolean isUsernameUpdated = chatDBManager
                                        .updateRecordQuery(updateRecord.UpdateUsernameEQEmail(
                                                        username,
                                                        dbRetrievedUser.get(0).getEmail()));

                        String status = !isUsernameUpdated ? "Failed" : "Success";
                        String message = !isUsernameUpdated ? "Failed to update username. Please try again!"
                                        : "Successfully updated username!";

                        return String.format(
                                        "{\"response\":{\"status\":\"%s\",\"message\":\"%s\"}}",
                                        status,
                                        message);
                } else
                        return "{\"response\":{\"status\":\"Failed\",\"message\":\"Email not found, sorry for the inconvenience! \"}}";
        }
}