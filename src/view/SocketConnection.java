package view;

import org.json.JSONObject;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Logger;

import controller.events.EventHandler;
import controller.events.EventHandlerRegistry;
import controller.helpers.Utils;

public class SocketConnection {

    private static final Logger logger = Logger.getLogger(SocketConnection.class.getName());

    private final int PORT_NUMBER = 8081;
    private static SocketConnection instance;
    private static Socket clientSocket;

    public static SocketConnection getInstance() {
        if (instance == null) {
            instance = new SocketConnection();
        }
        return instance;
    }

    SocketConnection() {
        try (ServerSocket serverSocket = new ServerSocket(PORT_NUMBER)) {
            logger.info("Server is listening on port " + PORT_NUMBER);

            while (true) {
                clientSocket = serverSocket.accept();
                logger.info("Client connected: " + clientSocket.getInetAddress());

                new Thread(() -> handleClientRequest()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClientRequest() {
        try {
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String payload = reader.readLine();
            JSONObject jsonPayload = new JSONObject(payload);
            logger.info("Received payload: " + payload);

            String eventType = Utils.base64Decode(jsonPayload.getString("encodedEventType"));
            logger.info("Event type received: " + eventType);

            EventHandler eventHandler = EventHandlerRegistry.getEventHandler(eventType);

            if (eventHandler != null) {

                String response = eventHandler.handleEvent(jsonPayload);

                logger.info("Response: " + response);
                writer.write(response);
                writer.newLine();
                writer.flush();
            } else {
                logger.info("Error, unknown event: " + eventHandler);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
