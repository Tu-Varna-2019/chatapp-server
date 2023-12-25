package view;

import org.json.JSONObject;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

import controller.events.EventHandler;
import controller.events.EventHandlerRegistry;
import controller.helpers.MaskData;

public class SocketConnection {

    private static final Logger logger = LogManager.getLogger(SocketConnection.class.getName());
    private final int PORT_NUMBER = 8081;
    private static SocketConnection instance;
    private final ConcurrentHashMap<String, Socket> sockets = new ConcurrentHashMap<>();

    public static SocketConnection getInstance() {
        if (instance == null) {
            instance = new SocketConnection();
        }

        return instance;
    }

    private SocketConnection() {
    }

    public void startServer() {
        try (ServerSocket serverSocket = new ServerSocket(PORT_NUMBER)) {
            logger.info("Server is listening on port {} ", PORT_NUMBER);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                logger.info("Client connected: {}", clientSocket.getInetAddress());
                Thread thread = new Thread(() -> handleClientRequest(clientSocket));
                thread.start();
            }
        } catch (IOException e) {
            logger.error("Server Socket Exception: ", e);
        }
    }

    private void handleClientRequest(Socket clientSocket) {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

            String payload = reader.readLine();
            if (payload == null) {
                logger.error("Error, payload is null!");
                return;
            }
            JSONObject jsonPayload = new JSONObject(payload);
            logger.info("Received payload: {}", payload);

            String eventType = MaskData.base64DecodeSelectedValue(jsonPayload.getString("eventType"));
            logger.info("Event type received: {}", eventType);

            EventHandler eventHandler = EventHandlerRegistry.getEventHandler(eventType);

            if (eventHandler != null) {
                TreeMap<String, String> decodedData = MaskData.base64Decode(jsonPayload);

                String response = eventHandler.handleEvent(decodedData);

                if (eventType.equals("Login")) {
                    logger.info("Client is now logged! Adding {} to sockets list!", decodedData.get("email"));
                    sockets.put(decodedData.get("email"), clientSocket);
                }

                logger.info("Response: {}", response);
                writer.write(response);
                writer.newLine();
                writer.flush();
            } else {
                logger.error("Error, unknown event: {}", eventType);
            }

        } catch (

        IOException e) {
            logger.error("I/O Exception in client request handling: ", e);
        }
    }

    public boolean sendEvent(String email, String response) {
        try {
            Socket socket = sockets.get(email);
            if (socket != null && !socket.isClosed()) {
                logger.info("Sending response to client: {}", response);
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                writer.write(response);
                writer.newLine();
                writer.flush();

                return true;
            }
            return false;
        } catch (IOException e) {
            logger.error("I/O Exception in client request handling: ", e);
            return false;
        }
    }
}
