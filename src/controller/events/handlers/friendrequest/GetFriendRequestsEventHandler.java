package controller.events.handlers.friendrequest;

import java.util.List;

import controller.events.handlers.shared.SharedEventHandler;
import model.FriendRequest;
import model.User;
import model.dataclass.ClientRequest;

public class GetFriendRequestsEventHandler extends SharedEventHandler {

        @Override
        public String handleEvent(ClientRequest payload) {
                message = "Friend requests not found!";

                String email = payload.data.user.getEmail();

                if (!email.isEmpty()) {

                        List<User> dbUser = sharedUser.getUserIDByEmail(email);
                        // Get all friend requests, that the authenticated user is currently in
                        List<FriendRequest> dbFriendRequest;
                        // nested if statements to check if the filter is null, and the || operator
                        // won't be evaluated, for throwing a NullPointerException
                        if (payload.filter == null || (payload.filter != null && payload.filter.friendrequest == null))
                                dbFriendRequest = sharedFriendRequest
                                                .getFriendRequestEQSenderID(dbUser.get(0).getId());
                        else
                                dbFriendRequest = sharedFriendRequest.getFriendRequestEQSenderID(
                                                dbUser.get(0).getId(), payload.filter.friendrequest);

                        if (!dbFriendRequest.isEmpty()) {
                                status = "Success";
                                message = "Friend request found!";
                                dataResponse.friendrequests = dbFriendRequest;
                        }
                }

                return sendPayloadToClient();
        }
}
