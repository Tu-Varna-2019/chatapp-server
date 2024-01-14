package controller.events.handlers.message;

import java.util.Base64;
import java.util.List;
import java.util.UUID;

import controller.events.handlers.shared.SharedEventHandler;
import model.Message;
import model.User;
import model.dataclass.ClientRequest;
import model.storage.S3Manager;

public class SendMessageEventHandler extends SharedEventHandler {

        @Override
        public String handleEvent(ClientRequest payload) {
                message = "Failed to send message. Please try again!";

                String groupChatID = payload.data.id;
                Message messageModel = payload.data.message;

                String fileAttachment = uploadAttachmentFile(messageModel.getAttachmentURL(),
                                messageModel.getSender().getEmail());

                // Set the binary image
                messageModel.setAttachmentURL(fileAttachment);

                List<User> dbSender = sharedUser.getUserIDByEmail(messageModel.getSender().getEmail());

                // Insert message
                sharedMessage.insertMessage(messageModel, groupChatID, dbSender);

                List<Message> dbMessages = sharedMessage.getMessages(groupChatID);

                if (!dbMessages.isEmpty()) {
                        status = "Success";
                        message = "Message inserted!";
                        dataResponse.messages = dbMessages;
                }
                return sendPayloadToClient();
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
