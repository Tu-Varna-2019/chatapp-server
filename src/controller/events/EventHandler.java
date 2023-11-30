package controller.events;

import org.json.JSONObject;

public interface EventHandler {
    public String handleEvent(JSONObject jsonPayload);

}
