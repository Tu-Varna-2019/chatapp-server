package controller.events.handlers.shared;

import java.util.List;
import java.util.stream.Collectors;

import controller.helpers.MaskData;
import model.Message;
import model.User;
import model.storage.S3Manager;

public class MessageSharedEvent extends SharedEventValues {

    public List<Message> getMessages(String groupChatID) {
        List<Message> dbMessages = null;
        try {
            dbMessages = chatDBManager
                    .getMessagesQuery(getRecord.getMessageEQGroupID(Integer.parseInt(groupChatID)));

            logger.info("Retrieved message: " + dbMessages.get(0).toString());

            dbMessages = dbMessages.stream()
                    .peek(message -> {
                        if (message.getAttachmentURL() == null || message.getAttachmentURL().isEmpty()) {
                            message.setAttachmentURL("");
                        } else {
                            String encodedAttachmentFile = MaskData.base64EncodeS3File(
                                    message.getSender().getEmail() + "/" + message.getAttachmentURL());
                            message.setAttachmentURL(encodedAttachmentFile);
                        }
                    })
                    .collect(Collectors.toList());

        } catch (Exception e) {
            logger.error("getMessages Error: {}", e.getMessage());
        }
        // Mask sender password
        dbMessages = maskSenderPassword(dbMessages);

        return dbMessages;
    }

    public void insertMessage(Message messageModel, String groupChatID, List<User> dbSender) {
        chatDBManager.insertQuery(insertStatement.INSERT_MESSAGE, messageModel.getContent(),
                messageModel.getAttachmentURL(), messageModel.getTimestamp(),
                dbSender.get(0).getId(), Integer.parseInt(groupChatID));
    }

    public boolean deleteMessageEQID(int messageID) {
        try {
            List<Message> dbMessage = chatDBManager
                    .getMessagesQuery(getRecord.getMessageEQID(messageID));

            S3Manager.deleteFile(dbMessage.get(0).getSender().getEmail() + "/"
                    + dbMessage.get(0).getAttachmentURL());

            boolean isDeleted = chatDBManager
                    .updateRecordQuery(
                            deleteRecord.DeleteMessageEQID(messageID));

            return isDeleted;
        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
        }
        return false;
    }

    private List<Message> maskSenderPassword(List<Message> dbMessages) {
        return dbMessages.stream()
                .peek(message -> {
                    message.getSender().setPassowrd("");
                })
                .collect(Collectors.toList());
    }

}
