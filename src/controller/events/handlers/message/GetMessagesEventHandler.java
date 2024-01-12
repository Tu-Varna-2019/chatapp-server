package controller.events.handlers.message;

import controller.events.handlers.shared.SharedEventHandler;
import model.dataclass.ClientResponse;

public class GetMessagesEventHandler extends SharedEventHandler {

        @Override
        public String handleEvent(ClientResponse payload) {

                String groupid = payload.data.id;

                // JSON string formatter for final result
                StringBuilder messagesJSON = getMessages(groupid);

                logger.info("\nReceived Group ID: {}", groupid);

                String status = messagesJSON.isEmpty() ? "Failed" : "Success";
                String message = messagesJSON.isEmpty() ? "No messages found!"
                                : "Messages found for the group id: " + groupid + " !";

                return (String.format(
                                "{\"response\":{\"status\":\"%s\",\"message\":\"%s\", \"messages\":[%s]}}",
                                status, message, messagesJSON));
        }
}
