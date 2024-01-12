package controller.events.handlers.friendrequest;

import java.util.List;

import controller.events.handlers.shared.SharedEventHandler;
import model.User;
import model.dataclass.ClientResponse;

public class SendFriendRequestEventHandler extends SharedEventHandler {

    private final String DEFAULT_STATUS = "Pending";

    @Override
    public String handleEvent(ClientResponse payload) {

        String emailSender = payload.data.emailSender;
        String emailRecipient = payload.data.emailRecipient;

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
