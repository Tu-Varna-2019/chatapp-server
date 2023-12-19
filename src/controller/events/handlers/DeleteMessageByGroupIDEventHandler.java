package controller.events.handlers;

import java.util.TreeMap;

import controller.events.SharedDataEventHandler;

public class DeleteMessageByGroupIDEventHandler extends SharedDataEventHandler {

        @Override
        public String handleEvent(TreeMap<String, String> payload) {

                String messageID = payload.get("id");
                try {
                        boolean isDeleted = chatDBManager
                                        .updateRecordQuery(
                                                        deleteRecord.DeleteMessageEQID(Integer.parseInt(messageID)));

                        String status = !isDeleted ? "Failed" : "Success";
                        String message = !isDeleted ? "Unable to delete message!" : "Successfully deleted message!";

                        return String.format(
                                        "{\"response\":{\"status\":\"%s\",\"message\":\"%s\"}}",
                                        status,
                                        message);

                } catch (Exception e) {
                        logger.error("Error: {}", e.getMessage());
                }

                return (String.format(
                                "{\"response\":{\"status\":\"%s\",\"message\":\"%s\", \"messages\":[%s]}}",
                                "Failed", "Error, please try again!", ""));
        }
}
