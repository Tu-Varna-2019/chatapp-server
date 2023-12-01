package view;

import org.json.JSONObject;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import controller.events.EventHandler;
import controller.events.EventHandlerRegistry;
import controller.helpers.MaskData;

public class SocketConnection {

    private static final Logger logger = LogManager.getLogger(SocketConnection.class.getName());

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
            logger.info("Server is listening on port {} ", PORT_NUMBER);

            while (true) {
                clientSocket = serverSocket.accept();
                logger.info("Client connected: {}", clientSocket.getInetAddress());

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
            logger.info("Received payload: {}", payload);

            String eventType = MaskData.base64DecodeSelectedValue(jsonPayload.getString("eventType"));
            logger.info("Event type received: {}", eventType);

            EventHandler eventHandler = EventHandlerRegistry.getEventHandler(eventType);

            if (eventHandler != null) {

                String response = eventHandler.handleEvent(MaskData.base64Decode(jsonPayload));

                logger.info("Response: {}", response);
                writer.write(response);
                writer.newLine();
                writer.flush();
            } else {
                logger.error("Error, unknown event: {}", eventHandler);
            }
        } catch (IOException e) {
            logger.error("Exception: {}", e.getCause());
            throw new RuntimeException(e);
        }
    }

}
