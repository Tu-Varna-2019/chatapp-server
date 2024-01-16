package controller.events.handlers.friendrequest;

import controller.events.handlers.shared.SharedEventHandler;
import model.User;
import model.database.ChatDBManager;
import model.database.sql_statements.UpdateRecord;
import model.dataclass.ClientRequest;

import java.util.List;

public class FriendRequestResponseEventHandler extends SharedEventHandler {

    @Override
    public String handleEvent(ClientRequest payload) {

        String friendrequestStatus = payload.data.friendrequest.getStatus();
        int friendRequestId = payload.data.friendrequest.getId();

        boolean isUpdateSucceded = sharedFriendRequest.updateStatusFriendRequest(friendrequestStatus, friendRequestId);

        if (isUpdateSucceded) {
            status = "Success";
            message = "Friend request " + friendrequestStatus +" !";
        }

        return sendPayloadToClient();
    }
}
