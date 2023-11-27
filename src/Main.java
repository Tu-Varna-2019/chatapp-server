
public class Main {
    public static void main(String[] arg) {
        String sqlGetDatabases = "SELECT datname FROM pg_database;";

        SocketConnection socketConnection = SocketConnection.getInstance();

        // Comment for now
        // String[] queryResult = chatDBManager.executeQuery(sqlGetDatabases,
        // "datname");
    }
}
