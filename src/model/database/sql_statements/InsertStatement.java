package model.database.sql_statements;

public class InsertStatement {

    public final String INSERT_USER = "INSERT INTO \"User\"(username, email, password) VALUES (?, ?, ?)";

    public final String INSERT_FRIEND_REQUEST = "INSERT INTO \"FriendRequest\"(status, senderid, recipientid) VALUES (?, ?, ?)";

}
