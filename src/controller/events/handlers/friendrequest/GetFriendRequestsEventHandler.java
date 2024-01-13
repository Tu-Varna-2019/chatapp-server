package controller.events.handlers.friendrequest;

import java.util.List;

import controller.events.handlers.shared.SharedEventHandler;
import model.FriendRequest;
import model.User;
import model.dataclass.ClientRequest;

public class GetFriendRequestsEventHandler extends SharedEventHandler {

        @Override
        public String handleEvent(ClientRequest payload) {

                String email = payload.data.user.getEmail();
                // JSON string formatter for final result
                StringBuilder friendRequestJSON = new StringBuilder();
                List<FriendRequest> dbRetrievedFriendRequest = null;

                try {
                        List<User> dbRetrievedUser = chatDBManager.getUsersQuery(getRecord.getUserEQEmail(email));

                        logger.info("Retrieved user: " + dbRetrievedUser.get(0).toString());

                        // Get all group chats, that the authenticated user is currently in
                        dbRetrievedFriendRequest = chatDBManager
                                        .getFriendRequestQuery(
                                                        getRecord.getFriendRequestEQSenderID(
                                                                        dbRetrievedUser.get(0).getId()));

                        for (FriendRequest friendRequest : dbRetrievedFriendRequest) {
                                friendRequestJSON.append(friendRequest.toString() + ",");
                        }

                        // Remove the last comma from the JSON string
                        friendRequestJSON.delete(friendRequestJSON.length() - 1, friendRequestJSON.length());
                } catch (Exception e) {
                        logger.error("Got FriendRequest Error: {}" + e.getMessage());
                }
                String status = dbRetrievedFriendRequest == null ? "Failed" : "Success";
                String message = dbRetrievedFriendRequest == null ? "Empty friend request!"
                                : "Found friend requests with the authenticated user!";

                return (String.format(
                                "{\"response\":{\"status\":\"%s\",\"message\":\"%s\", \"friendrequests\":[%s]}}",
                                status, message, friendRequestJSON));
        }
}
