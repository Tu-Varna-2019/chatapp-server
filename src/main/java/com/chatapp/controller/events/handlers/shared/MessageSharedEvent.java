package com.chatapp.controller.events.handlers.shared;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.chatapp.controller.helpers.MaskData;
import com.chatapp.model.Message;
import com.chatapp.model.User;
import com.chatapp.model.storage.S3Manager;

public class MessageSharedEvent extends SharedEventValues {

    public List<Message> getMessages(String groupChatID) {
        List<Message> dbMessages = null;
        try {
            dbMessages = chatDBManager
                    .getMessagesQuery(getRecord.GET_MESSAGE_EQ_GROUPCHAT_ID, Integer.parseInt(groupChatID));

            logger.info("Retrieved message: " + dbMessages.get(0).toString());

            dbMessages = dbMessages.stream()
                    .map(message -> {
                        if (message.getAttachmentURL() == null || message.getAttachmentURL().isEmpty()) {
                            message.setAttachmentURL("");
                        } else {
                            String encodedAttachmentFile = MaskData.base64EncodeS3File(
                                    message.getSender().getEmail() + "/" + message.getAttachmentURL());
                            message.setAttachmentURL(encodedAttachmentFile);
                        }
                        return message;
                    })
                    .collect(Collectors.toList());

            // Mask sender password
            dbMessages = maskSenderPassword(dbMessages);

            return dbMessages;

        } catch (Exception e) {
            logger.error("Messages not found!");
            return Collections.emptyList();
        }
    }

    public void insertMessage(Message messageModel, String groupChatID, List<User> dbSender) {
        chatDBManager.executeUpdateQuery(insertStatement.INSERT_MESSAGE, messageModel.getContent(),
                messageModel.getAttachmentURL(), messageModel.getTimestamp(),
                dbSender.get(0).getId(), Integer.parseInt(groupChatID));
    }

    public boolean deleteMessageEQID(int messageID) {
        try {
            List<Message> dbMessage = chatDBManager
                    .getMessagesQuery(getRecord.GET_MESSAGE_EQ_ID, messageID);

            new S3Manager().deleteFile(dbMessage.get(0).getSender().getEmail() + "/"
                    + dbMessage.get(0).getAttachmentURL());

            return chatDBManager
                    .executeUpdateQuery(
                            deleteRecord.DeleteMessageEQID, messageID);

        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
        }
        return false;
    }

    private List<Message> maskSenderPassword(List<Message> dbMessages) {
        return dbMessages.stream()
                .map(message -> {
                    message.getSender().setPassowrd("");
                    return message;
                })
                .collect(Collectors.toList());
    }

}
