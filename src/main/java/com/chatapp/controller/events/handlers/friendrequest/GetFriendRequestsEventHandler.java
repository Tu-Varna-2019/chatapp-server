package com.chatapp.controller.events.handlers.friendrequest;

import java.util.List;

import com.chatapp.controller.events.handlers.shared.SharedEventHandler;
import com.chatapp.model.FriendRequest;
import com.chatapp.model.User;
import com.chatapp.model.dataclass.ClientRequest;

public class GetFriendRequestsEventHandler extends SharedEventHandler {
        /*
         * Expected payload format:
         *
         * For getting all friend requests, that have Accepted status, either on the
         * sender or reciever side:
         * {
         * {
         * "eventType":"GetFriendRequests","
         * data":{
         * "id":"",
         * "user":{
         * "id":0,
         * "username":"me2",
         * "email":"me@me.bg",
         * "password":""}
         * },
         * "filter":{
         * "friendrequest":{
         * "id":0,
         * "status":"Accepted",
         * "recipient":{"id":0,"username":"","email":"","password":""},
         * "sender":{"id":0,"username":"","email":"","password":""}
         * }}}
         *
         * ------------------------------------------------------------
         * * For getting all friend requests, that have Pending status, that the auth
         * user is the sender (If you want to get all friend requests, that the auth
         * user is the recipient, just change the sender email to be empty and full the
         * recipient email with something):
         * {
         * "eventType": "GetFriendRequests",
         * "data": {
         * "id": "",
         * "user": {
         * "id": 0,
         * "username": "me2",
         * "email": "me@me.bg",
         * "password": ""
         * }
         * },
         * "filter": {
         * "friendrequest": {
         * "id": 0,
         * "status": "Pending",
         * "recipient": {
         * "id": 0,
         * "username": "",
         * "email": "",
         * "password": ""
         * },
         * "sender": {
         * "id": 0,
         * "username": "",
         * "email": "Non empty", // This is the only field that is not empty for sender
         * "password": ""
         * }
         * "recipient": {
         * "id": 0,
         * "username": "",
         * "email": "", // EMPTY!! This is the only field that shouldn't be empty for
         * recipient
         * "password": ""
         * }
         * }}}
         */

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
                        } else {
                                status = "Error";
                                if (dataResponse.friendrequests != null)
                                        dataResponse.friendrequests.clear();
                        }
                }

                return sendPayloadToClient();
        }
}
