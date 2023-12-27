package controller.events.handlers;

import java.sql.Timestamp;
import java.util.Base64;
import java.util.List;
import java.util.TreeMap;
import java.util.UUID;

import controller.events.SharedDataEventHandler;
import controller.helpers.Helpers;
import model.GroupChat;
import model.Message;
import model.User;
import model.storage.S3Manager;
import view.SocketConnection;

public class SendMessageByGroupIDEventHandler extends SharedDataEventHandler {

        @Override
        public String handleEvent(TreeMap<String, String> payload) {

                String groupChatID = payload.get("groupchatid");
                String fileAttachment = payload.get("attachmentURL");

                fileAttachment = uploadAttachmentFile(fileAttachment, payload.get("email"));

                Message receivedMessage = new Message(Integer.parseInt(payload.get("id")), payload.get("content"),
                                fileAttachment, Timestamp.valueOf(payload.get("timestamp")),
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

                        // Notify users from the group chat
                        // notifyUsers(groupChatID, receivedMessage, dbSender.get(0).getId());

                        StringBuilder messagesJSON = getMessages(groupChatID);

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

        private void notifyUsers(String groupChatID, Message message, Integer senderID) {
                try {
                        logger.info("Notifying users for group chat: {}", groupChatID);
                        SocketConnection socketConnection = SocketConnection.getInstance();
                        // Get group chat
                        List<GroupChat> dbGroupChat = chatDBManager
                                        .getGroupChatQuery(
                                                        getRecord.getGroupChatEQID(Integer.parseInt(groupChatID)));

                        logger.info("Retrieved user ids for group chat: " + dbGroupChat.get(0).getUserids());
                        // Get all users, that are in the current group chat
                        List<User> users = chatDBManager.getUsersQuery(
                                        getRecord.getUsersByIDS(
                                                        Helpers.convertArrIntToStringComma(
                                                                        dbGroupChat.get(0).getUserids())));

                        // Remove the sender from being notified
                        users.removeIf(nullUser -> nullUser.getId() == senderID);
                        logger.info("Retrieved users: " + users.toString());
                        // Add the users object to the current group chat
                        dbGroupChat.get(0).setUsers(users);
                        // Send group chat name as attachment URL, in order to avoid creating new data
                        // class on Kotlin side
                        message.setAttachmentURL(dbGroupChat.get(0).getName());

                        for (User user : dbGroupChat.get(0).getUsers()) {
                                // Get the socket of the current user
                                logger.info("Sending message to user: {}", user.getEmail());

                                String response = String.format(
                                                "{\"response\":{\"status\":\"%s\",\"message\":\"%s\", \"messages\":[%s]}}",
                                                "Success", "Notification sent!", message.toString());

                                socketConnection.sendEvent(user.getEmail(), response);
                        }

                } catch (Exception e) {
                        logger.error("Error: {}", e.getMessage());
                }
        }

        private String uploadAttachmentFile(String fileAttachment, String email) {
                String fileName = "img_" + UUID.randomUUID().toString() + ".png";

                if (fileAttachment != null && !fileAttachment.isEmpty()) {
                        byte[] imageBytes = Base64.getDecoder().decode(fileAttachment);
                        // Save file to s3
                        boolean isUploadCompleted = S3Manager.uploadFile(email + "/" + fileName,
                                        imageBytes);

                        if (isUploadCompleted)
                                return fileName;
                }
                return "";
        }

}
