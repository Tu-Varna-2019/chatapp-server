package com.chatapp.controller.events.handlers.groupchat;

import com.chatapp.controller.events.handlers.shared.SharedEventHandler;
import com.chatapp.model.dataclass.ClientRequest;

public class DeleteGroupChatEventHandler extends SharedEventHandler {
    @Override
    public String handleEvent(ClientRequest payload) {
        message = "No group chat found!";

        Integer groupchatid = payload.data.groupchat.getId();

        boolean isGroupChatDeleted = sharedGroupChat.deleteGroupChatEQID(groupchatid);

        if (isGroupChatDeleted) {
            status = "Success";
            message = "Group chat deleted successfully!";
        }

        return sendPayloadToClient();
    }
}
