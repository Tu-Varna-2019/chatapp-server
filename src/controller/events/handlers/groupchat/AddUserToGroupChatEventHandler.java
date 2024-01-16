package controller.events.handlers.groupchat;

import java.util.List;

import controller.events.handlers.shared.SharedEventHandler;
import model.User;
import model.dataclass.ClientRequest;

public class AddUserToGroupChatEventHandler extends SharedEventHandler {
    @Override
    public String handleEvent(ClientRequest payload) {
        message = "No user found!";

        String email = payload.data.user.getEmail();
        Integer groupchatid = Integer.parseInt(payload.data.id);

        if (!email.isEmpty()) {

            List<User> dbUser = sharedUser.getUserIDByEmail(email);

            boolean isUserAlreadyInGroupChat = sharedGroupChat.checkIfUserAlreadyInGroupChatEQGPID(groupchatid,
                    dbUser.get(0).getId());

            if (isUserAlreadyInGroupChat) {
                status = "Failed";
                message = "User is already present in group chat!";

            } else {
                boolean isUserAddedFromGroupChat = sharedGroupChat
                        .updateUserFromGroupChatEQGPID(groupchatid, dbUser.get(0).getId(), "Add");

                if (isUserAddedFromGroupChat) {
                    // User added from group chat successfully
                    status = "Success";
                    message = "User added to group chat successfully!";
                }
            }
        }

        return sendPayloadToClient();
    }
}
