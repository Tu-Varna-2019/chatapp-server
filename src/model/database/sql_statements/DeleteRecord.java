package model.database.sql_statements;

public class DeleteRecord {

    private final String DELETE_FROM = "DELETE FROM ";

    public final String DeleteUserEQID(int id) {

        return DELETE_FROM + " \"User\" WHERE id= " + id + ";";
    };

    public final String DeleteMessageEQID(int id) {

        return DELETE_FROM + " \"Message\" WHERE id= " + id + ";";
    };

    public final String DeleteGroupChatEQID(int id) {

        return DELETE_FROM + " \"GroupChat\" WHERE id= " + id + ";";
    };

    public final String DeleteFriendRequestEQID(int id) {

        return DELETE_FROM + " \"FriendRequest\" WHERE id= " + id + ";";
    };
}
