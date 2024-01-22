package controller.events.handlers.shared;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import model.FriendRequest;
import model.User;

public class FriendRequestSharedEvent extends SharedEventValues {

    private static final String STATUS_ACCEPTED = "Accepted";
    private static final String STATUS_PENDING = "Pending";
    private static final String STATUS_REJECTED = "Rejected";

    public List<FriendRequest> getFriendRequestEQSenderID(int senderID, FriendRequest filterFriendRequest) {

        List<FriendRequest> dbFriendRequest = null;
        try {
            String filteredGetRecord = filterGetRecord(filterFriendRequest);

            // Temporary work
            if (!filterFriendRequest.getStatus().equals(STATUS_ACCEPTED))
                dbFriendRequest = chatDBManager
                        .getFriendRequestQuery(
                                filteredGetRecord, senderID);
            else
                dbFriendRequest = chatDBManager
                        .getFriendRequestQuery(
                                filteredGetRecord, senderID, senderID);

            // Check if the status is accepted to get all friend requests that are accepted
            // and move them in recipient field
            if (filterFriendRequest.getStatus().equals(STATUS_ACCEPTED)
                    || filteredGetRecord.equals(getRecord.GET_FRIENDREQUEST_EQ__PENDING_EQ_RECIPIENT_ID))
                dbFriendRequest = filterFriendRequestNEQAuthUser(dbFriendRequest, senderID);

            logger.info("Retrieved friends: " + dbFriendRequest.get(0).toString());

            return dbFriendRequest;
        } catch (Exception e) {
            logger.error("Friends not found !");
            return Collections.emptyList();
        }
    }

    public List<FriendRequest> getFriendRequestEQSenderID(int senderID) {

        String noneFilteredGetRecord = getRecord.GET_FRIENDREQUEST_EQ_SENDER_ID;

        List<FriendRequest> dbFriendRequest = null;
        try {
            dbFriendRequest = chatDBManager
                    .getFriendRequestQuery(
                            noneFilteredGetRecord, senderID);

            logger.info("Retrieved sender: " + dbFriendRequest.get(0).toString());
        } catch (Exception e) {
            logger.error("getFriendRequestEQSenderID Error: {}", e.getMessage());
        }
        return dbFriendRequest;
    }

    public String checkIfFriendRequestExistsEQSenderRecipientID(int senderid, int recipientid) {

        List<FriendRequest> dbFriendRequest = null;
        String[] status = { STATUS_PENDING, STATUS_ACCEPTED, STATUS_REJECTED };

        for (String statusIterator : status) {

            dbFriendRequest = chatDBManager
                    .getFriendRequestQuery(
                            getRecord.GET_FRIENDREQUEST_EQ_SENDER_ID_OR_RECIPIENT_ID, senderid, recipientid,
                            recipientid, senderid,
                            statusIterator);

            if (!dbFriendRequest.isEmpty()) {
                if (statusIterator.equals(STATUS_PENDING))
                    return "You have already sent the friend request invitation. Status: " + statusIterator;
                else if (statusIterator.equals(STATUS_ACCEPTED))
                    return "You are already friends with the user. Status: " + statusIterator;
                else if (statusIterator.equals(STATUS_REJECTED)) {
                    chatDBManager
                            .executeUpdateQuery(
                                    updateRecord.UPDATE_FRIENDREQUEST_STATUS_EQ_ID, STATUS_PENDING,
                                    dbFriendRequest.get(0).getId());
                    return "User already rejected the friend request invitation. Now setting it back to Pending";
                }
            }
        }
        return "";
    }

    public void insertFriendRequest(String status, int senderID, int recipientID) {
        chatDBManager.executeUpdateQuery(insertStatement.INSERT_FRIEND_REQUEST, status, senderID, recipientID);
    }

    private String filterGetRecord(FriendRequest filterFriendRequest) {

        // Filter by status
        switch (filterFriendRequest.getStatus()) {
            case STATUS_PENDING:
                // Get friend requests that are pending for the sender
                if (!filterFriendRequest.getSender().getEmail().isEmpty()) {
                    return getRecord.GET_FRIENDREQUEST_EQ__PENDING_EQ_SENDER_ID;
                }
                // Get friend requests that are pending for the recipient
                else
                    return getRecord.GET_FRIENDREQUEST_EQ__PENDING_EQ_RECIPIENT_ID;

            case STATUS_ACCEPTED:
                return getRecord.GET_FRIENDREQUEST_EQ_ACCEPTED_EQ_SENDER_ID;

            default:
                throw new IllegalArgumentException("Invalid status: " + filterFriendRequest.getStatus());
        }

    }

    private List<FriendRequest> filterFriendRequestNEQAuthUser(List<FriendRequest> dbFriendRequest, int authUserID) {
        List<FriendRequest> filteredFriendRequests = new ArrayList<>();
        User retrievedUser = null;

        for (FriendRequest friendRequest : dbFriendRequest) {

            if (friendRequest.getSender().getId() != authUserID)
                retrievedUser = friendRequest.getSender();
            else
                retrievedUser = friendRequest.getRecipient();

            filteredFriendRequests.add(new FriendRequest(friendRequest.getId(), friendRequest.getStatus(),
                    new User(0, "", "", ""), retrievedUser));
        }

        return filteredFriendRequests;
    }

    public boolean updateStatusFriendRequest(String status, int friendrequestid) {
        try {
            return chatDBManager
                    .executeUpdateQuery(
                            updateRecord.UPDATE_FRIENDREQUEST_STATUS_EQ_ID, status, friendrequestid);

        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
        }
        return false;
    }

    public boolean deleteFriendRequestEQID(int friendrequestid) {
        try {
            return chatDBManager
                    .executeUpdateQuery(
                            deleteRecord.DeleteFriendRequestEQID, friendrequestid);

        } catch (Exception e) {
            logger.error("Error: {}", e.getMessage());
        }
        return false;
    }

}
