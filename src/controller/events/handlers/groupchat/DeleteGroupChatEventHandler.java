package controller.events.handlers.groupchat;

import controller.events.handlers.shared.SharedEventHandler;
import model.dataclass.ClientRequest;

public class DeleteGroupChatEventHandler extends SharedEventHandler {
    @Override
    public String handleEvent(ClientRequest payload) {
        message = "No group chat found!";

        Integer groupchatid = payload.data.groupchat.getId();

        // First delete all messages within group chat
        sharedMessage.deleteMessageEQGroupChatID(groupchatid);
        // then delete the empty group chat
        boolean isGroupChatDeleted = sharedGroupChat.deleteGroupChatEQID(groupchatid);

        if (isGroupChatDeleted) {
            status = "Success";
            message = "Group chat deleted successfully!";
        }

        return sendPayloadToClient();
    }
}
