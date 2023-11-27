import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

import helpers.Utils;
import org.json.JSONObject;

public class SocketConnection {

    private final int PORT_NUMBER = 8081;
    private static SocketConnection instance;
    private static final ChatDBManager chatDBManager = ChatDBManager.getInstance();
    private static Socket clientSocket;

    SocketConnection() {
        try (ServerSocket serverSocket = new ServerSocket(PORT_NUMBER)) {
            System.out.println("Server is listening on port " + PORT_NUMBER);

            while (true) {
                clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                new Thread(() -> eventHandler()).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SocketConnection getInstance() {
        if (instance == null) {
            instance = new SocketConnection();
        }
        return instance;
    }

    private static void signUp(String decodedUserName, String decodedEmail,
            String decodedPassword) {
        try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

            System.out.println("UserName: " + decodedUserName);
            System.out.println("Email: " + decodedEmail);
            System.out.println("Password: " + decodedPassword);

            String query = "INSERT INTO \"User\"(username, email, password) VALUES (?, ?, ?)";

            chatDBManager.insertQuery(query, decodedUserName, decodedEmail, decodedPassword);

            String response = "User has registered";

            writer.write(response);
            writer.newLine();
            writer.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void eventHandler() {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))) {
            String requestData = reader.readLine();
            System.out.println("Received JSON data: " + requestData);

            JSONObject json = new JSONObject(requestData);
            String eventType = Utils.base64Decode(json.getString("encodedEventType"));
            System.out.println(eventType);

            handleEventType(eventType, json);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void handleEventType(String eventType, JSONObject json) {
        switch (eventType) {
            case "SignUp":
                String decodedUserName = Utils.base64Decode(json.getString("encodedUserName"));
                String decodedEmail = Utils.base64Decode(json.getString("encodedEmail"));
                String decodedPassword = Utils.base64Decode(json.getString("encodedPassword"));
                signUp(decodedUserName, decodedEmail, decodedPassword);
                break;

            case "Login":
                // Future login logic
                break;

            default:
                System.out.println("Invalid Event Type");
        }
    }
}
