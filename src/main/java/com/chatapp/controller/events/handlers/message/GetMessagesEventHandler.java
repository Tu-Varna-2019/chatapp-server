package com.chatapp.controller.events.handlers.message;

import java.util.List;

import com.chatapp.controller.events.handlers.shared.SharedEventHandler;
import com.chatapp.model.Message;
import com.chatapp.model.dataclass.ClientRequest;

public class GetMessagesEventHandler extends SharedEventHandler {

        @Override
        public String handleEvent(ClientRequest payload) {
                message = "No messages found!";

                String groupid = payload.data.id;

                List<Message> dbMessages = sharedMessage.getMessages(groupid);

                if (!dbMessages.isEmpty()) {
                        status = "Success";
                        message = "Messages found for the group id: " + groupid + " !";
                        dataResponse.messages = dbMessages;
                } else {
                        status = "Error";
                        if (dataResponse.messages != null)
                                dataResponse.messages.clear();
                }

                return sendPayloadToClient();

        }
}
