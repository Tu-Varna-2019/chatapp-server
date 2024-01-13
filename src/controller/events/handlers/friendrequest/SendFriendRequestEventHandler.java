package controller.events.handlers.friendrequest;

import java.util.List;

import controller.events.handlers.shared.SharedEventHandler;
import model.User;
import model.dataclass.ClientRequest;

public class SendFriendRequestEventHandler extends SharedEventHandler {

    private final String DEFAULT_STATUS = "Pending";

    @Override
    public String handleEvent(ClientRequest payload) {
        message = "Email does not exist!";

        String emailSender = payload.data.friendrequest.getSender().getEmail();
        String emailRecipient = payload.data.friendrequest.getRecipient().getEmail();

        List<User> dbSender = sharedUser.getUserIDByEmail(emailSender);
        List<User> dbRecipient = sharedUser.getUserIDByEmail(emailRecipient);

        if (!dbRecipient.isEmpty()) {
            status = "Success";
            message = "Invitation send to " + emailRecipient + " !";

            sharedFriendRequest.insertFriendRequest(DEFAULT_STATUS, dbSender.get(0).getId(),
                    dbRecipient.get(0).getId());
        }

        return sendPayloadToClient();

    }

}
