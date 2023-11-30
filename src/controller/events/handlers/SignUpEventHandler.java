package controller.events.handlers;

import org.json.JSONObject;

import controller.events.SharedDataEventHandler;
import controller.helpers.Utils;

public class SignUpEventHandler extends SharedDataEventHandler {

    @Override
    public String handleEvent(JSONObject jsonPayload) {

        String username = Utils.base64Decode(jsonPayload.getString("encodedUserName"));
        String email = Utils.base64Decode(jsonPayload.getString("encodedEmail"));
        String password = Utils.base64Decode(jsonPayload.getString("encodedPassword"));

        logger.info("UserName: " + username);
        logger.info("Email: " + email);
        logger.info("Password: " + password);

        chatDBManager.insertQuery(insertStatement.INSERT_USER, username, email, password);

        return "User has registered";

    }

}
