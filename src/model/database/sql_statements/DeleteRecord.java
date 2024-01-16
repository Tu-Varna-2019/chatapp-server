package model.database.sql_statements;

public class DeleteRecord {

    public final String DeleteUserEQID(int id) {

        return "DELETE FROM \"User\" WHERE id= " + id + ";";
    };

    public final String DeleteMessageEQID(int id) {

        return "DELETE FROM \"Message\" WHERE id= " + id + ";";
    };

    public final String DeleteGroupChatEQID(int id) {

        return "DELETE FROM \"GroupChat\" WHERE id= " + id + ";";
    };
}
