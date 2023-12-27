package controller.events.handlers;

import java.util.TreeMap;

import controller.events.SharedDataEventHandler;

public class GetMessagesByGroupIDEventHandler extends SharedDataEventHandler {

        @Override
        public String handleEvent(TreeMap<String, String> payload) {
                String groupid = payload.get("id");
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
