package controller.events.handlers.friendrequest;

import controller.events.handlers.shared.SharedEventHandler;
import model.dataclass.ClientRequest;

public class FriendRequestOperation extends SharedEventHandler {

    /*
     * {
     * "eventType": "FriendRequestOperation",
     * "data": {
     * "friendrequest": {
     * "id": 30,
     * "status": "Accepted" OR "Rejected" <=== Required
     * "recipient": {
     * "id": 69,
     * "username": "q",
     * "email": "q@q.bg",
     * "password": "***"
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
