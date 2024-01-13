package controller.events.handlers.shared;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import controller.events.EventHandler;
import model.dataclass.ClientResponse;
import model.dataclass.DataResponse;

public abstract class SharedEventHandler implements EventHandler {

    protected String status = "Failed";
    protected String message = "Error! Please try again!";

    protected DataResponse dataResponse = new DataResponse();

    protected MessageSharedEvent sharedMessage = new MessageSharedEvent();
    protected UserSharedEvent sharedUser = new UserSharedEvent();
    protected FriendRequestSharedEvent sharedFriendRequest = new FriendRequestSharedEvent();
    protected GroupChatSharedEvent sharedGroupChat = new GroupChatSharedEvent();

    protected String sendPayloadToClient() {
        ClientResponse clientResponse = new ClientResponse(status, message);
        clientResponse.data = dataResponse;
        ObjectMapper objectMapper = new ObjectMapper();

        String jsonPayload = "";
        try {
            jsonPayload = objectMapper.writeValueAsString(clientResponse);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonPayload;
    }

}
