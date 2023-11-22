import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import sql_statements.CreateTable;

public class ChatDBManager {
    private final String URL = System.getenv("POSTGRE_URL");
    private final String USERNAME = System.getenv("POSTGRE_USERNAME");
    private String PASSWORD = System.getenv("POSTGRE_PASSWORD");

    // singleton
    private static ChatDBManager instance;
    private Connection connection;

    // Define the constructor as private to prevent direct instantiation of the
    // class (aka singleton)

    private void executeStatement(String tableQuery){
        try (PreparedStatement preparedStatement = connection.prepareStatement(tableQuery)) {
            preparedStatement.executeUpdate();
            System.out.println("Table created successfully!");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private ChatDBManager() {
        try {
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            executeStatement(CreateTable.getCreateUserTableQuery());

            executeStatement(CreateTable.getCreateFriendRequestTableQuery());

            executeStatement(CreateTable.getCreateGroupChatTableQuery());

            executeStatement(CreateTable.getCreateMessageTableQuery());

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static ChatDBManager getInstance() {
        if (instance == null) {
            instance = new ChatDBManager();
        }
        return instance;
    }

    public Connection getConnection() {
        return this.connection;
    }


    public String[] executeQuery(String query, String columns) {
        List<String> queryResultList = new ArrayList<String>();

        try (PreparedStatement pst = connection.prepareStatement(query);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {

                // TODO: check for cases to getInt, getDouble, etc.
                // Example: int id = rs.getInt("id");
                queryResultList.add(rs.getString(columns));
                System.out.println("Received columns -> " + rs.getString(columns));

            }
            return queryResultList.toArray(new String[queryResultList.size()]);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    public void insertQuery(String query,String... values) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            for(int i = 0; i < values.length; i++){
                preparedStatement.setString(i + 1, values[i]);
            }
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String base64Decode(String encodedValue) {
        byte[] decodedBytes = Base64.getDecoder().decode(encodedValue);
        return new String(decodedBytes);
    }
}




















