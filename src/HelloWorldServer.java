import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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

            String clientMessage = reader.readLine();
            System.out.println("Received from client: " + clientMessage);
            String[] parts = clientMessage.split(";");
            String userName = parts[0];
            String email = parts[1];
            String password = parts[2];

            String query = "INSERT INTO \"User\"(username, email, password) VALUES (?, ?, ?)";

            ChatDBManager chatDBManager = ChatDBManager.getInstance();
            chatDBManager.insertQuery(query, userName, email, password);

            String response = "User has registered";

            writer.write(response);
            writer.newLine();
            writer.flush();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
