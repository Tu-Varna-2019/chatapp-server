package controller.events.handlers;

import controller.events.SharedDataEventHandler;
import controller.helpers.MaskData;

public class SignUpEventHandler extends SharedDataEventHandler {

    @Override
    public String handleEvent(String... args) {

        String username = args[0];
        String email = args[1];
        String password = MaskData.hashPassword(args[2]);

        logger.info("UserName: {} \nEmail: {} \nPassword: {}", username, email, password);

        chatDBManager.insertQuery(insertStatement.INSERT_USER, username, email, password);

        return "User has registered";

    }
}
