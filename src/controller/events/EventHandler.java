package controller.events;

import model.dataclass.ClientRequest;

public interface EventHandler {
    public String handleEvent(ClientRequest payload);

}
