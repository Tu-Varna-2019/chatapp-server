package view;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import controller.events.EventHandler;
import controller.events.EventHandlerRegistry;
import model.dataclass.ClientRequest;

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

            String stringPayload = reader.readLine();

            logger.info("Received payload: {}", stringPayload);

            ClientRequest payload = deserializePayload(stringPayload);
            logger.info("Event type received: {}", payload.eventType);

            EventHandler eventHandler = EventHandlerRegistry.getInstance().getEventHandler(payload.eventType);

            if (eventHandler != null) {
                String response = eventHandler.handleEvent(payload);
                // STILL ON TESTING for implementing the notification functionality
                // if (eventType.equals("Login")) {
                // logger.info("Client is now logged! Adding {} to sockets list!",
                // decodedData.get("email"));
                // sockets.put(decodedData.get("email"), clientSocket);
                // }
                logger.info("Response: {}", response);
                writer.write(response);
                writer.newLine();
                writer.flush();
            } else {
                logger.error("Error, unknown event: {}", payload.eventType);
            }

        } catch (IOException e) {
            logger.error("I/O Exception in client request handling: ", e);
        }
    }

    private ClientRequest deserializePayload(String stringPayload) {
        JSONObject jsonPayload = new JSONObject(stringPayload);

        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(Include.NON_NULL);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        ClientRequest clientResponse = null;
        try {
            clientResponse = mapper.readValue(jsonPayload.toString(), ClientRequest.class);
        } catch (IOException e) {
            logger.error("Error deserializing payload: ", e);
        }

        return clientResponse;
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
