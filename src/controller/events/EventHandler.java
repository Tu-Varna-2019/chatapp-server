package controller.events;

import java.util.TreeMap;

public interface EventHandler {
    public String handleEvent(TreeMap<String, String> payload);

}
