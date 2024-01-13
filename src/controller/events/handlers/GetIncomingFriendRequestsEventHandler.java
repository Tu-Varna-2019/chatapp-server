package controller.events.handlers;

import java.util.List;

import controller.events.handlers.shared.SharedEventHandler;
import model.FriendRequest;
import model.User;
import model.dataclass.ClientRequest;

public class GetIncomingFriendRequestsEventHandler extends SharedEventHandler {
    @Override
    public String handleEvent(ClientRequest payload) {

        String email = payload.data.user.getEmail();
        // JSON string formatter for final result
        StringBuilder friendRequestJSON = new StringBuilder();
        List<FriendRequest> dbPendingFriendRequest = null;

        try {
            List<User> dbRetrievedUser = chatDBManager.getUsersQuery(getRecord.getUserEQEmail(email));

            logger.info("Retrieved user: " + dbRetrievedUser.get(0).toString());

            // Get all group chats, that the authenticated user is currently in
            dbPendingFriendRequest = chatDBManager
                    .getFriendRequestQuery(
                            getRecord.getIncomingFriendRequests(
                                    dbRetrievedUser.get(0).getId()));

            for (FriendRequest friendRequest : dbPendingFriendRequest) {
                friendRequestJSON.append(friendRequest.toString() + ",");
            }

            // Remove the last comma from the JSON string
            friendRequestJSON.delete(friendRequestJSON.length() - 1, friendRequestJSON.length());
        } catch (Exception e) {
            logger.error("Got FriendRequest Error: {}" + e.getMessage());
        }
        String status = dbPendingFriendRequest == null ? "Failed" : "Success";
        String message = dbPendingFriendRequest == null ? "Empty friend request!"
                : "Found pending friend requests";

        return (String.format(
                "{\"response\":{\"status\":\"%s\",\"message\":\"%s\", \"friendrequests\":[%s]}}",
                status, message, friendRequestJSON));
    }
}
