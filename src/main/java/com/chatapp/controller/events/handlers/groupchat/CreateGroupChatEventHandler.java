package com.chatapp.controller.events.handlers.groupchat;

import java.util.List;

import com.chatapp.controller.events.handlers.shared.SharedEventHandler;
import com.chatapp.model.GroupChat;
import com.chatapp.model.User;
import com.chatapp.model.dataclass.ClientRequest;

public class CreateGroupChatEventHandler extends SharedEventHandler {

    @Override
    public String handleEvent(ClientRequest payload) {

        List<User> users = payload.data.groupchat.getUsers();
        String groupchatName = payload.data.groupchat.getName();

        message = groupchatName + " already exists!";

        String email = users.get(0).getEmail();

        logger.info("Creating new group chat with name: {}\n from user: {}", groupchatName, email);

        List<User> dbUser = sharedUser.getUserIDByEmail(email);

        // Check if group chat name already exists
        List<GroupChat> dbGroupChat = sharedGroupChat.getGroupChatEQName(groupchatName);

        if (dbGroupChat.isEmpty()) {
            // Group chat name doesn't exists
            status = "Success";
            message = groupchatName + " created successfully!";
            // Insert new group chat
            sharedGroupChat.insertGroupChat(groupchatName, dbUser);
        }

        return sendPayloadToClient();
    }
}
