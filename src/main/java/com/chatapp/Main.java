package com.chatapp;

import com.chatapp.view.WebSocketConnection;

public class Main {
    public static void main(String[] arg) {

        WebSocketConnection WebSocketConnection = WebSocketConnection.getInstance();
        WebSocketConnection.startServer();
    }
}
