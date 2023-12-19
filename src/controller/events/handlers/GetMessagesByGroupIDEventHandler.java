package controller.events.handlers;

import java.util.List;
import java.util.TreeMap;

import controller.events.SharedDataEventHandler;
import model.Message;

public class GetMessagesByGroupIDEventHandler extends SharedDataEventHandler {

        @Override
        public String handleEvent(TreeMap<String, String> payload) {
                // args values: [id]
                Integer groupid = Integer.parseInt(payload.get("id"));
                // JSON string formatter for final result
                StringBuilder messagesJSON = new StringBuilder();
                List<Message> dbRetrievedMessage = null;
                logger.info("\nReceived Group ID: {}", groupid);

                try {
                        dbRetrievedMessage = chatDBManager
                                        .getMessagesQuery(getRecord.getMessageEQGroupID(groupid));

                        logger.info("Retrieved message: " + dbRetrievedMessage.get(0).toString());

                        for (Message message : dbRetrievedMessage) {
                                // Add the current message to the JSON string
                                messagesJSON.append(message.toString() + ",");
                        }
                } catch (Exception e) {
                        logger.error("Error: {}", e.getMessage());
                }
                // Remove the last comma from the JSON string
                messagesJSON.delete(messagesJSON.length() - 1, messagesJSON.length());
                String status = dbRetrievedMessage == null ? "Failed" : "Success";
                String message = dbRetrievedMessage == null ? "No messages found!"
                                : "Messages found for the group id: " + groupid + " !";

                return (String.format(
                                "{\"response\":{\"status\":\"%s\",\"message\":\"%s\", \"messages\":[%s]}}",
                                status, message, messagesJSON));
        }
}
