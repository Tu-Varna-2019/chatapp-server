package controller.events.handlers.groupchat;

import java.util.List;

import controller.events.handlers.shared.SharedEventHandler;
import model.GroupChat;
import model.User;
import model.dataclass.ClientRequest;

public class GetGroupChatsEventHandler extends SharedEventHandler {

        @Override
        public String handleEvent(ClientRequest payload) {
                message = "Empty group chat!";

                String email = payload.data.user.getEmail();

                if (!email.isEmpty()) {

                        List<User> dbUser = sharedUser.getUserIDByEmail(email);
                        // Get all group chats, that the authenticated user is currently in
                        List<GroupChat> dbGroupChat = sharedGroupChat.getGroupChatEQUserID(dbUser.get(0).getId());

                        if (!dbGroupChat.isEmpty()) {
                                status = "Success";
                                message = "Found group chat with the authenticated user!";
                                dataResponse.groupchats = dbGroupChat;
                        }
                }

                return sendPayloadToClient();
        }
}
