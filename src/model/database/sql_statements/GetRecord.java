package model.database.sql_statements;

public class GetRecord {

    public final String getUserEQEmail(String email) {

        return "SELECT * FROM \"User\" WHERE email='" + email + "';";
    };

    public final String getUserEQID(int id) {

        return "SELECT * FROM \"User\" WHERE id= " + id + ";";
    };

    public final String getUsersByIDS(String userIds) {

        return "SELECT * FROM \"User\" WHERE id IN (" + userIds + ");";
    };

    public final String getGroupChatEQUserID(int id) {

        return "SELECT * FROM \"GroupChat\" WHERE " + id + " = ANY(userids);";
    };

    public final String getGroupChatEQID(int id) {

        return "SELECT * FROM \"GroupChat\" WHERE id= " + id + ";";
    };

    public final String getMessageEQGroupID(int id) {

        return "SELECT * FROM \"Message\" WHERE groupchatid= " + id + " ;";
    };

    public final String getMessageEQID(int id) {

        return "SELECT * FROM \"Message\" WHERE id= " + id + " ;";
    };

    public final String getFriendRequestEQSenderID(int id) {

        return "SELECT * FROM \"FriendRequest\" WHERE senderid= " + id + " ;";
    };
};
