import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    public static Connection connectToRDS() {
        String url = "jdbc:tuvarnachatdb.culjntjsalhu.eu-west-1.rds.amazonaws.com";
        String username = "postgres";
        String password = "m9fwEDWuZ6EFSCXi7Up2EzNLWPYxa83E9RSra5RPiUjH7K6xC";

        try {
            Connection connection = DriverManager.getConnection(url, username, password);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
