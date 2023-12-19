package controller.events.handlers;

import java.util.List;
import java.util.TreeMap;

import controller.events.SharedDataEventHandler;
import model.FriendRequest;
import model.User;

public class GetFriendRequestsAuthUserEventHandler extends SharedDataEventHandler {

        @Override
        public String handleEvent(TreeMap<String, String> payload) {
                String email = payload.get("email");
                // JSON string formatter for final result
                StringBuilder friendRequestJSON = new StringBuilder();
                List<FriendRequest> dbRetrievedFriendRequest = null;
                logger.info("\nEmail: {}", email);

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
                } catch (

                Exception e) {
                        System.out.println("Error: {}" + e.getMessage());
                }
                // Remove the last comma from the JSON string
                friendRequestJSON.delete(friendRequestJSON.length() - 1, friendRequestJSON.length());
                String status = dbRetrievedFriendRequest == null ? "Failed" : "Success";
                String message = dbRetrievedFriendRequest == null ? "Empty friend request!"
                                : "Found friend requests with the authenticated user!";

                return (String.format(
                                "{\"response\":{\"status\":\"%s\",\"message\":\"%s\", \"friendrequests\":[%s]}}",
                                status, message, friendRequestJSON));
        }
}
