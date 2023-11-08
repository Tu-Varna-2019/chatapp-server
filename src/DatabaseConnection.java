import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection connectToRDS() {
        String url = System.getenv("POSTGRE_URL");
        String username = System.getenv("POSTGRE_USERNAME");
        String password = System.getenv("POSTGRE_PASSWORD");

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }

    }

    public void test(){

        String query = "SELECT datname FROM pg_database;";

        System.out.println(query);
    }

}
