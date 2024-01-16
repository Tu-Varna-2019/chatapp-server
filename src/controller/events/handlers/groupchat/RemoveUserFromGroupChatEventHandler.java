package controller.events.handlers.groupchat;

import java.util.List;

import controller.events.handlers.shared.SharedEventHandler;
import model.User;
import model.dataclass.ClientRequest;

public class RemoveUserFromGroupChatEventHandler extends SharedEventHandler {
    /*
     * Expected payload format:
     * {
     * "event": "RemoveUserFromGroupChat",
     * "data": {
     * "id": "1", <-- eg. groupchatid
     * "user": {
     * "username": "Empty",
     * "email": "john@doe.email.com",
     * "password": "Empty",
     */
    @Override
    public String handleEvent(ClientRequest payload) {
        message = "No user found!";

        String email = payload.data.user.getEmail();
        Integer groupchatid = Integer.parseInt(payload.data.id);

        if (!email.isEmpty()) {

            List<User> dbUser = sharedUser.getUserIDByEmail(email);

            boolean isUserRemovedFromGroupChat = sharedGroupChat
                    .updateUserFromGroupChatEQGPID(groupchatid, dbUser.get(0).getId(), "Remove");

            if (isUserRemovedFromGroupChat) {
                // User removed from group chat successfully
                status = "Success";
                message = "User removed from group chat successfully!";
            }
        }

        return sendPayloadToClient();
    }
}
