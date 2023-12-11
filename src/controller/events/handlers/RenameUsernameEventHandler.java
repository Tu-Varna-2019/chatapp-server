package controller.events.handlers;

import java.util.List;

import controller.events.SharedDataEventHandler;
import model.User;

public class RenameUsernameEventHandler extends SharedDataEventHandler {

        @Override
        public String handleEvent(String... args) {
                // args values: [email, password]
                String email = args[0];
                logger.info("\nEmail: {}", email);

                List<User> dbRetrievedUser = chatDBManager.getUsersQuery(getRecord.getUserEQEmail(email));

                if (!dbRetrievedUser.isEmpty()) {
                        boolean isUsernameUpdated = chatDBManager
                                        .deleteRecordQuery(updateRecord.UpdateUsernameEQEmail(
                                                        dbRetrievedUser.get(0).getUsername(),
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
