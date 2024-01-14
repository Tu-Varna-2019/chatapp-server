package controller.events.handlers.shared;

import java.util.List;

import model.FriendRequest;

public class FriendRequestSharedEvent extends SharedEventValues {

    public List<FriendRequest> getFriendRequestEQSenderID(int senderID, FriendRequest filterFriendRequest) {

        String filteredGetRecord = filterGetRecord(filterFriendRequest, senderID);

        List<FriendRequest> dbFriendRequest = null;
        try {
            dbFriendRequest = chatDBManager
                    .getFriendRequestQuery(
                            filteredGetRecord);

            logger.info("Retrieved sender: " + dbFriendRequest.get(0).toString());
        } catch (Exception e) {
            logger.error("getUserIDByEmail Error: {}", e.getMessage());
        }
        return dbFriendRequest;
    }

    public List<FriendRequest> getFriendRequestEQSenderID(int senderID) {

        String noneFilteredGetRecord = getRecord.getFriendRequestEQSenderID(senderID);

        List<FriendRequest> dbFriendRequest = null;
        try {
            dbFriendRequest = chatDBManager
                    .getFriendRequestQuery(
                            noneFilteredGetRecord);

            logger.info("Retrieved sender: " + dbFriendRequest.get(0).toString());
        } catch (Exception e) {
            logger.error("getUserIDByEmail Error: {}", e.getMessage());
        }
        return dbFriendRequest;
    }

    public void insertFriendRequest(String status, int senderID, int recipientID) {
        chatDBManager.insertQuery(insertStatement.INSERT_FRIEND_REQUEST, status, senderID, recipientID);
    }

    private String filterGetRecord(FriendRequest filterFriendRequest, int senderID) {

        // Filter by status
        switch (filterFriendRequest.getStatus()) {
            case "Pending":
                // Get friend requests that are pending for the sender
                if (!filterFriendRequest.getSender().getEmail().isEmpty())
                    return getRecord.getFriendRequestPendingEQSenderID(senderID);
                // Get friend requests that are pending for the recipient
                else
                    return getRecord.getReceivedFriendRequests(senderID);

            case "Accepted":
                return getRecord.getFriendRequestAcceptedEQSenderID(senderID);

            default:
                return getRecord.getFriendRequestEQSenderID(senderID);
        }

    }

}
