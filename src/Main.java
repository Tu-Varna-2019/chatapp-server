import view.SocketConnection;

public class Main {
    public static void main(String[] arg) {

        SocketConnection socketConnection = SocketConnection.getInstance();
        socketConnection.startServer();
    }
}
