package controller.events;

import model.dataclass.ClientResponse;

public interface EventHandler {
    public String handleEvent(ClientResponse payload);

}
