package view;

import org.json.JSONObject;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import controller.events.EventHandler;
import controller.events.EventHandlerRegistry;
import controller.helpers.MaskData;

public class SocketConnection {

    private static final Logger logger = LogManager.getLogger(SocketConnection.class.getName());
    private final int PORT_NUMBER = 8081;
    private static SocketConnection instance;
    private final ExecutorService threadPool;

    public static SocketConnection getInstance() {
        if (instance == null) {
            instance = new SocketConnection();
        }
        return instance;
    }

    private SocketConnection() {
        threadPool = Executors.newCachedThreadPool();

        try (ServerSocket serverSocket = new ServerSocket(PORT_NUMBER)) {
            logger.info("Server is listening on port {} ", PORT_NUMBER);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                logger.info("Client connected: {}", clientSocket.getInetAddress());
                threadPool.submit(() -> handleClientRequest(clientSocket));
            }
        } catch (IOException e) {
            logger.error("Server Socket Exception: ", e);
        }
    }

    private void handleClientRequest(Socket clientSocket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

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
                logger.error("Error, unknown event: {}", eventType);
            }

        } catch (IOException e) {
            logger.error("I/O Exception in client request handling: ", e);
        }
    }
}
