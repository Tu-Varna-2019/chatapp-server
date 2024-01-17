package controller.events.handlers.friendrequest;

import controller.events.handlers.shared.SharedEventHandler;
import model.dataclass.ClientRequest;

public class FriendRequestOperation extends SharedEventHandler {
    @Override
    public String handleEvent(ClientRequest payload) {

        String friendrequestStatus = payload.data.friendrequest.getStatus();
        Integer friendrequestid = payload.data.friendrequest.getId();

        boolean isUpdateSucceded = sharedFriendRequest.updateStatusFriendRequest(friendrequestStatus,
                friendrequestid);

        if (isUpdateSucceded) {
            status = "Success";
            message = "Friend request " + friendrequestStatus + " !";
        }

        return sendPayloadToClient();
    }
}
