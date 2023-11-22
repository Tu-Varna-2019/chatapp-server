import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

import org.json.JSONObject;

import java.util.Base64;

public class HelloWorldServer {

    public static void main(String[] args) {
        final int portNumber = 8081;

        try (ServerSocket serverSocket = new ServerSocket(portNumber)) {
            System.out.println("Server is listening on port " + portNumber);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());


                new Thread(() -> signUp(clientSocket)).start();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void signUp(Socket clientSocket) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()))) {

            String requestData = reader.readLine();
            System.out.println("Received JSON data: " + requestData);


            JSONObject json = new JSONObject(requestData);
            String encodedUserName = json.getString("encodedUserName");
            String encodedEmail = json.getString("encodedEmail");
            String hashedPassword = json.getString("hashedPassword");

            String decodedUserName =  ChatDBManager.base64Decode(encodedUserName);
            String decodedEmail =  ChatDBManager.base64Decode(encodedEmail);

            System.out.println("UserName: " + decodedUserName);
            System.out.println("Email: " + decodedEmail);
            System.out.println("Password: " + hashedPassword);

            String query = "INSERT INTO \"User\"(username, email, password) VALUES (?, ?, ?)";

            ChatDBManager chatDBManager = ChatDBManager.getInstance();
            chatDBManager.insertQuery(query, decodedUserName, decodedEmail, hashedPassword);

            String response = "User has registered";

            writer.write(response);
            writer.newLine();
            writer.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
