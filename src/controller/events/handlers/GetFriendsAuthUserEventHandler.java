package controller.events.handlers;

import model.FriendRequest;
import model.User;

import java.util.List;
import java.util.TreeMap;

import controller.events.handlers.shared.SharedEventHandler;

public class GetFriendsAuthUserEventHandler extends SharedEventHandler {

    @Override
    public String handleEvent(TreeMap<String, String> payload) {
        String email = payload.get("email");
        // JSON string formatter for final result
        StringBuilder friendRequestJSON = new StringBuilder();
        List<FriendRequest> dbFriendsAuthUser = null;

        try {
            List<User> dbRetrievedUser = chatDBManager.getUsersQuery(getRecord.getUserEQEmail(email));

            logger.info("Retrieved user: " + dbRetrievedUser.get(0).toString());

            // Get all group chats, that the authenticated user is currently in
            dbFriendsAuthUser = chatDBManager
                    .getFriendRequestQuery(
                            getRecord.getFriendRequestAcceptedEQSenderID(
                                    dbRetrievedUser.get(0).getId()));

            for (FriendRequest friendRequest : dbFriendsAuthUser) {
                friendRequestJSON.append(friendRequest.toString() + ",");
            }

            // Remove the last comma from the JSON string
            friendRequestJSON.delete(friendRequestJSON.length() - 1, friendRequestJSON.length());
        } catch (Exception e) {
            logger.error("Got FriendRequest Error: {}" + e.getMessage());
        }
        String status = dbFriendsAuthUser == null ? "Failed" : "Success";
        String message = dbFriendsAuthUser == null ? "Empty friend request!"
                : "Found friends for auth user";

        return (String.format(
                "{\"response\":{\"status\":\"%s\",\"message\":\"%s\", \"friendrequests\":[%s]}}",
                status, message, friendRequestJSON));
    }
}
