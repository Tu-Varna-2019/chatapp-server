public class Main {
    public static void main(String[] arg) {
        String sqlGetDatabases = "SELECT datname FROM pg_database;";

        ChatDBManager chatDBManager = ChatDBManager.getInstance();

        String[] queryResult = chatDBManager.executeQuery(sqlGetDatabases, "datname");

        //тест hava network project
    }
}
