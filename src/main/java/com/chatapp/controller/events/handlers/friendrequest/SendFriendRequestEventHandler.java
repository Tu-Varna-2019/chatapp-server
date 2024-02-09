package com.chatapp.controller.events.handlers.friendrequest;

import java.util.List;

import com.chatapp.controller.events.handlers.shared.SharedEventHandler;
import com.chatapp.model.User;
import com.chatapp.model.dataclass.ClientRequest;

public class SendFriendRequestEventHandler extends SharedEventHandler {
    /*
     * Expected payload format:
     *
     * {
     * "eventType": "SendFriendRequest",
     * "data": {
     * "friendrequest": {
     * "id": 0,
     * "status": "Pending",
     * "recipient": {
     * "id": 0,
     * "username": "",
     * "email": "qq@qq.bg",
     * "password": ""
     * },
     * "sender": {
     * "id": 0,
     * "username": "me2",
     * "email": "me@me.bg",
     * "password": ""
     * }
     * }
     * }
     * }
     */

    private static final String DEFAULT_STATUS = "Pending";

    @Override
    public String handleEvent(ClientRequest payload) {
        message = "Email does not exist!";

        String emailSender = payload.data.friendrequest.getSender().getEmail();
        String emailRecipient = payload.data.friendrequest.getRecipient().getEmail();

        List<User> dbSender = sharedUser.getUserIDByEmail(emailSender);
        List<User> dbRecipient = sharedUser.getUserIDByEmail(emailRecipient);

        if (!dbRecipient.isEmpty()) {

            String checkForExistingFQMessage = sharedFriendRequest
                    .checkIfFriendRequestExistsEQSenderRecipientID(
                            dbSender.get(0).getId(), dbRecipient.get(0).getId());

            // Check if the friend request already exists
            if (!checkForExistingFQMessage.isEmpty())
                message = checkForExistingFQMessage;
            else {
                status = "Success";
                message = "Invitation send to " + emailRecipient + " !";
                sharedFriendRequest.insertFriendRequest(DEFAULT_STATUS, dbSender.get(0).getId(),
                        dbRecipient.get(0).getId());
            }
        }

        return sendPayloadToClient();

    }

}
