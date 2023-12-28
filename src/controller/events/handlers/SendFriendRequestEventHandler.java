package controller.events.handlers;

import controller.events.SharedDataEventHandler;
import model.User;
import java.util.List;
import java.util.TreeMap;

public class SendFriendRequestEventHandler extends SharedDataEventHandler {

    private final String DEFAULT_STATUS = "Pending";

    @Override
    public String handleEvent(TreeMap<String, String> payload) {

        String emailSender = payload.get("emailSender");
        String emailRecipient = payload.get("emailRecipient");

        logger.info("Sender email: {}\n Recipient email: {}\n", emailSender, emailRecipient);

        List<User> dbSenderAuthUser = chatDBManager.getUsersQuery(getRecord.getUserEQEmail(emailSender));
        List<User> dbRecipientUser = chatDBManager.getUsersQuery(getRecord.getUserEQEmail(emailRecipient));

        if (!dbRecipientUser.isEmpty()) {

            try {
                chatDBManager.insertQuery(insertStatement.INSERT_FRIEND_REQUEST, DEFAULT_STATUS,
                        dbSenderAuthUser.get(0).getId(), dbRecipientUser.get(0).getId());

                return (String.format("{\"response\":{\"status\":\"Success\",\"message\":\"Invitation send to %s\"}}",
                        emailRecipient));

            } catch (Exception e) {
                return "Error in SendFriendRequestEventHandler: {}" + e.getMessage();
            }
        } else {
            return "{\"response\":{\"status\":\"Failed\",\"message\":\"Email does not exist!\"}}";
        }
    }
}
