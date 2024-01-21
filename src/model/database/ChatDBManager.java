package model.database;

import java.sql.Timestamp;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import model.FriendRequest;
import model.GroupChat;
import model.Message;
import model.User;
import model.database.sql_statements.CreateTable;
import model.database.sql_statements.GetRecord;
import controller.helpers.exceptions.SqlExecutionException;

public class ChatDBManager {
    private static final Logger logger = LogManager.getLogger(ChatDBManager.class.getName());

    private final String URL = System.getenv("POSTGRE_URL");
    private final String USERNAME = System.getenv("POSTGRE_USERNAME");
    private String PASSWORD = System.getenv("POSTGRE_PASSWORD");

    // singleton
    private static ChatDBManager instance;
    private Connection connection;
    private final GetRecord getRecord = new GetRecord();

    // Define the constructor as private to prevent direct instantiation of the
    // class (aka singleton)
    private void createTableQuery(String tableQuery) {
        try (PreparedStatement preparedStatement = connection.prepareStatement(tableQuery)) {
            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            throw new SqlExecutionException("createTableQuery error!", e);
        }
    }

    private ChatDBManager() {
        try {
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);

            for (String tableQuery : CreateTable.getCreateTableQueries()) {
                createTableQuery(tableQuery);
            }

        } catch (SQLException e) {
            logger.info(e.getMessage());
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

    public boolean getRecordExists(String query, Object... columns) {

        try (PreparedStatement pst = getPreparedStatement(query, columns)) {

            try (ResultSet rs = pst.executeQuery()) {
                if (rs.next())
                    return rs.getBoolean(1);
            }
        } catch (SQLException ex) {
            logger.error(ex.getMessage());
        }
        return false;
    }

    public List<User> getUsersQuery(String query, Object... columns) {
        List<User> queryResultList = new ArrayList<>();

        try (PreparedStatement pst = getPreparedStatement(query, columns);
                ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {

                User user = new User(rs.getInt("id"), rs.getString("username"), rs.getString("email"),
                        rs.getString("password"));

                queryResultList.add(user);
            }
            return queryResultList;

        } catch (SQLException ex) {
            logger.error(ex.getMessage());
        }
        return Collections.emptyList();

    }

    public List<GroupChat> getGroupChatQuery(String query, Object... columns) {
        List<GroupChat> queryResultList = new ArrayList<>();

        try (PreparedStatement pst = getPreparedStatement(query, columns);
                ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {

                Array userIdsArray = rs.getArray("userids");
                Integer[] userIds = (Integer[]) userIdsArray.getArray();

                GroupChat groupChat = new GroupChat(rs.getInt("id"), rs.getString("name"), userIds);

                queryResultList.add(groupChat);
            }
            return queryResultList;
        } catch (SQLException ex) {
            logger.info(ex.getMessage());
        }
        return Collections.emptyList();
    }

    public List<FriendRequest> getFriendRequestQuery(String query, Object... columns) {
        List<FriendRequest> queryResultList = new ArrayList<>();

        try (PreparedStatement pst = getPreparedStatement(query, columns);
                ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {

                Integer recipientIdsArray = rs.getInt("recipientid");
                Integer senderIdsArray = rs.getInt("senderid");

                List<User> dbSender = getUsersQuery(getRecord.GET_USER_EQ_ID, senderIdsArray);
                List<User> dbRecipient = getUsersQuery(getRecord.GET_USER_EQ_ID, recipientIdsArray);

                FriendRequest friendRequest = new FriendRequest(rs.getInt("id"), rs.getString("status"),
                        dbSender.get(0), dbRecipient.get(0));

                queryResultList.add(friendRequest);
            }
            return queryResultList;
        } catch (SQLException ex) {
            logger.info(ex.getMessage());
        }
        return Collections.emptyList();
    }

    public List<Message> getMessagesQuery(String query, Object... columns) {
        List<Message> queryResultList = new ArrayList<>();

        try (PreparedStatement pst = getPreparedStatement(query, columns);
                ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {

                Integer senderID = rs.getInt("senderid");

                List<User> dbRetrievedUser = getUsersQuery(getRecord.GET_USER_EQ_ID, senderID);

                Message message = new Message(rs.getInt("id"), rs.getString("content"), rs.getString("attachmenturl"),
                        rs.getTimestamp("timestamp"),
                        dbRetrievedUser.get(0));

                queryResultList.add(message);
            }
            return queryResultList;
        } catch (SQLException ex) {
            logger.info(ex.getMessage());
        }
        return Collections.emptyList();
    }

    public boolean executeUpdateQuery(String query, Object... columns) {
        try (PreparedStatement preparedStatement = getPreparedStatement(query, columns)) {

            int rowsAffected = preparedStatement.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            throw new SqlExecutionException("executeUpdateQuery error!", e);
        }
    }

    public PreparedStatement getPreparedStatement(String query, Object... columns) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);

            for (int index = 0; index < columns.length; index++) {
                if (columns[index] instanceof String)
                    preparedStatement.setString(index + 1, (String) columns[index]);
                else if (columns[index] instanceof Timestamp)
                    preparedStatement.setTimestamp(index + 1, (Timestamp) columns[index]);
                else if (columns[index] instanceof Integer)
                    preparedStatement.setInt(index + 1, (Integer) columns[index]);
                else if (columns[index] instanceof Integer[]) {
                    Array sqlArray = connection.createArrayOf("integer", (Object[]) columns[index]);
                    preparedStatement.setArray(index + 1, sqlArray);
                }
            }

            return preparedStatement;
        } catch (SQLException e) {
            throw new SqlExecutionException("getPreparedStatement error!", e);
        }
    }
}
