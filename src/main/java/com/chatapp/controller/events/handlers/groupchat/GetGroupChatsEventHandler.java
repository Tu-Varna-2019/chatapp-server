package com.chatapp.controller.events.handlers.groupchat;

import java.util.List;

import com.chatapp.controller.events.handlers.shared.SharedEventHandler;
import com.chatapp.model.GroupChat;
import com.chatapp.model.User;
import com.chatapp.model.dataclass.ClientRequest;

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
                        } else {
                                status = "Error";
                                if (dataResponse.groupchats != null)
                                        dataResponse.groupchats.clear();
                        }
                }

                return sendPayloadToClient();
        }
}
