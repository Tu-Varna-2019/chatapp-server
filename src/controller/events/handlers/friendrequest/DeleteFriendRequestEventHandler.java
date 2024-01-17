package controller.events.handlers.friendrequest;

import controller.events.handlers.shared.SharedEventHandler;
import model.dataclass.ClientRequest;

public class DeleteFriendRequestEventHandler extends SharedEventHandler {

    /*
     * Expected payload format:
     *
     * For getting all friend requests, that have Accepted status, either on the
     * sender or reciever side:
     * {
     * "eventType": "DeleteFriendRequest",
     * "data": {
     * "friendrequest": {
     * "id": 29, <============== REQUIRED
     * "status": "Accepted",
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
        message = "Deletion of friend request failed. Please try again!";

        Integer friendrequestid = payload.data.friendrequest.getId();

        boolean isFriendRequestDeleted = sharedFriendRequest.deleteFriendRequestEQID(friendrequestid);

        if (isFriendRequestDeleted) {
            status = "Success";
            message = "Friend request removed successfully!";
        }

        return sendPayloadToClient();
    }
}
