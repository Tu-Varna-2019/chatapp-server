package controller.events.handlers.message;

import controller.events.handlers.shared.SharedEventHandler;
import model.dataclass.ClientRequest;

public class DeleteMessageEventHandler extends SharedEventHandler {

        @Override
        public String handleEvent(ClientRequest payload) {
                message = "Failed to delete message. Please try again!";

                String messageID = payload.data.id;

                boolean isMessageDeleted = sharedMessage.deleteMessageEQID(Integer.parseInt(messageID));

                if (isMessageDeleted) {
                        status = "Success";
                        message = "Message deleted!";
                }

                return sendPayloadToClient();

        }
}
