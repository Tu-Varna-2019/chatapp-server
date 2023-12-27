package controller.events;

import org.apache.logging.log4j.Logger;

import controller.helpers.MaskData;

import java.util.List;
import org.apache.logging.log4j.LogManager;

import model.Message;
import model.database.ChatDBManager;
import model.database.sql_statements.DeleteRecord;
import model.database.sql_statements.GetRecord;
import model.database.sql_statements.InsertRecord;
import model.database.sql_statements.UpdateRecord;
import view.SocketConnection;

public abstract class SharedDataEventHandler implements EventHandler {

    protected static final Logger logger = LogManager
            .getLogger(SocketConnection.class.getName());

    protected static final ChatDBManager chatDBManager = ChatDBManager.getInstance();
    protected InsertRecord insertStatement = new InsertRecord();
    protected GetRecord getRecord = new GetRecord();
    protected DeleteRecord deleteRecord = new DeleteRecord();
    protected UpdateRecord updateRecord = new UpdateRecord();

    public StringBuilder getMessages(String groupChatID) {
        StringBuilder messagesJSON = new StringBuilder();

        try {
            List<Message> dbRetrievedMessage = chatDBManager
                    .getMessagesQuery(getRecord.getMessageEQGroupID(Integer.parseInt(groupChatID)));

            logger.info("Retrieved message: " + dbRetrievedMessage.get(0).toString());

            for (Message message : dbRetrievedMessage) {

                String encodedAttachmentFile = MaskData.base64EncodeS3File(
                        message.getSender().getEmail() + "/"
                                + message.getAttachmentURL());

                message.setAttachmentURL(encodedAttachmentFile);
                // Add the current message to the JSON string
                messagesJSON.append(message.toString() + ",");
            }
            // Remove the last comma from the JSON string
            messagesJSON.delete(messagesJSON.length() - 1, messagesJSON.length());

        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
        }
        return messagesJSON;
    }
}
