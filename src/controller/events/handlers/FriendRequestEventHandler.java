package controller.events.handlers;

import controller.events.SharedDataEventHandler;
import model.User;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class FriendRequestEventHandler extends SharedDataEventHandler {

    JSONObject jsonObject;
    JSONArray jsonArrayFRUser;
    JSONArray jsonArrayAuthUser;
    @Override
    public String handleEvent(String... args) {

        String emailSender = args[1];
        String emailReceiver = args[0];
        logger.info("Sender email: {} \n}", emailSender);
        logger.info("Received email: {} \n}", emailReceiver);

        List<User> dbRetrievedAuthUser = chatDBManager.getUsersQuery(getRecord.getUserEQEmail(emailSender));

        List<User> dbReceivedFRUser = chatDBManager.getUsersQuery(getRecord.getUserEQEmail(emailReceiver));

        if(!dbReceivedFRUser.isEmpty()){
            try {

                int retrievedAuthUserID = dbRetrievedAuthUser.get(0).getId();
                logger.info("Auth user id: " + retrievedAuthUserID);


                int retrievedFRUserID = dbReceivedFRUser.get(0).getId();
                logger.info("Retrieved user id: " + retrievedFRUserID);

                String status = "Pending";
                chatDBManager.insertQueryFriendRequest(insertStatement.INSERT_FRIEND_REQUEST, status, retrievedAuthUserID, retrievedFRUserID);

                return "{\"response\":{\"status\":\"Success\",\"message\":\"Email exists!\"}}";

            } catch (Exception e) {
                return "Error: {}" + e.getMessage();
            }
        } else {
            return "{\"response\":{\"status\":\"Failed\",\"message\":\"Email does not exist!\"}}";
        }
    }
}
