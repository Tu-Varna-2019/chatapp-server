package controller.events.handlers;

import java.sql.Timestamp;
import java.util.List;
import java.util.TreeMap;

import controller.events.SharedDataEventHandler;
import model.Message;
import model.User;

public class SendMessageByGroupIDEventHandler extends SharedDataEventHandler {

        @Override
        public String handleEvent(TreeMap<String, String> payload) {

                // JSON string formatter for final result
                StringBuilder messagesJSON = new StringBuilder();
                String groupChatID = payload.get("groupchatid");

                Message receivedMessage = new Message(Integer.parseInt(payload.get("id")), payload.get("content"),
                                payload.get("attachmentURL"), Timestamp.valueOf(payload.get("timestamp")),
                                new User(0, payload.get("username"), payload.get("email"), ""));
                logger.info("\nReceived message: {} \n GroupChat ID: {}", receivedMessage.toString(), groupChatID);

                try {
                        List<User> dbSender = chatDBManager.getUsersQuery(
                                        getRecord.getUserEQEmail(receivedMessage.getSender().getEmail()));

                        logger.info("Retrieved sender: " + dbSender.get(0).toString());

                        // Insert message
                        chatDBManager.insertQuery(insertStatement.INSERT_MESSAGE, receivedMessage.getContent(),
                                        receivedMessage.getAttachmentURL(), receivedMessage.getTimestamp(),
                                        dbSender.get(0).getId(), Integer.parseInt(groupChatID));

                        List<Message> dbMessages = chatDBManager
                                        .getMessagesQuery(getRecord.getMessageEQGroupID(Integer.parseInt(groupChatID)));

                        logger.info("Retrieved message: " + dbMessages.get(0).toString());

                        for (Message message : dbMessages) {
                                // Add the current message to the JSON string
                                messagesJSON.append(message.toString() + ",");
                        }
                        // Remove the last comma from the JSON string
                        messagesJSON.delete(messagesJSON.length() - 1, messagesJSON.length());
                        return (String.format(
                                        "{\"response\":{\"status\":\"%s\",\"message\":\"%s\", \"messages\":[%s]}}",
                                        "Success", "Message inserted!", messagesJSON));

                } catch (Exception e) {
                        logger.error("Error: {}", e.getMessage());
                }

                return (String.format(
                                "{\"response\":{\"status\":\"%s\",\"message\":\"%s\", \"messages\":[%s]}}",
                                "Failed", "Error retrieveing messages", ""));
        }
}
