import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatDBManager {
    private final String URL = System.getenv("POSTGRE_URL");
    private final String USERNAME = System.getenv("POSTGRE_USERNAME");
    private String PASSWORD = System.getenv("POSTGRE_PASSWORD");


    // singleton
    private static ChatDBManager instance;
    private Connection connection;



    // Define the constructor as private to prevent direct instantiation of the
    // class (aka singleton)
    private ChatDBManager() {
        try {
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            String createUserTableQuery = "CREATE TABLE IF NOT EXISTS \"User\" ("
                    + "ID SERIAL PRIMARY KEY,"
                    + "Username VARCHAR(255) NOT NULL,"
                    + "Email VARCHAR(255) NOT NULL,"
                    + "Password VARCHAR(255) NOT NULL"
                    + ");";

            try (PreparedStatement preparedStatement = connection.prepareStatement(createUserTableQuery)) {
                preparedStatement.executeUpdate();
                // защо когато runn-а кодът винаги ми излиза че съм направил нова таблица, а когато вляза в dbeaver има само 1 създадеа таблица
                System.out.println("User table created successfully.");
            }

            String createFriendRequestTableQuery = "CREATE TABLE IF NOT EXISTS FriendRequest ("
                    + "ID SERIAL PRIMARY KEY,"
                    + "Status VARCHAR(20) DEFAULT 'Pending' CHECK (Status IN ('Pending', 'Accepted', 'Rejected')),"
                    + "SentUserID INT,"
                    + "InvitedUserID INT,"
                    + "FOREIGN KEY (SentUserID) REFERENCES \"User\"(ID),"
                    + "FOREIGN KEY (InvitedUserID) REFERENCES \"User\"(ID)"
                    + ");";

            try (PreparedStatement preparedStatement = connection.prepareStatement(createFriendRequestTableQuery)) {
                preparedStatement.executeUpdate();
                System.out.println("FriendRequest table created successfully.");
            }

            String createGroupChatTableQuery = "CREATE TABLE IF NOT EXISTS GroupChat ("
                    + "ID SERIAL PRIMARY KEY,"
                    + "Name VARCHAR(255),"
                    + "UserID INT,"
                    + "FOREIGN KEY (UserID) REFERENCES \"User\"(ID)" // ID или Username
                    + ");";

            try (PreparedStatement preparedStatement = connection.prepareStatement(createGroupChatTableQuery)) {
                preparedStatement.executeUpdate();
                System.out.println("GroupChat table created successfully.");
            }

            String createMessageTableQuery = "CREATE TABLE IF NOT EXISTS Message ("
                    + "ID SERIAL PRIMARY KEY,"
                    + "Content VARCHAR(255),"
                    + "AttachmentURL VARCHAR(255),"
                    + "TimeStamp TIMESTAMP,"
                    + "SenderID INT,"
                    + "ChatID INT,"
                    + "FOREIGN KEY (SenderID) REFERENCES \"User\"(ID),"
                    + "FOREIGN KEY (ChatID) REFERENCES GroupChat(ID)"
                    + ");";

            try (PreparedStatement preparedStatement = connection.prepareStatement(createMessageTableQuery)) {
                preparedStatement.executeUpdate();
                System.out.println("Message table created successfully.");
            }



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

/*     try {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS User ("
                + "ID SERIAL PRIMARY KEY,"
                + "Username VARCHAR(255) NOT NULL,"
                + "Email VARCHAR(255) NOT NULL,"
                + "Password VARCHAR(255) NOT NULL"
                + ");";

        // Execute the query
        try (PreparedStatement preparedStatement = connection.prepareStatement(createTableQuery)) {
            // Execute the query
            preparedStatement.executeUpdate();

            System.out.println("Table created successfully.");
        }

        connection.close();

    } catch (SQLException e) {
        throw new RuntimeException(e);
    }*/
}
