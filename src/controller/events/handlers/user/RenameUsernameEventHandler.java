package controller.events.handlers.user;

import java.util.List;
import java.util.TreeMap;

import controller.events.handlers.shared.SharedEventHandler;
import model.User;

public class RenameUsernameEventHandler extends SharedEventHandler {

        @Override
        public String handleEvent(TreeMap<String, String> payload) {
                String email = payload.get("email");
                String username = payload.get("username");
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
