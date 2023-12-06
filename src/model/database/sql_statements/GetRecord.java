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

    public final String getGroupChatEQID(int id) {

        return "SELECT * FROM \"GroupChat\" WHERE " + id + " = ANY(userids);";
    };
};
