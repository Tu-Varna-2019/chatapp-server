package com.chatapp.controller.events;

import com.chatapp.model.dataclass.ClientRequest;

public interface EventHandler {
    public String handleEvent(ClientRequest payload);

}
